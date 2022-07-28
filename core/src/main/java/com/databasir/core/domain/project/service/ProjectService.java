package com.databasir.core.domain.project.service;

import com.databasir.common.codec.Aes;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.project.converter.DataSourceConverter;
import com.databasir.core.domain.project.converter.ProjectConverter;
import com.databasir.core.domain.project.converter.ProjectResponseConverter;
import com.databasir.core.domain.project.converter.ProjectSimpleTaskResponseConverter;
import com.databasir.core.domain.project.data.*;
import com.databasir.core.domain.project.data.task.ProjectSimpleTaskResponse;
import com.databasir.core.domain.project.data.task.ProjectTaskListCondition;
import com.databasir.core.domain.project.event.ProjectDeleted;
import com.databasir.core.domain.project.event.ProjectSaved;
import com.databasir.core.infrastructure.connection.DatabaseConnectionService;
import com.databasir.core.infrastructure.event.EventPublisher;
import com.databasir.dao.enums.ProjectSyncTaskStatus;
import com.databasir.dao.impl.*;
import com.databasir.dao.tables.pojos.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final DatabaseConnectionService databaseConnectionService;

    private final ProjectDao projectDao;

    private final ProjectSyncRuleDao projectSyncRuleDao;

    private final DataSourceDao dataSourceDao;

    private final SysKeyDao sysKeyDao;

    private final DataSourcePropertyDao dataSourcePropertyDao;

    private final UserFavoriteProjectDao userFavoriteProjectDao;

    private final ProjectSyncTaskDao projectSyncTaskDao;

    private final DataSourceConverter dataSourceConverter;

    private final ProjectConverter projectConverter;

    private final ProjectResponseConverter projectResponseConverter;

    private final ProjectSimpleTaskResponseConverter projectSimpleTaskResponseConverter;

    private final EventPublisher eventPublisher;

    public ProjectDetailResponse getOne(Integer id) {
        return projectDao.selectOptionalById(id)
                .map(schemaSource -> {
                    var dataSource = dataSourceDao.selectByProjectId(id);
                    var properties = dataSourcePropertyDao.selectByDataSourceId(dataSource.getId());
                    var dataSourceResponse = projectResponseConverter.toResponse(dataSource, properties);
                    var projectSyncRule = projectSyncRuleDao.selectOptionalByProjectId(id).orElse(null);
                    var ruleResponse = projectResponseConverter.toResponse(projectSyncRule);
                    return projectResponseConverter.toResponse(schemaSource, dataSourceResponse, ruleResponse);
                })
                .orElseThrow(DomainErrors.PROJECT_NOT_FOUND::exception);
    }

    @Transactional
    public Integer create(ProjectCreateRequest request) {
        Project project = projectConverter.of(request);
        Integer projectId = null;
        try {
            projectId = projectDao.insertAndReturnId(project);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.PROJECT_NAME_DUPLICATE.exception();
        }

        String newPassword = encryptPassword(request.getDataSource().getPassword()).get();
        DataSource dataSource = dataSourceConverter.of(request.getDataSource(), newPassword, projectId);
        Integer dataSourceId = dataSourceDao.insertAndReturnId(dataSource);

        List<DataSourcePropertyValue> propertyValues = request.getDataSource().getProperties();
        List<DataSourceProperty> properties = dataSourceConverter.of(propertyValues, dataSourceId);
        dataSourcePropertyDao.batchInsert(properties);

        ProjectSyncRule syncRule = projectConverter.of(request.getProjectSyncRule(), projectId);
        projectSyncRuleDao.insertAndReturnId(syncRule);

        var event = ProjectSaved.builder()
                .groupId(project.getGroupId())
                .projectId(projectId)
                .projectName(project.getName())
                .projectDescription(project.getDescription())
                .databaseType(request.getDataSource().getDatabaseType())
                .databaseName(dataSource.getDatabaseName())
                .schemaName(dataSource.getSchemaName())
                .build();
        eventPublisher.publish(event);
        return projectId;
    }

    @Transactional
    public void update(Integer groupId, ProjectUpdateRequest request) {
        Integer projectId = request.getId();
        if (projectDao.exists(groupId, projectId)) {
            // update dataSource
            String newPassword = encryptPassword(request.getDataSource().getPassword()).orElse(null);
            DataSource dataSource = dataSourceConverter.of(request.getDataSource(), newPassword, projectId);
            dataSourceDao.updateByProjectId(dataSource);

            // update connection property
            Integer dataSourceId = dataSourceDao.selectByProjectId(projectId).getId();
            List<DataSourcePropertyValue> propertyValues = request.getDataSource().getProperties();
            List<DataSourceProperty> properties = dataSourceConverter.of(propertyValues, dataSourceId);
            if (properties.isEmpty()) {
                dataSourcePropertyDao.deleteByDataSourceId(dataSourceId);
            } else {
                dataSourcePropertyDao.deleteByDataSourceId(dataSourceId);
                dataSourcePropertyDao.batchInsert(properties);
            }

            // update project sync rule
            ProjectSyncRule syncRule = projectConverter.of(request.getProjectSyncRule(), projectId);
            projectSyncRuleDao.deleteByProjectId(projectId);
            projectSyncRuleDao.insertAndReturnId(syncRule);

            // update project info
            Project project = projectConverter.of(request);
            projectDao.updateById(project);

            ProjectSaved event = ProjectSaved.builder()
                    .groupId(groupId)
                    .projectId(project.getId())
                    .projectName(project.getName())
                    .projectDescription(project.getDescription())
                    .databaseType(request.getDataSource().getDatabaseType())
                    .databaseName(dataSource.getDatabaseName())
                    .schemaName(dataSource.getSchemaName())
                    .build();
            eventPublisher.publish(event);
        } else {
            throw DomainErrors.PROJECT_NOT_FOUND.exception();
        }
    }

    private Optional<String> encryptPassword(String password) {
        if (!StringUtils.hasText(password)) {
            return Optional.empty();
        }
        SysKey sysKey = sysKeyDao.selectTopOne();
        // String decryptedPassword = Rsa.decryptFromBase64DataByPrivateKey(password, sysKey.getRsaPrivateKey());
        return Optional.of(Aes.encryptToBase64Data(password, sysKey.getAesKey()));
    }

    @Transactional
    public void delete(Integer projectId) {
        projectDao.updateDeletedById(true, projectId);
        projectSyncRuleDao.disableAutoSyncByProjectId(projectId);
        eventPublisher.publish(new ProjectDeleted(projectId));
    }

    public Page<ProjectSimpleResponse> list(Integer userId, Pageable page, ProjectListCondition condition) {
        Page<Project> pageData = projectDao.selectByCondition(page, condition.toCondition());
        List<Integer> projectIds = pageData.getContent()
                .stream()
                .map(Project::getId)
                .collect(Collectors.toList());
        Map<Integer, DataSource> dataSourceMapByProjectId = dataSourceDao.selectInProjectIds(projectIds)
                .stream()
                .collect(Collectors.toMap(DataSource::getProjectId, Function.identity()));
        Map<Integer, ProjectSyncRule> syncRuleMapByProjectId = projectSyncRuleDao.selectInProjectIds(projectIds)
                .stream()
                .collect(Collectors.toMap(ProjectSyncRule::getProjectId, Function.identity()));
        Set<Integer> favoriteProjectIds = userFavoriteProjectDao.selectByUserIdAndProjectIds(userId, projectIds)
                .stream()
                .map(UserFavoriteProject::getProjectId)
                .collect(Collectors.toSet());
        return pageData.map(project -> {
            DataSource dataSource = dataSourceMapByProjectId.get(project.getId());
            ProjectSyncRule syncRule = syncRuleMapByProjectId.get(project.getId());
            Boolean isFavorite = favoriteProjectIds.contains(project.getId());
            return projectResponseConverter.toSimple(project, dataSource, syncRule, isFavorite);
        });
    }

    public void testConnection(ProjectTestConnectionRequest request) {
        String password;
        if (request.getProjectId() != null && !StringUtils.hasText(request.getPassword())) {
            DataSource dataSource = dataSourceDao.selectByProjectId(request.getProjectId());
            SysKey sysKey = sysKeyDao.selectTopOne();
            password = Aes.decryptFromBase64Data(dataSource.getPassword(), sysKey.getAesKey());
        } else if (StringUtils.hasText(request.getPassword())) {
            password = request.getPassword();
        } else {
            throw DomainErrors.PASSWORD_MUST_NOT_BE_BLANK.exception();
        }
        Properties properties = new Properties();
        request.getProperties().forEach(prop -> properties.put(prop.getKey(), prop.getValue()));
        databaseConnectionService.testConnection(request.getUsername(),
                password,
                request.getUrl(),
                request.getDatabaseName(),
                request.getSchemaName(),
                request.getDatabaseType(),
                properties);
    }

    @Transactional
    public Optional<Integer> createSyncTask(Integer projectId, Integer userId, boolean ignoreIfExists) {
        if (!projectDao.existsById(projectId)) {
            log.warn("create sync task failed, because project not exists, projectId={}", projectId);
            throw DomainErrors.PROJECT_NOT_FOUND.exception();
        }
        var validTaskStatus = List.of(ProjectSyncTaskStatus.NEW, ProjectSyncTaskStatus.RUNNING);
        if (ignoreIfExists && projectSyncTaskDao.existsByProjectId(projectId, validTaskStatus)) {
            log.warn("create sync task failed, it's already exists, projectId={}", projectId);
            return Optional.empty();
        }
        ProjectSyncTask projectSyncTask = new ProjectSyncTask();
        projectSyncTask.setProjectId(projectId);
        projectSyncTask.setStatus(ProjectSyncTaskStatus.NEW);
        projectSyncTask.setUserId(userId);
        return Optional.of(projectSyncTaskDao.insertAndReturnId(projectSyncTask));
    }

    public List<ProjectSimpleTaskResponse> listManualTasks(Integer projectId, ProjectTaskListCondition condition) {
        var tasks = projectSyncTaskDao.selectList(condition.toCondition(projectId));
        return projectSimpleTaskResponseConverter.of(tasks);
    }

    @Transactional

    public void cancelTask(Integer projectId, Integer taskId) {
        if (!projectDao.existsById(projectId)) {
            throw DomainErrors.PROJECT_NOT_FOUND.exception();
        }
        projectSyncTaskDao.selectOptionalById(taskId).ifPresent(task -> {
            if (task.getStatus() == ProjectSyncTaskStatus.NEW || task.getStatus() == ProjectSyncTaskStatus.RUNNING) {
                projectSyncTaskDao.updateStatusAndResultById(taskId, ProjectSyncTaskStatus.CANCELED, "主动取消");
            }
        });
    }
}

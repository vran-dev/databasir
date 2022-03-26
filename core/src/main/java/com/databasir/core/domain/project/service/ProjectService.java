package com.databasir.core.domain.project.service;

import com.databasir.common.codec.Aes;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.project.converter.DataSourcePojoConverter;
import com.databasir.core.domain.project.converter.ProjectPojoConverter;
import com.databasir.core.domain.project.converter.ProjectResponseConverter;
import com.databasir.core.domain.project.data.*;
import com.databasir.core.infrastructure.connection.DatabaseConnectionService;
import com.databasir.dao.impl.*;
import com.databasir.dao.tables.pojos.*;
import lombok.RequiredArgsConstructor;
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
public class ProjectService {

    private final ProjectDao projectDao;

    private final ProjectSyncRuleDao projectSyncRuleDao;

    private final DataSourceDao dataSourceDao;

    private final SysKeyDao sysKeyDao;

    private final DataSourcePropertyDao dataSourcePropertyDao;

    private final UserFavoriteProjectDao userFavoriteProjectDao;

    private final DataSourcePojoConverter dataSourcePojoConverter;

    private final ProjectPojoConverter projectPojoConverter;

    private final ProjectResponseConverter projectResponseConverter;

    private final DatabaseConnectionService databaseConnectionService;

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
        ProjectPojo project = projectPojoConverter.of(request);
        Integer projectId = null;
        try {
            projectId = projectDao.insertAndReturnId(project);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.PROJECT_NAME_DUPLICATE.exception();
        }

        String newPassword = encryptPassword(request.getDataSource().getPassword()).get();
        DataSourcePojo dataSource = dataSourcePojoConverter.of(request.getDataSource(), newPassword, projectId);
        Integer dataSourceId = dataSourceDao.insertAndReturnId(dataSource);

        List<DataSourcePropertyValue> propertyValues = request.getDataSource().getProperties();
        List<DataSourcePropertyPojo> properties = dataSourcePojoConverter.of(propertyValues, dataSourceId);
        dataSourcePropertyDao.batchInsert(properties);

        ProjectSyncRulePojo syncRule = projectPojoConverter.of(request.getProjectSyncRule(), projectId);
        projectSyncRuleDao.insertAndReturnId(syncRule);
        return projectId;
    }

    @Transactional
    public void update(Integer groupId, ProjectUpdateRequest request) {
        Integer projectId = request.getId();
        if (projectDao.exists(groupId, projectId)) {
            // update dataSource
            String newPassword = encryptPassword(request.getDataSource().getPassword()).orElse(null);
            DataSourcePojo dataSource = dataSourcePojoConverter.of(request.getDataSource(), newPassword, projectId);
            dataSourceDao.updateByProjectId(dataSource);

            // update connection property
            Integer dataSourceId = dataSourceDao.selectByProjectId(projectId).getId();
            List<DataSourcePropertyValue> propertyValues = request.getDataSource().getProperties();
            List<DataSourcePropertyPojo> properties = dataSourcePojoConverter.of(propertyValues, dataSourceId);
            if (properties.isEmpty()) {
                dataSourcePropertyDao.deleteByDataSourceId(dataSourceId);
            } else {
                dataSourcePropertyDao.deleteByDataSourceId(dataSourceId);
                dataSourcePropertyDao.batchInsert(properties);
            }

            // update project sync rule
            ProjectSyncRulePojo syncRule = projectPojoConverter.of(request.getProjectSyncRule(), projectId);
            projectSyncRuleDao.deleteByProjectId(projectId);
            projectSyncRuleDao.insertAndReturnId(syncRule);

            // update project info
            ProjectPojo project = projectPojoConverter.of(request);
            projectDao.updateById(project);
        } else {
            throw DomainErrors.PROJECT_NOT_FOUND.exception();
        }
    }

    private Optional<String> encryptPassword(String password) {
        if (!StringUtils.hasText(password)) {
            return Optional.empty();
        }
        SysKeyPojo sysKey = sysKeyDao.selectTopOne();
        // String decryptedPassword = Rsa.decryptFromBase64DataByPrivateKey(password, sysKey.getRsaPrivateKey());
        return Optional.of(Aes.encryptToBase64Data(password, sysKey.getAesKey()));
    }

    @Transactional
    public void delete(Integer projectId) {
        projectDao.updateDeletedById(true, projectId);
        projectSyncRuleDao.disableAutoSyncByProjectId(projectId);
    }

    public Page<ProjectSimpleResponse> list(Integer userId, Pageable page, ProjectListCondition condition) {
        Page<ProjectPojo> pageData = projectDao.selectByCondition(page, condition.toCondition());
        List<Integer> projectIds = pageData.getContent()
                .stream()
                .map(ProjectPojo::getId)
                .collect(Collectors.toList());
        Map<Integer, DataSourcePojo> dataSourceMapByProjectId = dataSourceDao.selectInProjectIds(projectIds)
                .stream()
                .collect(Collectors.toMap(DataSourcePojo::getProjectId, Function.identity()));
        Map<Integer, ProjectSyncRulePojo> syncRuleMapByProjectId = projectSyncRuleDao.selectInProjectIds(projectIds)
                .stream()
                .collect(Collectors.toMap(ProjectSyncRulePojo::getProjectId, Function.identity()));
        Set<Integer> favoriteProjectIds = userFavoriteProjectDao.selectByUserIdAndProjectIds(userId, projectIds)
                .stream()
                .map(UserFavoriteProjectPojo::getProjectId)
                .collect(Collectors.toSet());
        return pageData.map(project -> {
            DataSourcePojo dataSource = dataSourceMapByProjectId.get(project.getId());
            ProjectSyncRulePojo syncRule = syncRuleMapByProjectId.get(project.getId());
            Boolean isFavorite = favoriteProjectIds.contains(project.getId());
            return projectResponseConverter.toSimple(project, dataSource, syncRule, isFavorite);
        });
    }

    public void testConnection(ProjectTestConnectionRequest request) {
        String password;
        if (request.getProjectId() != null && !StringUtils.hasText(request.getPassword())) {
            DataSourcePojo dataSource = dataSourceDao.selectByProjectId(request.getProjectId());
            SysKeyPojo sysKey = sysKeyDao.selectTopOne();
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

}

package com.databasir.core.domain.user.service;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.user.converter.FavoriteProjectPageResponseConverter;
import com.databasir.core.domain.user.data.FavoriteProjectPageCondition;
import com.databasir.core.domain.user.data.FavoriteProjectPageResponse;
import com.databasir.dao.impl.*;
import com.databasir.dao.tables.pojos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProjectService {

    private final UserFavoriteProjectDao userFavoriteProjectDao;

    private final ProjectDao projectDao;

    private final ProjectSyncRuleDao projectSyncRuleDao;

    private final DataSourceDao dataSourceDao;

    private final GroupDao groupDao;

    private final FavoriteProjectPageResponseConverter favoriteProjectPageResponseConverter;

    public Page<FavoriteProjectPageResponse> listFavorites(Pageable pageable,
                                                           Integer userId,
                                                           FavoriteProjectPageCondition condition) {
        var data = userFavoriteProjectDao.selectByCondition(pageable, condition.toCondition(userId));
        // project data
        var projectIdList = data.map(UserFavoriteProject::getProjectId).toList();
        var projects = projectDao.selectInIds(projectIdList);
        var projectMapById = projects.stream()
                .collect(Collectors.toMap(Project::getId, Function.identity()));
        // dataSource data
        var dataSourceMapByProjectId = dataSourceDao.selectInProjectIds(projectIdList)
                .stream()
                .collect(Collectors.toMap(DataSource::getProjectId, Function.identity()));
        // project sync rule data
        var projectSyncRuleMapByProjectId = projectSyncRuleDao.selectInProjectIds(projectIdList)
                .stream()
                .collect(Collectors.toMap(ProjectSyncRule::getProjectId, Function.identity()));
        // group data
        var groupIdList = projects.stream().map(Project::getGroupId).collect(Collectors.toList());
        var groupMapById = groupDao.selectInIds(groupIdList)
                .stream()
                .collect(Collectors.toMap(Group::getId, Function.identity()));
        // response data
        return data.map(favorite -> {
            Project project = projectMapById.get(favorite.getProjectId());
            DataSource dataSource = dataSourceMapByProjectId.get(favorite.getProjectId());
            ProjectSyncRule projectSyncRule = projectSyncRuleMapByProjectId.get(favorite.getProjectId());
            Group group = null;
            if (project != null) {
                group = groupMapById.get(project.getGroupId());
            }
            return favoriteProjectPageResponseConverter.to(favorite, project, dataSource, projectSyncRule, group);
        });
    }

    @Transactional
    public void addFavorites(Integer projectId, Integer userId) {
        if (!projectDao.existsById(projectId)) {
            throw DomainErrors.PROJECT_NOT_FOUND.exception();
        }
        if (!userFavoriteProjectDao.exists(userId, projectId)) {
            userFavoriteProjectDao.insert(userId, projectId);
        }
    }

    @Transactional

    public void removeFavorites(Integer projectId, Integer userId) {
        if (userFavoriteProjectDao.exists(userId, projectId)) {
            userFavoriteProjectDao.delete(userId, projectId);
        }
    }

}

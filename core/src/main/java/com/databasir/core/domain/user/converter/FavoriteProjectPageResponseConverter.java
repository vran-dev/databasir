package com.databasir.core.domain.user.converter;

import com.databasir.core.domain.user.data.FavoriteProjectPageResponse;
import com.databasir.dao.tables.pojos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteProjectPageResponseConverter {

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectName", source = "project.name")
    @Mapping(target = "projectDescription", source = "project.description")
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "createAt", source = "favoriteProject.createAt")
    FavoriteProjectPageResponse to(UserFavoriteProjectPojo favoriteProject,
                                   ProjectPojo project,
                                   DataSourcePojo dataSource,
                                   ProjectSyncRulePojo projectSyncRule,
                                   GroupPojo group);
}

package com.databasir.core.domain.user.converter;

import com.databasir.core.domain.user.data.FavoriteProjectPageResponse;
import com.databasir.dao.tables.pojos.DataSource;
import com.databasir.dao.tables.pojos.Group;
import com.databasir.dao.tables.pojos.Project;
import com.databasir.dao.tables.pojos.ProjectSyncRule;
import com.databasir.dao.tables.pojos.UserFavoriteProject;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class FavoriteProjectPageResponseConverterImpl implements FavoriteProjectPageResponseConverter {

    @Override
    public FavoriteProjectPageResponse to(UserFavoriteProject favoriteProject, Project project, DataSource dataSource, ProjectSyncRule projectSyncRule, Group group) {
        if ( favoriteProject == null && project == null && dataSource == null && projectSyncRule == null && group == null ) {
            return null;
        }

        FavoriteProjectPageResponse favoriteProjectPageResponse = new FavoriteProjectPageResponse();

        if ( favoriteProject != null ) {
            favoriteProjectPageResponse.setCreateAt( favoriteProject.getCreateAt() );
        }
        if ( project != null ) {
            favoriteProjectPageResponse.setProjectId( project.getId() );
            favoriteProjectPageResponse.setProjectName( project.getName() );
            favoriteProjectPageResponse.setProjectDescription( project.getDescription() );
        }
        if ( dataSource != null ) {
            favoriteProjectPageResponse.setDatabaseName( dataSource.getDatabaseName() );
            favoriteProjectPageResponse.setDatabaseType( dataSource.getDatabaseType() );
        }
        if ( projectSyncRule != null ) {
            favoriteProjectPageResponse.setAutoSyncCron( projectSyncRule.getAutoSyncCron() );
            favoriteProjectPageResponse.setIsAutoSync( projectSyncRule.getIsAutoSync() );
        }
        if ( group != null ) {
            favoriteProjectPageResponse.setGroupId( group.getId() );
            favoriteProjectPageResponse.setGroupName( group.getName() );
        }

        return favoriteProjectPageResponse;
    }
}

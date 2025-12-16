package com.databasir.core.domain.project.converter;

import com.databasir.core.domain.project.data.ProjectCreateRequest;
import com.databasir.core.domain.project.data.ProjectCreateRequest.ProjectSyncRuleCreateRequest;
import com.databasir.core.domain.project.data.ProjectUpdateRequest;
import com.databasir.core.domain.project.data.ProjectUpdateRequest.ProjectSyncRuleUpdateRequest;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.Project;
import com.databasir.dao.tables.pojos.ProjectSyncRule;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ProjectConverterImpl implements ProjectConverter {

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public Project of(ProjectCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Project project = new Project();

        project.setName( request.getName() );
        project.setDescription( optionToEmpty( request.getDescription() ) );
        project.setGroupId( request.getGroupId() );

        return project;
    }

    @Override
    public Project of(ProjectUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        Project project = new Project();

        project.setId( request.getId() );
        project.setName( request.getName() );
        project.setDescription( optionToEmpty( request.getDescription() ) );

        return project;
    }

    @Override
    public ProjectSyncRule of(ProjectSyncRuleCreateRequest request, Integer projectId) {
        if ( request == null && projectId == null ) {
            return null;
        }

        ProjectSyncRule projectSyncRule = new ProjectSyncRule();

        if ( request != null ) {
            projectSyncRule.setIgnoreTableNameRegexArray( jsonConverter.toJson( request.getIgnoreTableNameRegexes() ) );
            projectSyncRule.setIgnoreColumnNameRegexArray( jsonConverter.toJson( request.getIgnoreColumnNameRegexes() ) );
            projectSyncRule.setIsAutoSync( request.getIsAutoSync() );
            projectSyncRule.setAutoSyncCron( request.getAutoSyncCron() );
        }
        if ( projectId != null ) {
            projectSyncRule.setProjectId( projectId );
        }

        return projectSyncRule;
    }

    @Override
    public ProjectSyncRule of(ProjectSyncRuleUpdateRequest request, Integer projectId) {
        if ( request == null && projectId == null ) {
            return null;
        }

        ProjectSyncRule projectSyncRule = new ProjectSyncRule();

        if ( request != null ) {
            projectSyncRule.setIgnoreTableNameRegexArray( jsonConverter.toJson( request.getIgnoreTableNameRegexes() ) );
            projectSyncRule.setIgnoreColumnNameRegexArray( jsonConverter.toJson( request.getIgnoreColumnNameRegexes() ) );
            projectSyncRule.setIsAutoSync( request.getIsAutoSync() );
            projectSyncRule.setAutoSyncCron( request.getAutoSyncCron() );
        }
        if ( projectId != null ) {
            projectSyncRule.setProjectId( projectId );
        }

        return projectSyncRule;
    }
}

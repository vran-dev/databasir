package com.databasir.core.domain.project.converter;

import com.databasir.core.domain.project.data.DataSourcePropertyValue;
import com.databasir.core.domain.project.data.ProjectDetailResponse;
import com.databasir.core.domain.project.data.ProjectDetailResponse.DataSourceResponse;
import com.databasir.core.domain.project.data.ProjectDetailResponse.ProjectSyncRuleResponse;
import com.databasir.core.domain.project.data.ProjectSimpleResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.DataSource;
import com.databasir.dao.tables.pojos.DataSourceProperty;
import com.databasir.dao.tables.pojos.Project;
import com.databasir.dao.tables.pojos.ProjectSyncRule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ProjectResponseConverterImpl implements ProjectResponseConverter {

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public ProjectDetailResponse toResponse(Project database, DataSourceResponse dataSource, ProjectSyncRuleResponse projectSyncRule) {
        if ( database == null && dataSource == null && projectSyncRule == null ) {
            return null;
        }

        ProjectDetailResponse projectDetailResponse = new ProjectDetailResponse();

        if ( database != null ) {
            projectDetailResponse.setId( database.getId() );
            projectDetailResponse.setCreateAt( database.getCreateAt() );
            projectDetailResponse.setDescription( database.getDescription() );
            projectDetailResponse.setGroupId( database.getGroupId() );
            projectDetailResponse.setName( database.getName() );
        }
        if ( dataSource != null ) {
            projectDetailResponse.setDataSource( dataSource );
        }
        if ( projectSyncRule != null ) {
            projectDetailResponse.setProjectSyncRule( projectSyncRule );
        }

        return projectDetailResponse;
    }

    @Override
    public DataSourceResponse toResponse(DataSource dataSource, List<DataSourceProperty> properties) {
        if ( dataSource == null && properties == null ) {
            return null;
        }

        DataSourceResponse dataSourceResponse = new DataSourceResponse();

        if ( dataSource != null ) {
            dataSourceResponse.setCreateAt( dataSource.getCreateAt() );
            dataSourceResponse.setDatabaseName( dataSource.getDatabaseName() );
            dataSourceResponse.setDatabaseType( dataSource.getDatabaseType() );
            dataSourceResponse.setId( dataSource.getId() );
            dataSourceResponse.setSchemaName( dataSource.getSchemaName() );
            dataSourceResponse.setUpdateAt( dataSource.getUpdateAt() );
            dataSourceResponse.setUrl( dataSource.getUrl() );
            dataSourceResponse.setUsername( dataSource.getUsername() );
        }
        if ( properties != null ) {
            dataSourceResponse.setProperties( dataSourcePropertyListToDataSourcePropertyValueList( properties ) );
        }

        return dataSourceResponse;
    }

    @Override
    public ProjectSyncRuleResponse toResponse(ProjectSyncRule rule) {
        if ( rule == null ) {
            return null;
        }

        ProjectSyncRuleResponse projectSyncRuleResponse = new ProjectSyncRuleResponse();

        projectSyncRuleResponse.setIgnoreTableNameRegexes( jsonConverter.fromJson( rule.getIgnoreTableNameRegexArray() ) );
        projectSyncRuleResponse.setIgnoreColumnNameRegexes( jsonConverter.fromJson( rule.getIgnoreColumnNameRegexArray() ) );
        projectSyncRuleResponse.setAutoSyncCron( rule.getAutoSyncCron() );
        projectSyncRuleResponse.setCreateAt( rule.getCreateAt() );
        projectSyncRuleResponse.setId( rule.getId() );
        projectSyncRuleResponse.setIsAutoSync( rule.getIsAutoSync() );

        return projectSyncRuleResponse;
    }

    @Override
    public ProjectSimpleResponse toSimple(Project project, DataSource dataSource, ProjectSyncRule syncRule, Boolean isFavorite) {
        if ( project == null && dataSource == null && syncRule == null && isFavorite == null ) {
            return null;
        }

        ProjectSimpleResponse projectSimpleResponse = new ProjectSimpleResponse();

        if ( project != null ) {
            projectSimpleResponse.setId( project.getId() );
            projectSimpleResponse.setCreateAt( project.getCreateAt() );
            projectSimpleResponse.setDescription( project.getDescription() );
            projectSimpleResponse.setName( project.getName() );
        }
        if ( dataSource != null ) {
            projectSimpleResponse.setDatabaseName( dataSource.getDatabaseName() );
            projectSimpleResponse.setDatabaseType( dataSource.getDatabaseType() );
            projectSimpleResponse.setSchemaName( dataSource.getSchemaName() );
        }
        if ( syncRule != null ) {
            projectSimpleResponse.setAutoSyncCron( syncRule.getAutoSyncCron() );
            projectSimpleResponse.setIsAutoSync( syncRule.getIsAutoSync() );
        }
        if ( isFavorite != null ) {
            projectSimpleResponse.setIsFavorite( isFavorite );
        }

        return projectSimpleResponse;
    }

    protected DataSourcePropertyValue dataSourcePropertyToDataSourcePropertyValue(DataSourceProperty dataSourceProperty) {
        if ( dataSourceProperty == null ) {
            return null;
        }

        DataSourcePropertyValue dataSourcePropertyValue = new DataSourcePropertyValue();

        dataSourcePropertyValue.setKey( dataSourceProperty.getKey() );
        dataSourcePropertyValue.setValue( dataSourceProperty.getValue() );

        return dataSourcePropertyValue;
    }

    protected List<DataSourcePropertyValue> dataSourcePropertyListToDataSourcePropertyValueList(List<DataSourceProperty> list) {
        if ( list == null ) {
            return null;
        }

        List<DataSourcePropertyValue> list1 = new ArrayList<DataSourcePropertyValue>( list.size() );
        for ( DataSourceProperty dataSourceProperty : list ) {
            list1.add( dataSourcePropertyToDataSourcePropertyValue( dataSourceProperty ) );
        }

        return list1;
    }
}

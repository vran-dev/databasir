package com.databasir.core.domain.project.converter;

import com.databasir.core.domain.project.data.DataSourcePropertyValue;
import com.databasir.core.domain.project.data.ProjectCreateRequest.DataSourceCreateRequest;
import com.databasir.core.domain.project.data.ProjectUpdateRequest.DataSourceUpdateRequest;
import com.databasir.dao.tables.pojos.DataSource;
import com.databasir.dao.tables.pojos.DataSourceProperty;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DataSourceConverterImpl implements DataSourceConverter {

    @Override
    public DataSource of(DataSourceCreateRequest request, String password, Integer projectId) {
        if ( request == null && password == null && projectId == null ) {
            return null;
        }

        DataSource dataSource = new DataSource();

        if ( request != null ) {
            dataSource.setDatabaseName( request.getDatabaseName() );
            dataSource.setSchemaName( request.getSchemaName() );
            dataSource.setDatabaseType( request.getDatabaseType() );
            dataSource.setUrl( request.getUrl() );
            dataSource.setUsername( request.getUsername() );
        }
        if ( password != null ) {
            dataSource.setPassword( password );
        }
        if ( projectId != null ) {
            dataSource.setProjectId( projectId );
        }

        return dataSource;
    }

    @Override
    public DataSource of(DataSourceUpdateRequest request, String password, Integer projectId) {
        if ( request == null && password == null && projectId == null ) {
            return null;
        }

        DataSource dataSource = new DataSource();

        if ( request != null ) {
            dataSource.setDatabaseName( request.getDatabaseName() );
            dataSource.setSchemaName( request.getSchemaName() );
            dataSource.setDatabaseType( request.getDatabaseType() );
            dataSource.setUrl( request.getUrl() );
            dataSource.setUsername( request.getUsername() );
        }
        if ( password != null ) {
            dataSource.setPassword( password );
        }
        if ( projectId != null ) {
            dataSource.setProjectId( projectId );
        }

        return dataSource;
    }

    @Override
    public DataSourceProperty of(DataSourcePropertyValue propertyValues, Integer dataSourceId) {
        if ( propertyValues == null && dataSourceId == null ) {
            return null;
        }

        DataSourceProperty dataSourceProperty = new DataSourceProperty();

        if ( propertyValues != null ) {
            dataSourceProperty.setKey( propertyValues.getKey() );
            dataSourceProperty.setValue( propertyValues.getValue() );
        }
        if ( dataSourceId != null ) {
            dataSourceProperty.setDataSourceId( dataSourceId );
        }

        return dataSourceProperty;
    }
}

package com.databasir.core.domain.database.converter;

import com.databasir.core.domain.database.data.DatabaseTypeCreateRequest;
import com.databasir.core.domain.database.data.DatabaseTypeDetailResponse;
import com.databasir.core.domain.database.data.DatabaseTypePageResponse;
import com.databasir.core.domain.database.data.DatabaseTypeUpdateRequest;
import com.databasir.dao.tables.pojos.DatabaseType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DatabaseTypeConverterImpl implements DatabaseTypeConverter {

    @Override
    public DatabaseType of(DatabaseTypeCreateRequest request, String jdbcDriverFilePath) {
        if ( request == null && jdbcDriverFilePath == null ) {
            return null;
        }

        DatabaseType databaseType = new DatabaseType();

        if ( request != null ) {
            databaseType.setDatabaseType( request.getDatabaseType() );
            databaseType.setIcon( request.getIcon() );
            databaseType.setDescription( request.getDescription() );
            databaseType.setJdbcDriverFileUrl( request.getJdbcDriverFileUrl() );
            databaseType.setJdbcDriverClassName( request.getJdbcDriverClassName() );
            databaseType.setJdbcProtocol( request.getJdbcProtocol() );
            databaseType.setUrlPattern( request.getUrlPattern() );
        }
        if ( jdbcDriverFilePath != null ) {
            databaseType.setJdbcDriverFilePath( jdbcDriverFilePath );
        }

        return databaseType;
    }

    @Override
    public DatabaseType of(DatabaseTypeUpdateRequest request, String jdbcDriverFilePath) {
        if ( request == null && jdbcDriverFilePath == null ) {
            return null;
        }

        DatabaseType databaseType = new DatabaseType();

        if ( request != null ) {
            databaseType.setId( request.getId() );
            databaseType.setDatabaseType( request.getDatabaseType() );
            databaseType.setIcon( request.getIcon() );
            databaseType.setDescription( request.getDescription() );
            databaseType.setJdbcDriverFileUrl( request.getJdbcDriverFileUrl() );
            databaseType.setJdbcDriverClassName( request.getJdbcDriverClassName() );
            databaseType.setJdbcProtocol( request.getJdbcProtocol() );
            databaseType.setUrlPattern( request.getUrlPattern() );
        }
        if ( jdbcDriverFilePath != null ) {
            databaseType.setJdbcDriverFilePath( jdbcDriverFilePath );
        }

        return databaseType;
    }

    @Override
    public DatabaseType of(DatabaseTypeUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        DatabaseType databaseType = new DatabaseType();

        databaseType.setId( request.getId() );
        databaseType.setDatabaseType( request.getDatabaseType() );
        databaseType.setIcon( request.getIcon() );
        databaseType.setDescription( request.getDescription() );
        databaseType.setJdbcDriverFileUrl( request.getJdbcDriverFileUrl() );
        databaseType.setJdbcDriverFilePath( request.getJdbcDriverFilePath() );
        databaseType.setJdbcDriverClassName( request.getJdbcDriverClassName() );
        databaseType.setJdbcProtocol( request.getJdbcProtocol() );
        databaseType.setUrlPattern( request.getUrlPattern() );

        return databaseType;
    }

    @Override
    public DatabaseTypeDetailResponse toDetailResponse(DatabaseType data) {
        if ( data == null ) {
            return null;
        }

        DatabaseTypeDetailResponse databaseTypeDetailResponse = new DatabaseTypeDetailResponse();

        databaseTypeDetailResponse.setCreateAt( data.getCreateAt() );
        databaseTypeDetailResponse.setDatabaseType( data.getDatabaseType() );
        databaseTypeDetailResponse.setDeleted( data.getDeleted() );
        databaseTypeDetailResponse.setDeletedToken( data.getDeletedToken() );
        databaseTypeDetailResponse.setDescription( data.getDescription() );
        databaseTypeDetailResponse.setIcon( data.getIcon() );
        databaseTypeDetailResponse.setId( data.getId() );
        databaseTypeDetailResponse.setJdbcDriverClassName( data.getJdbcDriverClassName() );
        databaseTypeDetailResponse.setJdbcDriverFilePath( data.getJdbcDriverFilePath() );
        databaseTypeDetailResponse.setJdbcDriverFileUrl( data.getJdbcDriverFileUrl() );
        databaseTypeDetailResponse.setJdbcProtocol( data.getJdbcProtocol() );
        databaseTypeDetailResponse.setUpdateAt( data.getUpdateAt() );
        databaseTypeDetailResponse.setUrlPattern( data.getUrlPattern() );

        return databaseTypeDetailResponse;
    }

    @Override
    public DatabaseTypePageResponse toPageResponse(DatabaseType pojo, Integer projectCount) {
        if ( pojo == null && projectCount == null ) {
            return null;
        }

        DatabaseTypePageResponse databaseTypePageResponse = new DatabaseTypePageResponse();

        if ( pojo != null ) {
            databaseTypePageResponse.setCreateAt( pojo.getCreateAt() );
            databaseTypePageResponse.setDatabaseType( pojo.getDatabaseType() );
            databaseTypePageResponse.setDeleted( pojo.getDeleted() );
            databaseTypePageResponse.setDeletedToken( pojo.getDeletedToken() );
            databaseTypePageResponse.setDescription( pojo.getDescription() );
            databaseTypePageResponse.setIcon( pojo.getIcon() );
            databaseTypePageResponse.setId( pojo.getId() );
            databaseTypePageResponse.setJdbcDriverClassName( pojo.getJdbcDriverClassName() );
            databaseTypePageResponse.setJdbcDriverFilePath( pojo.getJdbcDriverFilePath() );
            databaseTypePageResponse.setJdbcDriverFileUrl( pojo.getJdbcDriverFileUrl() );
            databaseTypePageResponse.setJdbcProtocol( pojo.getJdbcProtocol() );
            databaseTypePageResponse.setUpdateAt( pojo.getUpdateAt() );
            databaseTypePageResponse.setUrlPattern( pojo.getUrlPattern() );
        }
        if ( projectCount != null ) {
            databaseTypePageResponse.setProjectCount( projectCount );
        }

        return databaseTypePageResponse;
    }
}

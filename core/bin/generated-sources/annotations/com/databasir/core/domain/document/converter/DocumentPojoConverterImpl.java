package com.databasir.core.domain.document.converter;

import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.data.ForeignKeyMeta;
import com.databasir.core.meta.data.IndexMeta;
import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.meta.data.TriggerMeta;
import com.databasir.dao.tables.pojos.DatabaseDocument;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import com.databasir.dao.tables.pojos.TableForeignKeyDocument;
import com.databasir.dao.tables.pojos.TableIndexDocument;
import com.databasir.dao.tables.pojos.TableTriggerDocument;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DocumentPojoConverterImpl implements DocumentPojoConverter {

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public DatabaseDocument toDatabasePojo(Integer projectId, DatabaseMeta meta, Long version) {
        if ( projectId == null && meta == null && version == null ) {
            return null;
        }

        DatabaseDocument databaseDocument = new DatabaseDocument();

        if ( projectId != null ) {
            databaseDocument.setProjectId( projectId );
        }
        if ( meta != null ) {
            databaseDocument.setDatabaseName( meta.getDatabaseName() );
            databaseDocument.setSchemaName( meta.getSchemaName() );
            databaseDocument.setProductName( meta.getProductName() );
            databaseDocument.setProductVersion( meta.getProductVersion() );
        }
        if ( version != null ) {
            databaseDocument.setVersion( version );
        }
        databaseDocument.setIsArchive( false );

        return databaseDocument;
    }

    @Override
    public TableDocument toTablePojo(Integer databaseDocumentId, TableMeta meta) {
        if ( databaseDocumentId == null && meta == null ) {
            return null;
        }

        TableDocument tableDocument = new TableDocument();

        if ( databaseDocumentId != null ) {
            tableDocument.setDatabaseDocumentId( databaseDocumentId );
        }
        if ( meta != null ) {
            tableDocument.setName( meta.getName() );
            tableDocument.setType( meta.getType() );
            tableDocument.setComment( meta.getComment() );
        }

        return tableDocument;
    }

    @Override
    public TableColumnDocument toColumnPojo(Integer databaseDocumentId, Integer tableDocumentId, ColumnMeta meta) {
        if ( databaseDocumentId == null && tableDocumentId == null && meta == null ) {
            return null;
        }

        TableColumnDocument tableColumnDocument = new TableColumnDocument();

        if ( databaseDocumentId != null ) {
            tableColumnDocument.setDatabaseDocumentId( databaseDocumentId );
        }
        if ( tableDocumentId != null ) {
            tableColumnDocument.setTableDocumentId( tableDocumentId );
        }
        if ( meta != null ) {
            tableColumnDocument.setName( meta.getName() );
            tableColumnDocument.setType( meta.getType() );
            tableColumnDocument.setDataType( meta.getDataType() );
            tableColumnDocument.setComment( meta.getComment() );
            tableColumnDocument.setDefaultValue( meta.getDefaultValue() );
            tableColumnDocument.setSize( meta.getSize() );
            tableColumnDocument.setDecimalDigits( meta.getDecimalDigits() );
            tableColumnDocument.setIsPrimaryKey( meta.getIsPrimaryKey() );
            tableColumnDocument.setNullable( meta.getNullable() );
            tableColumnDocument.setAutoIncrement( meta.getAutoIncrement() );
        }

        return tableColumnDocument;
    }

    @Override
    public TableIndexDocument toIndexPojo(Integer databaseDocumentId, Integer tableDocumentId, IndexMeta meta) {
        if ( databaseDocumentId == null && tableDocumentId == null && meta == null ) {
            return null;
        }

        TableIndexDocument tableIndexDocument = new TableIndexDocument();

        if ( databaseDocumentId != null ) {
            tableIndexDocument.setDatabaseDocumentId( databaseDocumentId );
        }
        if ( tableDocumentId != null ) {
            tableIndexDocument.setTableDocumentId( tableDocumentId );
        }
        if ( meta != null ) {
            tableIndexDocument.setIsUnique( meta.getIsUniqueKey() );
            tableIndexDocument.setColumnNameArray( jsonConverter.toJson( meta.getColumnNames() ) );
            tableIndexDocument.setName( meta.getName() );
        }

        return tableIndexDocument;
    }

    @Override
    public TableTriggerDocument toTriggerPojo(Integer databaseDocumentId, Integer tableDocumentId, TriggerMeta meta) {
        if ( databaseDocumentId == null && tableDocumentId == null && meta == null ) {
            return null;
        }

        TableTriggerDocument tableTriggerDocument = new TableTriggerDocument();

        if ( databaseDocumentId != null ) {
            tableTriggerDocument.setDatabaseDocumentId( databaseDocumentId );
        }
        if ( tableDocumentId != null ) {
            tableTriggerDocument.setTableDocumentId( tableDocumentId );
        }
        if ( meta != null ) {
            tableTriggerDocument.setTriggerCreateAt( meta.getCreateAt() );
            tableTriggerDocument.setName( meta.getName() );
            tableTriggerDocument.setTiming( meta.getTiming() );
            tableTriggerDocument.setManipulation( meta.getManipulation() );
            tableTriggerDocument.setStatement( meta.getStatement() );
        }

        return tableTriggerDocument;
    }

    @Override
    public TableForeignKeyDocument toForeignKeyPojo(Integer databaseDocumentId, Integer tableDocumentId, ForeignKeyMeta foreignKey) {
        if ( databaseDocumentId == null && tableDocumentId == null && foreignKey == null ) {
            return null;
        }

        TableForeignKeyDocument tableForeignKeyDocument = new TableForeignKeyDocument();

        if ( databaseDocumentId != null ) {
            tableForeignKeyDocument.setDatabaseDocumentId( databaseDocumentId );
        }
        if ( tableDocumentId != null ) {
            tableForeignKeyDocument.setTableDocumentId( tableDocumentId );
        }
        if ( foreignKey != null ) {
            tableForeignKeyDocument.setKeySeq( foreignKey.getKeySeq() );
            tableForeignKeyDocument.setFkName( foreignKey.getFkName() );
            tableForeignKeyDocument.setFkTableName( foreignKey.getFkTableName() );
            tableForeignKeyDocument.setFkColumnName( foreignKey.getFkColumnName() );
            tableForeignKeyDocument.setPkName( foreignKey.getPkName() );
            tableForeignKeyDocument.setPkTableName( foreignKey.getPkTableName() );
            tableForeignKeyDocument.setPkColumnName( foreignKey.getPkColumnName() );
            tableForeignKeyDocument.setUpdateRule( foreignKey.getUpdateRule() );
            tableForeignKeyDocument.setDeleteRule( foreignKey.getDeleteRule() );
        }

        return tableForeignKeyDocument;
    }
}

package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.diff.ColumnDocDiff;
import com.databasir.core.domain.document.data.diff.DatabaseDocDiff;
import com.databasir.core.domain.document.data.diff.ForeignKeyDocDiff;
import com.databasir.core.domain.document.data.diff.IndexDocDiff;
import com.databasir.core.domain.document.data.diff.TableDocDiff;
import com.databasir.core.domain.document.data.diff.TableDocDiff.TableDocDiffBuilder;
import com.databasir.core.domain.document.data.diff.TriggerDocDiff;
import com.databasir.dao.tables.pojos.DatabaseDocument;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import com.databasir.dao.tables.pojos.TableForeignKeyDocument;
import com.databasir.dao.tables.pojos.TableIndexDocument;
import com.databasir.dao.tables.pojos.TableTriggerDocument;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DocumentDiffConverterImpl implements DocumentDiffConverter {

    @Override
    public DatabaseDocDiff of(DatabaseDocument databaseDocument, List<TableDocDiff> tables) {
        if ( databaseDocument == null && tables == null ) {
            return null;
        }

        DatabaseDocDiff databaseDocDiff = new DatabaseDocDiff();

        if ( databaseDocument != null ) {
            databaseDocDiff.setCreateAt( databaseDocument.getCreateAt() );
            databaseDocDiff.setDatabaseName( databaseDocument.getDatabaseName() );
            databaseDocDiff.setId( databaseDocument.getId() );
            databaseDocDiff.setProductName( databaseDocument.getProductName() );
            databaseDocDiff.setProductVersion( databaseDocument.getProductVersion() );
            databaseDocDiff.setProjectId( databaseDocument.getProjectId() );
            databaseDocDiff.setSchemaName( databaseDocument.getSchemaName() );
            databaseDocDiff.setUpdateAt( databaseDocument.getUpdateAt() );
            databaseDocDiff.setVersion( databaseDocument.getVersion() );
        }
        if ( tables != null ) {
            List<TableDocDiff> list = tables;
            if ( list != null ) {
                databaseDocDiff.setTables( new ArrayList<TableDocDiff>( list ) );
            }
        }

        return databaseDocDiff;
    }

    @Override
    public TableDocDiff of(TableDocument table, List<TableColumnDocument> columns, List<TableIndexDocument> indexes, List<TableTriggerDocument> triggers, List<TableForeignKeyDocument> foreignKeys) {
        if ( table == null && columns == null && indexes == null && triggers == null && foreignKeys == null ) {
            return null;
        }

        TableDocDiffBuilder tableDocDiff = TableDocDiff.builder();

        if ( table != null ) {
            tableDocDiff.comment( table.getComment() );
            tableDocDiff.createAt( table.getCreateAt() );
            tableDocDiff.id( table.getId() );
            tableDocDiff.name( table.getName() );
            tableDocDiff.type( table.getType() );
        }
        if ( columns != null ) {
            tableDocDiff.columns( tableColumnDocumentListToColumnDocDiffList( columns ) );
        }
        if ( indexes != null ) {
            tableDocDiff.indexes( tableIndexDocumentListToIndexDocDiffList( indexes ) );
        }
        if ( triggers != null ) {
            tableDocDiff.triggers( tableTriggerDocumentListToTriggerDocDiffList( triggers ) );
        }
        if ( foreignKeys != null ) {
            tableDocDiff.foreignKeys( tableForeignKeyDocumentListToForeignKeyDocDiffList( foreignKeys ) );
        }

        return tableDocDiff.build();
    }

    @Override
    public ColumnDocDiff of(TableColumnDocument pojo) {
        if ( pojo == null ) {
            return null;
        }

        ColumnDocDiff columnDocDiff = new ColumnDocDiff();

        columnDocDiff.setAutoIncrement( pojo.getAutoIncrement() );
        columnDocDiff.setComment( pojo.getComment() );
        columnDocDiff.setCreateAt( pojo.getCreateAt() );
        columnDocDiff.setDataType( pojo.getDataType() );
        columnDocDiff.setDatabaseDocumentId( pojo.getDatabaseDocumentId() );
        columnDocDiff.setDecimalDigits( pojo.getDecimalDigits() );
        columnDocDiff.setDefaultValue( pojo.getDefaultValue() );
        columnDocDiff.setId( pojo.getId() );
        columnDocDiff.setIsPrimaryKey( pojo.getIsPrimaryKey() );
        columnDocDiff.setName( pojo.getName() );
        columnDocDiff.setNullable( pojo.getNullable() );
        columnDocDiff.setSize( pojo.getSize() );
        columnDocDiff.setTableDocumentId( pojo.getTableDocumentId() );
        columnDocDiff.setType( pojo.getType() );

        return columnDocDiff;
    }

    @Override
    public IndexDocDiff of(TableIndexDocument pojo) {
        if ( pojo == null ) {
            return null;
        }

        IndexDocDiff indexDocDiff = new IndexDocDiff();

        indexDocDiff.setColumnNameArray( pojo.getColumnNameArray() );
        indexDocDiff.setCreateAt( pojo.getCreateAt() );
        indexDocDiff.setDatabaseDocumentId( pojo.getDatabaseDocumentId() );
        indexDocDiff.setId( pojo.getId() );
        indexDocDiff.setIsUnique( pojo.getIsUnique() );
        indexDocDiff.setName( pojo.getName() );
        indexDocDiff.setTableDocumentId( pojo.getTableDocumentId() );

        return indexDocDiff;
    }

    @Override
    public TriggerDocDiff of(TableTriggerDocument pojo) {
        if ( pojo == null ) {
            return null;
        }

        TriggerDocDiff triggerDocDiff = new TriggerDocDiff();

        triggerDocDiff.setCreateAt( pojo.getCreateAt() );
        triggerDocDiff.setDatabaseDocumentId( pojo.getDatabaseDocumentId() );
        triggerDocDiff.setId( pojo.getId() );
        triggerDocDiff.setManipulation( pojo.getManipulation() );
        triggerDocDiff.setName( pojo.getName() );
        triggerDocDiff.setStatement( pojo.getStatement() );
        triggerDocDiff.setTableDocumentId( pojo.getTableDocumentId() );
        triggerDocDiff.setTiming( pojo.getTiming() );
        triggerDocDiff.setTriggerCreateAt( pojo.getTriggerCreateAt() );

        return triggerDocDiff;
    }

    @Override
    public ForeignKeyDocDiff of(TableForeignKeyDocument pojo) {
        if ( pojo == null ) {
            return null;
        }

        ForeignKeyDocDiff foreignKeyDocDiff = new ForeignKeyDocDiff();

        foreignKeyDocDiff.setCreateAt( pojo.getCreateAt() );
        foreignKeyDocDiff.setDatabaseDocumentId( pojo.getDatabaseDocumentId() );
        foreignKeyDocDiff.setDeleteRule( pojo.getDeleteRule() );
        foreignKeyDocDiff.setFkColumnName( pojo.getFkColumnName() );
        foreignKeyDocDiff.setFkName( pojo.getFkName() );
        foreignKeyDocDiff.setFkTableName( pojo.getFkTableName() );
        foreignKeyDocDiff.setId( pojo.getId() );
        foreignKeyDocDiff.setKeySeq( pojo.getKeySeq() );
        foreignKeyDocDiff.setPkColumnName( pojo.getPkColumnName() );
        foreignKeyDocDiff.setPkName( pojo.getPkName() );
        foreignKeyDocDiff.setPkTableName( pojo.getPkTableName() );
        foreignKeyDocDiff.setTableDocumentId( pojo.getTableDocumentId() );
        foreignKeyDocDiff.setUpdateRule( pojo.getUpdateRule() );

        return foreignKeyDocDiff;
    }

    protected List<ColumnDocDiff> tableColumnDocumentListToColumnDocDiffList(List<TableColumnDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<ColumnDocDiff> list1 = new ArrayList<ColumnDocDiff>( list.size() );
        for ( TableColumnDocument tableColumnDocument : list ) {
            list1.add( of( tableColumnDocument ) );
        }

        return list1;
    }

    protected List<ForeignKeyDocDiff> tableForeignKeyDocumentListToForeignKeyDocDiffList(List<TableForeignKeyDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<ForeignKeyDocDiff> list1 = new ArrayList<ForeignKeyDocDiff>( list.size() );
        for ( TableForeignKeyDocument tableForeignKeyDocument : list ) {
            list1.add( of( tableForeignKeyDocument ) );
        }

        return list1;
    }

    protected List<IndexDocDiff> tableIndexDocumentListToIndexDocDiffList(List<TableIndexDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<IndexDocDiff> list1 = new ArrayList<IndexDocDiff>( list.size() );
        for ( TableIndexDocument tableIndexDocument : list ) {
            list1.add( of( tableIndexDocument ) );
        }

        return list1;
    }

    protected List<TriggerDocDiff> tableTriggerDocumentListToTriggerDocDiffList(List<TableTriggerDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<TriggerDocDiff> list1 = new ArrayList<TriggerDocDiff>( list.size() );
        for ( TableTriggerDocument tableTriggerDocument : list ) {
            list1.add( of( tableTriggerDocument ) );
        }

        return list1;
    }
}

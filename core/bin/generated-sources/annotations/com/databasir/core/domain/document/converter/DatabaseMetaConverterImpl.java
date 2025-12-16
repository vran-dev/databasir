package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.ColumnDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.ForeignKeyDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.IndexDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.TriggerDocumentResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.data.ColumnMeta.ColumnMetaBuilder;
import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.data.ForeignKeyMeta;
import com.databasir.core.meta.data.ForeignKeyMeta.ForeignKeyMetaBuilder;
import com.databasir.core.meta.data.IndexMeta;
import com.databasir.core.meta.data.IndexMeta.IndexMetaBuilder;
import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.meta.data.TableMeta.TableMetaBuilder;
import com.databasir.core.meta.data.TriggerMeta;
import com.databasir.core.meta.data.TriggerMeta.TriggerMetaBuilder;
import com.databasir.dao.tables.pojos.DatabaseDocument;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import com.databasir.dao.tables.pojos.TableForeignKeyDocument;
import com.databasir.dao.tables.pojos.TableIndexDocument;
import com.databasir.dao.tables.pojos.TableTriggerDocument;
import java.time.format.DateTimeFormatter;
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
public class DatabaseMetaConverterImpl implements DatabaseMetaConverter {

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public DatabaseMeta of(DatabaseDocument database, List<TableMeta> tables) {
        if ( database == null && tables == null ) {
            return null;
        }

        DatabaseMeta databaseMeta = new DatabaseMeta();

        if ( database != null ) {
            databaseMeta.setProductName( database.getProductName() );
            databaseMeta.setProductVersion( database.getProductVersion() );
            databaseMeta.setDatabaseName( database.getDatabaseName() );
            databaseMeta.setSchemaName( database.getSchemaName() );
        }
        if ( tables != null ) {
            List<TableMeta> list = tables;
            if ( list != null ) {
                databaseMeta.setTables( new ArrayList<TableMeta>( list ) );
            }
        }

        return databaseMeta;
    }

    @Override
    public TableMeta of(TableDocument table, List<TableColumnDocument> columns, List<TableIndexDocument> indexes, List<TableTriggerDocument> triggers, List<TableForeignKeyDocument> foreignKeys) {
        if ( table == null && columns == null && indexes == null && triggers == null && foreignKeys == null ) {
            return null;
        }

        TableMeta tableMeta = new TableMeta();

        if ( table != null ) {
            tableMeta.setName( table.getName() );
            tableMeta.setType( table.getType() );
            tableMeta.setComment( table.getComment() );
        }
        if ( columns != null ) {
            tableMeta.setColumns( tableColumnDocumentListToColumnMetaList( columns ) );
        }
        if ( indexes != null ) {
            tableMeta.setIndexes( tableIndexDocumentListToIndexMetaList( indexes ) );
        }
        if ( triggers != null ) {
            tableMeta.setTriggers( tableTriggerDocumentListToTriggerMetaList( triggers ) );
        }
        if ( foreignKeys != null ) {
            tableMeta.setForeignKeys( tableForeignKeyDocumentListToForeignKeyMetaList( foreignKeys ) );
        }

        return tableMeta;
    }

    @Override
    public ColumnMeta of(TableColumnDocument pojo) {
        if ( pojo == null ) {
            return null;
        }

        ColumnMeta columnMeta = new ColumnMeta();

        columnMeta.setName( pojo.getName() );
        columnMeta.setComment( pojo.getComment() );
        columnMeta.setType( pojo.getType() );
        columnMeta.setDataType( pojo.getDataType() );
        columnMeta.setDefaultValue( pojo.getDefaultValue() );
        columnMeta.setSize( pojo.getSize() );
        columnMeta.setDecimalDigits( pojo.getDecimalDigits() );
        columnMeta.setNullable( pojo.getNullable() );
        columnMeta.setAutoIncrement( pojo.getAutoIncrement() );
        columnMeta.setIsPrimaryKey( pojo.getIsPrimaryKey() );

        return columnMeta;
    }

    @Override
    public IndexMeta of(TableIndexDocument pojo) {
        if ( pojo == null ) {
            return null;
        }

        IndexMeta indexMeta = new IndexMeta();

        indexMeta.setIsUniqueKey( pojo.getIsUnique() );
        indexMeta.setColumnNames( jsonConverter.fromJson( pojo.getColumnNameArray() ) );
        indexMeta.setName( pojo.getName() );

        return indexMeta;
    }

    @Override
    public TriggerMeta of(TableTriggerDocument pojo) {
        if ( pojo == null ) {
            return null;
        }

        TriggerMeta triggerMeta = new TriggerMeta();

        triggerMeta.setCreateAt( pojo.getTriggerCreateAt() );
        triggerMeta.setName( pojo.getName() );
        triggerMeta.setTiming( pojo.getTiming() );
        triggerMeta.setManipulation( pojo.getManipulation() );
        triggerMeta.setStatement( pojo.getStatement() );

        return triggerMeta;
    }

    @Override
    public List<TableMeta> of(List<TableDocumentResponse> table) {
        if ( table == null ) {
            return null;
        }

        List<TableMeta> list = new ArrayList<TableMeta>( table.size() );
        for ( TableDocumentResponse tableDocumentResponse : table ) {
            list.add( of( tableDocumentResponse ) );
        }

        return list;
    }

    @Override
    public TableMeta of(TableDocumentResponse table) {
        if ( table == null ) {
            return null;
        }

        TableMetaBuilder tableMeta = TableMeta.builder();

        tableMeta.name( table.getName() );
        tableMeta.type( table.getType() );
        tableMeta.comment( table.getComment() );
        tableMeta.columns( columnDocumentResponseListToColumnMetaList( table.getColumns() ) );
        tableMeta.triggers( triggerDocumentResponseListToTriggerMetaList( table.getTriggers() ) );
        tableMeta.indexes( indexDocumentResponseListToIndexMetaList( table.getIndexes() ) );
        tableMeta.foreignKeys( foreignKeyDocumentResponseListToForeignKeyMetaList( table.getForeignKeys() ) );

        return tableMeta.build();
    }

    protected List<ColumnMeta> tableColumnDocumentListToColumnMetaList(List<TableColumnDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<ColumnMeta> list1 = new ArrayList<ColumnMeta>( list.size() );
        for ( TableColumnDocument tableColumnDocument : list ) {
            list1.add( of( tableColumnDocument ) );
        }

        return list1;
    }

    protected List<TriggerMeta> tableTriggerDocumentListToTriggerMetaList(List<TableTriggerDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<TriggerMeta> list1 = new ArrayList<TriggerMeta>( list.size() );
        for ( TableTriggerDocument tableTriggerDocument : list ) {
            list1.add( of( tableTriggerDocument ) );
        }

        return list1;
    }

    protected List<IndexMeta> tableIndexDocumentListToIndexMetaList(List<TableIndexDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<IndexMeta> list1 = new ArrayList<IndexMeta>( list.size() );
        for ( TableIndexDocument tableIndexDocument : list ) {
            list1.add( of( tableIndexDocument ) );
        }

        return list1;
    }

    protected ForeignKeyMeta tableForeignKeyDocumentToForeignKeyMeta(TableForeignKeyDocument tableForeignKeyDocument) {
        if ( tableForeignKeyDocument == null ) {
            return null;
        }

        ForeignKeyMeta foreignKeyMeta = new ForeignKeyMeta();

        foreignKeyMeta.setKeySeq( tableForeignKeyDocument.getKeySeq() );
        foreignKeyMeta.setPkName( tableForeignKeyDocument.getPkName() );
        foreignKeyMeta.setPkTableName( tableForeignKeyDocument.getPkTableName() );
        foreignKeyMeta.setPkColumnName( tableForeignKeyDocument.getPkColumnName() );
        foreignKeyMeta.setFkName( tableForeignKeyDocument.getFkName() );
        foreignKeyMeta.setFkTableName( tableForeignKeyDocument.getFkTableName() );
        foreignKeyMeta.setFkColumnName( tableForeignKeyDocument.getFkColumnName() );
        foreignKeyMeta.setUpdateRule( tableForeignKeyDocument.getUpdateRule() );
        foreignKeyMeta.setDeleteRule( tableForeignKeyDocument.getDeleteRule() );

        return foreignKeyMeta;
    }

    protected List<ForeignKeyMeta> tableForeignKeyDocumentListToForeignKeyMetaList(List<TableForeignKeyDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<ForeignKeyMeta> list1 = new ArrayList<ForeignKeyMeta>( list.size() );
        for ( TableForeignKeyDocument tableForeignKeyDocument : list ) {
            list1.add( tableForeignKeyDocumentToForeignKeyMeta( tableForeignKeyDocument ) );
        }

        return list1;
    }

    protected ColumnMeta columnDocumentResponseToColumnMeta(ColumnDocumentResponse columnDocumentResponse) {
        if ( columnDocumentResponse == null ) {
            return null;
        }

        ColumnMetaBuilder columnMeta = ColumnMeta.builder();

        columnMeta.name( columnDocumentResponse.getName() );
        columnMeta.comment( columnDocumentResponse.getComment() );
        columnMeta.type( columnDocumentResponse.getType() );
        columnMeta.defaultValue( columnDocumentResponse.getDefaultValue() );
        columnMeta.size( columnDocumentResponse.getSize() );
        columnMeta.decimalDigits( columnDocumentResponse.getDecimalDigits() );
        columnMeta.nullable( columnDocumentResponse.getNullable() );
        columnMeta.autoIncrement( columnDocumentResponse.getAutoIncrement() );
        columnMeta.isPrimaryKey( columnDocumentResponse.getIsPrimaryKey() );

        return columnMeta.build();
    }

    protected List<ColumnMeta> columnDocumentResponseListToColumnMetaList(List<ColumnDocumentResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<ColumnMeta> list1 = new ArrayList<ColumnMeta>( list.size() );
        for ( ColumnDocumentResponse columnDocumentResponse : list ) {
            list1.add( columnDocumentResponseToColumnMeta( columnDocumentResponse ) );
        }

        return list1;
    }

    protected TriggerMeta triggerDocumentResponseToTriggerMeta(TriggerDocumentResponse triggerDocumentResponse) {
        if ( triggerDocumentResponse == null ) {
            return null;
        }

        TriggerMetaBuilder triggerMeta = TriggerMeta.builder();

        triggerMeta.name( triggerDocumentResponse.getName() );
        triggerMeta.timing( triggerDocumentResponse.getTiming() );
        triggerMeta.manipulation( triggerDocumentResponse.getManipulation() );
        triggerMeta.statement( triggerDocumentResponse.getStatement() );
        if ( triggerDocumentResponse.getCreateAt() != null ) {
            triggerMeta.createAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( triggerDocumentResponse.getCreateAt() ) );
        }

        return triggerMeta.build();
    }

    protected List<TriggerMeta> triggerDocumentResponseListToTriggerMetaList(List<TriggerDocumentResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<TriggerMeta> list1 = new ArrayList<TriggerMeta>( list.size() );
        for ( TriggerDocumentResponse triggerDocumentResponse : list ) {
            list1.add( triggerDocumentResponseToTriggerMeta( triggerDocumentResponse ) );
        }

        return list1;
    }

    protected IndexMeta indexDocumentResponseToIndexMeta(IndexDocumentResponse indexDocumentResponse) {
        if ( indexDocumentResponse == null ) {
            return null;
        }

        IndexMetaBuilder indexMeta = IndexMeta.builder();

        indexMeta.name( indexDocumentResponse.getName() );
        List<String> list = indexDocumentResponse.getColumnNames();
        if ( list != null ) {
            indexMeta.columnNames( new ArrayList<String>( list ) );
        }

        return indexMeta.build();
    }

    protected List<IndexMeta> indexDocumentResponseListToIndexMetaList(List<IndexDocumentResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<IndexMeta> list1 = new ArrayList<IndexMeta>( list.size() );
        for ( IndexDocumentResponse indexDocumentResponse : list ) {
            list1.add( indexDocumentResponseToIndexMeta( indexDocumentResponse ) );
        }

        return list1;
    }

    protected ForeignKeyMeta foreignKeyDocumentResponseToForeignKeyMeta(ForeignKeyDocumentResponse foreignKeyDocumentResponse) {
        if ( foreignKeyDocumentResponse == null ) {
            return null;
        }

        ForeignKeyMetaBuilder foreignKeyMeta = ForeignKeyMeta.builder();

        foreignKeyMeta.keySeq( foreignKeyDocumentResponse.getKeySeq() );
        foreignKeyMeta.pkName( foreignKeyDocumentResponse.getPkName() );
        foreignKeyMeta.pkTableName( foreignKeyDocumentResponse.getPkTableName() );
        foreignKeyMeta.pkColumnName( foreignKeyDocumentResponse.getPkColumnName() );
        foreignKeyMeta.fkName( foreignKeyDocumentResponse.getFkName() );
        foreignKeyMeta.fkTableName( foreignKeyDocumentResponse.getFkTableName() );
        foreignKeyMeta.fkColumnName( foreignKeyDocumentResponse.getFkColumnName() );
        foreignKeyMeta.updateRule( foreignKeyDocumentResponse.getUpdateRule() );
        foreignKeyMeta.deleteRule( foreignKeyDocumentResponse.getDeleteRule() );

        return foreignKeyMeta.build();
    }

    protected List<ForeignKeyMeta> foreignKeyDocumentResponseListToForeignKeyMetaList(List<ForeignKeyDocumentResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<ForeignKeyMeta> list1 = new ArrayList<ForeignKeyMeta>( list.size() );
        for ( ForeignKeyDocumentResponse foreignKeyDocumentResponse : list ) {
            list1.add( foreignKeyDocumentResponseToForeignKeyMeta( foreignKeyDocumentResponse ) );
        }

        return list1;
    }
}

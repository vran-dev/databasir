package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentResponse.DatabaseDocumentResponseBuilder;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.ColumnDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.ColumnDocumentResponse.ColumnDocumentResponseBuilder;
import com.databasir.core.domain.document.data.TableDocumentResponse.ForeignKeyDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.ForeignKeyDocumentResponse.ForeignKeyDocumentResponseBuilder;
import com.databasir.core.domain.document.data.TableDocumentResponse.IndexDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.IndexDocumentResponse.IndexDocumentResponseBuilder;
import com.databasir.core.domain.document.data.TableDocumentResponse.TableDocumentResponseBuilder;
import com.databasir.core.domain.document.data.TableDocumentResponse.TriggerDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.TriggerDocumentResponse.TriggerDocumentResponseBuilder;
import com.databasir.core.domain.document.data.diff.ColumnDocDiff;
import com.databasir.core.domain.document.data.diff.ForeignKeyDocDiff;
import com.databasir.core.domain.document.data.diff.IndexDocDiff;
import com.databasir.core.domain.document.data.diff.TableDocDiff;
import com.databasir.core.domain.document.data.diff.TriggerDocDiff;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.DatabaseDocument;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import com.databasir.dao.tables.pojos.TableForeignKeyDocument;
import com.databasir.dao.tables.pojos.TableIndexDocument;
import com.databasir.dao.tables.pojos.TableTriggerDocument;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DocumentResponseConverterImpl implements DocumentResponseConverter {

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public TableDocumentResponse of(TableDocument tableDocument, List<TableColumnDocument> columns, List<TableIndexDocument> indexes, List<TableForeignKeyDocument> foreignKeys, List<TableTriggerDocument> triggers) {
        if ( tableDocument == null && columns == null && indexes == null && foreignKeys == null && triggers == null ) {
            return null;
        }

        TableDocumentResponseBuilder tableDocumentResponse = TableDocumentResponse.builder();

        if ( tableDocument != null ) {
            tableDocumentResponse.comment( tableDocument.getComment() );
            tableDocumentResponse.createAt( tableDocument.getCreateAt() );
            tableDocumentResponse.id( tableDocument.getId() );
            tableDocumentResponse.name( tableDocument.getName() );
            tableDocumentResponse.type( tableDocument.getType() );
        }
        if ( columns != null ) {
            tableDocumentResponse.columns( tableColumnDocumentListToColumnDocumentResponseList( columns ) );
        }
        if ( indexes != null ) {
            tableDocumentResponse.indexes( tableIndexDocumentListToIndexDocumentResponseList( indexes ) );
        }
        if ( foreignKeys != null ) {
            tableDocumentResponse.foreignKeys( tableForeignKeyDocumentListToForeignKeyDocumentResponseList( foreignKeys ) );
        }
        if ( triggers != null ) {
            tableDocumentResponse.triggers( tableTriggerDocumentListToTriggerDocumentResponseList( triggers ) );
        }

        return tableDocumentResponse.build();
    }

    @Override
    public TableDocumentResponse of(TableDocument tableDocument, Integer discussionCount, String description, List<ColumnDocumentResponse> columns, List<TableIndexDocument> indexes, List<TableForeignKeyDocument> foreignKeys, List<TableTriggerDocument> triggers) {
        if ( tableDocument == null && discussionCount == null && description == null && columns == null && indexes == null && foreignKeys == null && triggers == null ) {
            return null;
        }

        TableDocumentResponseBuilder tableDocumentResponse = TableDocumentResponse.builder();

        if ( tableDocument != null ) {
            tableDocumentResponse.comment( tableDocument.getComment() );
            tableDocumentResponse.createAt( tableDocument.getCreateAt() );
            tableDocumentResponse.id( tableDocument.getId() );
            tableDocumentResponse.name( tableDocument.getName() );
            tableDocumentResponse.type( tableDocument.getType() );
        }
        if ( discussionCount != null ) {
            tableDocumentResponse.discussionCount( discussionCount );
        }
        if ( description != null ) {
            tableDocumentResponse.description( description );
        }
        if ( columns != null ) {
            List<ColumnDocumentResponse> list = columns;
            if ( list != null ) {
                tableDocumentResponse.columns( new ArrayList<ColumnDocumentResponse>( list ) );
            }
        }
        if ( indexes != null ) {
            tableDocumentResponse.indexes( tableIndexDocumentListToIndexDocumentResponseList( indexes ) );
        }
        if ( foreignKeys != null ) {
            tableDocumentResponse.foreignKeys( tableForeignKeyDocumentListToForeignKeyDocumentResponseList( foreignKeys ) );
        }
        if ( triggers != null ) {
            tableDocumentResponse.triggers( tableTriggerDocumentListToTriggerDocumentResponseList( triggers ) );
        }

        return tableDocumentResponse.build();
    }

    @Override
    public ColumnDocumentResponse of(TableColumnDocument pojo, String description) {
        if ( pojo == null && description == null ) {
            return null;
        }

        ColumnDocumentResponseBuilder columnDocumentResponse = ColumnDocumentResponse.builder();

        if ( pojo != null ) {
            columnDocumentResponse.autoIncrement( pojo.getAutoIncrement() );
            columnDocumentResponse.comment( pojo.getComment() );
            columnDocumentResponse.createAt( pojo.getCreateAt() );
            columnDocumentResponse.decimalDigits( pojo.getDecimalDigits() );
            columnDocumentResponse.defaultValue( pojo.getDefaultValue() );
            columnDocumentResponse.id( pojo.getId() );
            columnDocumentResponse.isPrimaryKey( pojo.getIsPrimaryKey() );
            columnDocumentResponse.name( pojo.getName() );
            columnDocumentResponse.nullable( pojo.getNullable() );
            columnDocumentResponse.size( pojo.getSize() );
            columnDocumentResponse.type( pojo.getType() );
        }
        if ( description != null ) {
            columnDocumentResponse.description( description );
        }

        return columnDocumentResponse.build();
    }

    @Override
    public IndexDocumentResponse of(TableIndexDocument indexDocument) {
        if ( indexDocument == null ) {
            return null;
        }

        IndexDocumentResponseBuilder indexDocumentResponse = IndexDocumentResponse.builder();

        indexDocumentResponse.columnNames( jsonConverter.fromJson( indexDocument.getColumnNameArray() ) );
        indexDocumentResponse.createAt( indexDocument.getCreateAt() );
        indexDocumentResponse.id( indexDocument.getId() );
        indexDocumentResponse.isUnique( indexDocument.getIsUnique() );
        indexDocumentResponse.name( indexDocument.getName() );

        return indexDocumentResponse.build();
    }

    @Override
    public DatabaseDocumentResponse of(DatabaseDocument databaseDocument, List<TableDocumentResponse> tables) {
        if ( databaseDocument == null && tables == null ) {
            return null;
        }

        DatabaseDocumentResponseBuilder databaseDocumentResponse = DatabaseDocumentResponse.builder();

        if ( databaseDocument != null ) {
            databaseDocumentResponse.id( databaseDocument.getId() );
            databaseDocumentResponse.createAt( databaseDocument.getCreateAt() );
            if ( databaseDocument.getVersion() != null ) {
                databaseDocumentResponse.documentVersion( databaseDocument.getVersion().intValue() );
            }
            databaseDocumentResponse.databaseName( databaseDocument.getDatabaseName() );
            databaseDocumentResponse.productName( databaseDocument.getProductName() );
            databaseDocumentResponse.productVersion( databaseDocument.getProductVersion() );
            databaseDocumentResponse.schemaName( databaseDocument.getSchemaName() );
        }
        if ( tables != null ) {
            List<TableDocumentResponse> list = tables;
            if ( list != null ) {
                databaseDocumentResponse.tables( new ArrayList<TableDocumentResponse>( list ) );
            }
        }

        return databaseDocumentResponse.build();
    }

    @Override
    public TableDocumentResponse ofDiff(TableDocDiff table, List<ColumnDocumentResponse> cols, Map<String, Integer> discussionCountMap, Map<String, String> descriptionMap) {
        if ( table == null && cols == null && discussionCountMap == null && descriptionMap == null ) {
            return null;
        }

        TableDocumentResponseBuilder tableDocumentResponse = TableDocumentResponse.builder();

        if ( table != null ) {
            tableDocumentResponse.comment( table.getComment() );
            tableDocumentResponse.createAt( table.getCreateAt() );
            tableDocumentResponse.diffType( table.getDiffType() );
            tableDocumentResponse.foreignKeys( foreignKeyDocDiffListToForeignKeyDocumentResponseList( table.getForeignKeys() ) );
            tableDocumentResponse.id( table.getId() );
            tableDocumentResponse.indexes( indexDocDiffListToIndexDocumentResponseList( table.getIndexes() ) );
            tableDocumentResponse.name( table.getName() );
            tableDocumentResponse.original( tableDocDiffToTableDocumentResponse( table.getOriginal() ) );
            tableDocumentResponse.triggers( triggerDocDiffListToTriggerDocumentResponseList( table.getTriggers() ) );
            tableDocumentResponse.type( table.getType() );
        }
        if ( cols != null ) {
            List<ColumnDocumentResponse> list = cols;
            if ( list != null ) {
                tableDocumentResponse.columns( new ArrayList<ColumnDocumentResponse>( list ) );
            }
        }
        tableDocumentResponse.description( descriptionMap.get(table.getName()) );
        tableDocumentResponse.discussionCount( discussionCountMap.get(table.getName()) );

        return tableDocumentResponse.build();
    }

    @Override
    public ColumnDocumentResponse toColumn(String tableName, ColumnDocDiff diff, Map<String, String> descriptionMap) {
        if ( tableName == null && diff == null && descriptionMap == null ) {
            return null;
        }

        ColumnDocumentResponseBuilder columnDocumentResponse = ColumnDocumentResponse.builder();

        if ( diff != null ) {
            columnDocumentResponse.autoIncrement( diff.getAutoIncrement() );
            columnDocumentResponse.comment( diff.getComment() );
            columnDocumentResponse.createAt( diff.getCreateAt() );
            columnDocumentResponse.decimalDigits( diff.getDecimalDigits() );
            columnDocumentResponse.defaultValue( diff.getDefaultValue() );
            columnDocumentResponse.diffType( diff.getDiffType() );
            columnDocumentResponse.id( diff.getId() );
            columnDocumentResponse.isPrimaryKey( diff.getIsPrimaryKey() );
            columnDocumentResponse.name( diff.getName() );
            columnDocumentResponse.nullable( diff.getNullable() );
            columnDocumentResponse.size( diff.getSize() );
            columnDocumentResponse.type( diff.getType() );
        }
        columnDocumentResponse.description( descriptionMap.get(tableName+"."+diff.getName()) );
        columnDocumentResponse.original( toOriginalColumn(tableName, diff.getOriginal(), descriptionMap) );

        return columnDocumentResponse.build();
    }

    protected ColumnDocumentResponse tableColumnDocumentToColumnDocumentResponse(TableColumnDocument tableColumnDocument) {
        if ( tableColumnDocument == null ) {
            return null;
        }

        ColumnDocumentResponseBuilder columnDocumentResponse = ColumnDocumentResponse.builder();

        columnDocumentResponse.autoIncrement( tableColumnDocument.getAutoIncrement() );
        columnDocumentResponse.comment( tableColumnDocument.getComment() );
        columnDocumentResponse.createAt( tableColumnDocument.getCreateAt() );
        columnDocumentResponse.decimalDigits( tableColumnDocument.getDecimalDigits() );
        columnDocumentResponse.defaultValue( tableColumnDocument.getDefaultValue() );
        columnDocumentResponse.id( tableColumnDocument.getId() );
        columnDocumentResponse.isPrimaryKey( tableColumnDocument.getIsPrimaryKey() );
        columnDocumentResponse.name( tableColumnDocument.getName() );
        columnDocumentResponse.nullable( tableColumnDocument.getNullable() );
        columnDocumentResponse.size( tableColumnDocument.getSize() );
        columnDocumentResponse.type( tableColumnDocument.getType() );

        return columnDocumentResponse.build();
    }

    protected List<ColumnDocumentResponse> tableColumnDocumentListToColumnDocumentResponseList(List<TableColumnDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<ColumnDocumentResponse> list1 = new ArrayList<ColumnDocumentResponse>( list.size() );
        for ( TableColumnDocument tableColumnDocument : list ) {
            list1.add( tableColumnDocumentToColumnDocumentResponse( tableColumnDocument ) );
        }

        return list1;
    }

    protected List<IndexDocumentResponse> tableIndexDocumentListToIndexDocumentResponseList(List<TableIndexDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<IndexDocumentResponse> list1 = new ArrayList<IndexDocumentResponse>( list.size() );
        for ( TableIndexDocument tableIndexDocument : list ) {
            list1.add( of( tableIndexDocument ) );
        }

        return list1;
    }

    protected ForeignKeyDocumentResponse tableForeignKeyDocumentToForeignKeyDocumentResponse(TableForeignKeyDocument tableForeignKeyDocument) {
        if ( tableForeignKeyDocument == null ) {
            return null;
        }

        ForeignKeyDocumentResponseBuilder foreignKeyDocumentResponse = ForeignKeyDocumentResponse.builder();

        foreignKeyDocumentResponse.createAt( tableForeignKeyDocument.getCreateAt() );
        foreignKeyDocumentResponse.deleteRule( tableForeignKeyDocument.getDeleteRule() );
        foreignKeyDocumentResponse.fkColumnName( tableForeignKeyDocument.getFkColumnName() );
        foreignKeyDocumentResponse.fkName( tableForeignKeyDocument.getFkName() );
        foreignKeyDocumentResponse.fkTableName( tableForeignKeyDocument.getFkTableName() );
        foreignKeyDocumentResponse.id( tableForeignKeyDocument.getId() );
        foreignKeyDocumentResponse.keySeq( tableForeignKeyDocument.getKeySeq() );
        foreignKeyDocumentResponse.pkColumnName( tableForeignKeyDocument.getPkColumnName() );
        foreignKeyDocumentResponse.pkName( tableForeignKeyDocument.getPkName() );
        foreignKeyDocumentResponse.pkTableName( tableForeignKeyDocument.getPkTableName() );
        foreignKeyDocumentResponse.updateRule( tableForeignKeyDocument.getUpdateRule() );

        return foreignKeyDocumentResponse.build();
    }

    protected List<ForeignKeyDocumentResponse> tableForeignKeyDocumentListToForeignKeyDocumentResponseList(List<TableForeignKeyDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<ForeignKeyDocumentResponse> list1 = new ArrayList<ForeignKeyDocumentResponse>( list.size() );
        for ( TableForeignKeyDocument tableForeignKeyDocument : list ) {
            list1.add( tableForeignKeyDocumentToForeignKeyDocumentResponse( tableForeignKeyDocument ) );
        }

        return list1;
    }

    protected TriggerDocumentResponse tableTriggerDocumentToTriggerDocumentResponse(TableTriggerDocument tableTriggerDocument) {
        if ( tableTriggerDocument == null ) {
            return null;
        }

        TriggerDocumentResponseBuilder triggerDocumentResponse = TriggerDocumentResponse.builder();

        triggerDocumentResponse.createAt( tableTriggerDocument.getCreateAt() );
        triggerDocumentResponse.id( tableTriggerDocument.getId() );
        triggerDocumentResponse.manipulation( tableTriggerDocument.getManipulation() );
        triggerDocumentResponse.name( tableTriggerDocument.getName() );
        triggerDocumentResponse.statement( tableTriggerDocument.getStatement() );
        triggerDocumentResponse.timing( tableTriggerDocument.getTiming() );
        triggerDocumentResponse.triggerCreateAt( tableTriggerDocument.getTriggerCreateAt() );

        return triggerDocumentResponse.build();
    }

    protected List<TriggerDocumentResponse> tableTriggerDocumentListToTriggerDocumentResponseList(List<TableTriggerDocument> list) {
        if ( list == null ) {
            return null;
        }

        List<TriggerDocumentResponse> list1 = new ArrayList<TriggerDocumentResponse>( list.size() );
        for ( TableTriggerDocument tableTriggerDocument : list ) {
            list1.add( tableTriggerDocumentToTriggerDocumentResponse( tableTriggerDocument ) );
        }

        return list1;
    }

    protected ForeignKeyDocumentResponse foreignKeyDocDiffToForeignKeyDocumentResponse(ForeignKeyDocDiff foreignKeyDocDiff) {
        if ( foreignKeyDocDiff == null ) {
            return null;
        }

        ForeignKeyDocumentResponseBuilder foreignKeyDocumentResponse = ForeignKeyDocumentResponse.builder();

        foreignKeyDocumentResponse.createAt( foreignKeyDocDiff.getCreateAt() );
        foreignKeyDocumentResponse.deleteRule( foreignKeyDocDiff.getDeleteRule() );
        foreignKeyDocumentResponse.diffType( foreignKeyDocDiff.getDiffType() );
        foreignKeyDocumentResponse.fkColumnName( foreignKeyDocDiff.getFkColumnName() );
        foreignKeyDocumentResponse.fkName( foreignKeyDocDiff.getFkName() );
        foreignKeyDocumentResponse.fkTableName( foreignKeyDocDiff.getFkTableName() );
        foreignKeyDocumentResponse.id( foreignKeyDocDiff.getId() );
        foreignKeyDocumentResponse.keySeq( foreignKeyDocDiff.getKeySeq() );
        foreignKeyDocumentResponse.original( foreignKeyDocDiffToForeignKeyDocumentResponse( foreignKeyDocDiff.getOriginal() ) );
        foreignKeyDocumentResponse.pkColumnName( foreignKeyDocDiff.getPkColumnName() );
        foreignKeyDocumentResponse.pkName( foreignKeyDocDiff.getPkName() );
        foreignKeyDocumentResponse.pkTableName( foreignKeyDocDiff.getPkTableName() );
        foreignKeyDocumentResponse.updateRule( foreignKeyDocDiff.getUpdateRule() );

        return foreignKeyDocumentResponse.build();
    }

    protected List<ForeignKeyDocumentResponse> foreignKeyDocDiffListToForeignKeyDocumentResponseList(List<ForeignKeyDocDiff> list) {
        if ( list == null ) {
            return null;
        }

        List<ForeignKeyDocumentResponse> list1 = new ArrayList<ForeignKeyDocumentResponse>( list.size() );
        for ( ForeignKeyDocDiff foreignKeyDocDiff : list ) {
            list1.add( foreignKeyDocDiffToForeignKeyDocumentResponse( foreignKeyDocDiff ) );
        }

        return list1;
    }

    protected IndexDocumentResponse indexDocDiffToIndexDocumentResponse(IndexDocDiff indexDocDiff) {
        if ( indexDocDiff == null ) {
            return null;
        }

        IndexDocumentResponseBuilder indexDocumentResponse = IndexDocumentResponse.builder();

        indexDocumentResponse.createAt( indexDocDiff.getCreateAt() );
        indexDocumentResponse.diffType( indexDocDiff.getDiffType() );
        indexDocumentResponse.id( indexDocDiff.getId() );
        indexDocumentResponse.isUnique( indexDocDiff.getIsUnique() );
        indexDocumentResponse.name( indexDocDiff.getName() );
        indexDocumentResponse.original( indexDocDiffToIndexDocumentResponse( indexDocDiff.getOriginal() ) );

        return indexDocumentResponse.build();
    }

    protected List<IndexDocumentResponse> indexDocDiffListToIndexDocumentResponseList(List<IndexDocDiff> list) {
        if ( list == null ) {
            return null;
        }

        List<IndexDocumentResponse> list1 = new ArrayList<IndexDocumentResponse>( list.size() );
        for ( IndexDocDiff indexDocDiff : list ) {
            list1.add( indexDocDiffToIndexDocumentResponse( indexDocDiff ) );
        }

        return list1;
    }

    protected ColumnDocumentResponse columnDocDiffToColumnDocumentResponse(ColumnDocDiff columnDocDiff) {
        if ( columnDocDiff == null ) {
            return null;
        }

        ColumnDocumentResponseBuilder columnDocumentResponse = ColumnDocumentResponse.builder();

        columnDocumentResponse.autoIncrement( columnDocDiff.getAutoIncrement() );
        columnDocumentResponse.comment( columnDocDiff.getComment() );
        columnDocumentResponse.createAt( columnDocDiff.getCreateAt() );
        columnDocumentResponse.decimalDigits( columnDocDiff.getDecimalDigits() );
        columnDocumentResponse.defaultValue( columnDocDiff.getDefaultValue() );
        columnDocumentResponse.diffType( columnDocDiff.getDiffType() );
        columnDocumentResponse.id( columnDocDiff.getId() );
        columnDocumentResponse.isPrimaryKey( columnDocDiff.getIsPrimaryKey() );
        columnDocumentResponse.name( columnDocDiff.getName() );
        columnDocumentResponse.nullable( columnDocDiff.getNullable() );
        columnDocumentResponse.original( columnDocDiffToColumnDocumentResponse( columnDocDiff.getOriginal() ) );
        columnDocumentResponse.size( columnDocDiff.getSize() );
        columnDocumentResponse.type( columnDocDiff.getType() );

        return columnDocumentResponse.build();
    }

    protected List<ColumnDocumentResponse> columnDocDiffListToColumnDocumentResponseList(List<ColumnDocDiff> list) {
        if ( list == null ) {
            return null;
        }

        List<ColumnDocumentResponse> list1 = new ArrayList<ColumnDocumentResponse>( list.size() );
        for ( ColumnDocDiff columnDocDiff : list ) {
            list1.add( columnDocDiffToColumnDocumentResponse( columnDocDiff ) );
        }

        return list1;
    }

    protected TriggerDocumentResponse triggerDocDiffToTriggerDocumentResponse(TriggerDocDiff triggerDocDiff) {
        if ( triggerDocDiff == null ) {
            return null;
        }

        TriggerDocumentResponseBuilder triggerDocumentResponse = TriggerDocumentResponse.builder();

        triggerDocumentResponse.createAt( triggerDocDiff.getCreateAt() );
        triggerDocumentResponse.diffType( triggerDocDiff.getDiffType() );
        triggerDocumentResponse.id( triggerDocDiff.getId() );
        triggerDocumentResponse.manipulation( triggerDocDiff.getManipulation() );
        triggerDocumentResponse.name( triggerDocDiff.getName() );
        triggerDocumentResponse.original( triggerDocDiffToTriggerDocumentResponse( triggerDocDiff.getOriginal() ) );
        triggerDocumentResponse.statement( triggerDocDiff.getStatement() );
        triggerDocumentResponse.timing( triggerDocDiff.getTiming() );
        triggerDocumentResponse.triggerCreateAt( triggerDocDiff.getTriggerCreateAt() );

        return triggerDocumentResponse.build();
    }

    protected List<TriggerDocumentResponse> triggerDocDiffListToTriggerDocumentResponseList(List<TriggerDocDiff> list) {
        if ( list == null ) {
            return null;
        }

        List<TriggerDocumentResponse> list1 = new ArrayList<TriggerDocumentResponse>( list.size() );
        for ( TriggerDocDiff triggerDocDiff : list ) {
            list1.add( triggerDocDiffToTriggerDocumentResponse( triggerDocDiff ) );
        }

        return list1;
    }

    protected TableDocumentResponse tableDocDiffToTableDocumentResponse(TableDocDiff tableDocDiff) {
        if ( tableDocDiff == null ) {
            return null;
        }

        TableDocumentResponseBuilder tableDocumentResponse = TableDocumentResponse.builder();

        tableDocumentResponse.columns( columnDocDiffListToColumnDocumentResponseList( tableDocDiff.getColumns() ) );
        tableDocumentResponse.comment( tableDocDiff.getComment() );
        tableDocumentResponse.createAt( tableDocDiff.getCreateAt() );
        tableDocumentResponse.diffType( tableDocDiff.getDiffType() );
        tableDocumentResponse.foreignKeys( foreignKeyDocDiffListToForeignKeyDocumentResponseList( tableDocDiff.getForeignKeys() ) );
        tableDocumentResponse.id( tableDocDiff.getId() );
        tableDocumentResponse.indexes( indexDocDiffListToIndexDocumentResponseList( tableDocDiff.getIndexes() ) );
        tableDocumentResponse.name( tableDocDiff.getName() );
        tableDocumentResponse.original( tableDocDiffToTableDocumentResponse( tableDocDiff.getOriginal() ) );
        tableDocumentResponse.triggers( triggerDocDiffListToTriggerDocumentResponseList( tableDocDiff.getTriggers() ) );
        tableDocumentResponse.type( tableDocDiff.getType() );

        return tableDocumentResponse.build();
    }
}

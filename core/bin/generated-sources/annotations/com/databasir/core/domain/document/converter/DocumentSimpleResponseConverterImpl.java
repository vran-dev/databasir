package com.databasir.core.domain.document.converter;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse;
import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse.TableData;
import com.databasir.core.domain.document.data.diff.TableDocDiff;
import com.databasir.dao.tables.pojos.DatabaseDocument;
import com.databasir.dao.tables.pojos.TableDocument;
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
public class DocumentSimpleResponseConverterImpl implements DocumentSimpleResponseConverter {

    @Override
    public DatabaseDocumentSimpleResponse of(DatabaseDocument database, List<TableDocDiff> tables, String projectName) {
        if ( database == null && tables == null && projectName == null ) {
            return null;
        }

        DatabaseDocumentSimpleResponse databaseDocumentSimpleResponse = new DatabaseDocumentSimpleResponse();

        if ( database != null ) {
            databaseDocumentSimpleResponse.setId( database.getId() );
            databaseDocumentSimpleResponse.setCreateAt( database.getCreateAt() );
            if ( database.getVersion() != null ) {
                databaseDocumentSimpleResponse.setDocumentVersion( database.getVersion().intValue() );
            }
            databaseDocumentSimpleResponse.setDatabaseName( database.getDatabaseName() );
            databaseDocumentSimpleResponse.setProductName( database.getProductName() );
            databaseDocumentSimpleResponse.setProductVersion( database.getProductVersion() );
            databaseDocumentSimpleResponse.setSchemaName( database.getSchemaName() );
        }
        if ( tables != null ) {
            databaseDocumentSimpleResponse.setTables( tableDocDiffListToTableDataList( tables ) );
        }
        if ( projectName != null ) {
            databaseDocumentSimpleResponse.setProjectName( projectName );
        }

        return databaseDocumentSimpleResponse;
    }

    @Override
    public DatabaseDocumentSimpleResponse of(DatabaseDocument databaseDocument, List<TableData> tables, DiffType diffType, String projectName) {
        if ( databaseDocument == null && tables == null && diffType == null && projectName == null ) {
            return null;
        }

        DatabaseDocumentSimpleResponse databaseDocumentSimpleResponse = new DatabaseDocumentSimpleResponse();

        if ( databaseDocument != null ) {
            databaseDocumentSimpleResponse.setId( databaseDocument.getId() );
            databaseDocumentSimpleResponse.setCreateAt( databaseDocument.getCreateAt() );
            if ( databaseDocument.getVersion() != null ) {
                databaseDocumentSimpleResponse.setDocumentVersion( databaseDocument.getVersion().intValue() );
            }
            databaseDocumentSimpleResponse.setDatabaseName( databaseDocument.getDatabaseName() );
            databaseDocumentSimpleResponse.setProductName( databaseDocument.getProductName() );
            databaseDocumentSimpleResponse.setProductVersion( databaseDocument.getProductVersion() );
            databaseDocumentSimpleResponse.setSchemaName( databaseDocument.getSchemaName() );
        }
        if ( tables != null ) {
            List<TableData> list = tables;
            if ( list != null ) {
                databaseDocumentSimpleResponse.setTables( new ArrayList<TableData>( list ) );
            }
        }
        if ( diffType != null ) {
            databaseDocumentSimpleResponse.setDiffType( diffType );
        }
        if ( projectName != null ) {
            databaseDocumentSimpleResponse.setProjectName( projectName );
        }

        return databaseDocumentSimpleResponse;
    }

    @Override
    public TableData of(TableDocument tables, Integer discussionCount, String description) {
        if ( tables == null && discussionCount == null && description == null ) {
            return null;
        }

        TableData tableData = new TableData();

        if ( tables != null ) {
            tableData.setComment( tables.getComment() );
            tableData.setId( tables.getId() );
            tableData.setName( tables.getName() );
            tableData.setType( tables.getType() );
        }
        if ( discussionCount != null ) {
            tableData.setDiscussionCount( discussionCount );
        }
        if ( description != null ) {
            tableData.setDescription( description );
        }

        return tableData;
    }

    @Override
    public TableData ofDiff(TableDocDiff table, Integer discussionCount, String description) {
        if ( table == null && discussionCount == null && description == null ) {
            return null;
        }

        TableData tableData = new TableData();

        if ( table != null ) {
            tableData.setComment( table.getComment() );
            tableData.setDiffType( table.getDiffType() );
            tableData.setId( table.getId() );
            tableData.setName( table.getName() );
            tableData.setOriginal( tableDocDiffToTableData( table.getOriginal() ) );
            tableData.setType( table.getType() );
        }
        if ( discussionCount != null ) {
            tableData.setDiscussionCount( discussionCount );
        }
        if ( description != null ) {
            tableData.setDescription( description );
        }

        return tableData;
    }

    protected TableData tableDocDiffToTableData(TableDocDiff tableDocDiff) {
        if ( tableDocDiff == null ) {
            return null;
        }

        TableData tableData = new TableData();

        tableData.setComment( tableDocDiff.getComment() );
        tableData.setDiffType( tableDocDiff.getDiffType() );
        tableData.setId( tableDocDiff.getId() );
        tableData.setName( tableDocDiff.getName() );
        tableData.setOriginal( tableDocDiffToTableData( tableDocDiff.getOriginal() ) );
        tableData.setType( tableDocDiff.getType() );

        return tableData;
    }

    protected List<TableData> tableDocDiffListToTableDataList(List<TableDocDiff> list) {
        if ( list == null ) {
            return null;
        }

        List<TableData> list1 = new ArrayList<TableData>( list.size() );
        for ( TableDocDiff tableDocDiff : list ) {
            list1.add( tableDocDiffToTableData( tableDocDiff ) );
        }

        return list1;
    }
}

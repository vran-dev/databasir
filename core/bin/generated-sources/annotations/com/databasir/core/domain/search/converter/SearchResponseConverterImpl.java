package com.databasir.core.domain.search.converter;

import com.databasir.core.domain.search.data.SearchResponse.Item;
import com.databasir.dao.tables.pojos.DocumentFullText;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class SearchResponseConverterImpl implements SearchResponseConverter {

    @Override
    public Item toItem(DocumentFullText item, String groupName, String groupDescription, String projectName, String projectDescription) {
        if ( item == null && groupName == null && groupDescription == null && projectName == null && projectDescription == null ) {
            return null;
        }

        Item item1 = new Item();

        if ( item != null ) {
            item1.setColComment( item.getColComment() );
            item1.setColDescription( item.getColDescription() );
            item1.setColName( item.getColName() );
            item1.setDatabaseDocumentId( item.getDatabaseDocumentId() );
            item1.setDatabaseDocumentVersion( item.getDatabaseDocumentVersion() );
            item1.setDatabaseName( item.getDatabaseName() );
            item1.setDatabaseProductName( item.getDatabaseProductName() );
            item1.setDatabaseType( item.getDatabaseType() );
            item1.setGroupId( item.getGroupId() );
            item1.setProjectId( item.getProjectId() );
            item1.setSchemaName( item.getSchemaName() );
            item1.setTableColumnDocumentId( item.getTableColumnDocumentId() );
            item1.setTableComment( item.getTableComment() );
            item1.setTableDescription( item.getTableDescription() );
            item1.setTableDocumentId( item.getTableDocumentId() );
            item1.setTableName( item.getTableName() );
        }
        if ( groupName != null ) {
            item1.setGroupName( groupName );
        }
        if ( groupDescription != null ) {
            item1.setGroupDescription( groupDescription );
        }
        if ( projectName != null ) {
            item1.setProjectName( projectName );
        }
        if ( projectDescription != null ) {
            item1.setProjectDescription( projectDescription );
        }

        return item1;
    }

    @Override
    public Item toItem(DocumentFullText pojo) {
        if ( pojo == null ) {
            return null;
        }

        Item item = new Item();

        item.setColComment( pojo.getColComment() );
        item.setColDescription( pojo.getColDescription() );
        item.setColName( pojo.getColName() );
        item.setDatabaseDocumentId( pojo.getDatabaseDocumentId() );
        item.setDatabaseDocumentVersion( pojo.getDatabaseDocumentVersion() );
        item.setDatabaseName( pojo.getDatabaseName() );
        item.setDatabaseProductName( pojo.getDatabaseProductName() );
        item.setDatabaseType( pojo.getDatabaseType() );
        item.setGroupDescription( pojo.getGroupDescription() );
        item.setGroupId( pojo.getGroupId() );
        item.setGroupName( pojo.getGroupName() );
        item.setProjectDescription( pojo.getProjectDescription() );
        item.setProjectId( pojo.getProjectId() );
        item.setProjectName( pojo.getProjectName() );
        item.setSchemaName( pojo.getSchemaName() );
        item.setTableColumnDocumentId( pojo.getTableColumnDocumentId() );
        item.setTableComment( pojo.getTableComment() );
        item.setTableDescription( pojo.getTableDescription() );
        item.setTableDocumentId( pojo.getTableDocumentId() );
        item.setTableName( pojo.getTableName() );

        return item;
    }
}

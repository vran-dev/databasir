package com.databasir.core.domain.document.converter;

import com.databasir.dao.tables.pojos.DatabaseDocument;
import com.databasir.dao.tables.pojos.DocumentFullText;
import com.databasir.dao.tables.pojos.Group;
import com.databasir.dao.tables.pojos.Project;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DocumentFullTextConverterImpl implements DocumentFullTextConverter {

    @Override
    public DocumentFullText toPojo(Group group, Project project, DatabaseDocument db, TableDocument table, TableColumnDocument column, String tableDescription, String columnDescription) {
        if ( group == null && project == null && db == null && table == null && column == null && tableDescription == null && columnDescription == null ) {
            return null;
        }

        DocumentFullText documentFullText = new DocumentFullText();

        if ( group != null ) {
            documentFullText.setGroupId( group.getId() );
        }
        if ( db != null ) {
            documentFullText.setProjectId( db.getProjectId() );
            documentFullText.setDatabaseDocumentId( db.getId() );
            if ( db.getVersion() != null ) {
                documentFullText.setDatabaseDocumentVersion( db.getVersion().intValue() );
            }
            documentFullText.setDatabaseProductName( db.getProductName() );
            documentFullText.setDatabaseName( db.getDatabaseName() );
            documentFullText.setSchemaName( db.getSchemaName() );
        }
        if ( table != null ) {
            documentFullText.setTableDocumentId( table.getId() );
            documentFullText.setTableName( table.getName() );
            documentFullText.setTableComment( table.getComment() );
        }
        if ( column != null ) {
            documentFullText.setTableColumnDocumentId( column.getId() );
            documentFullText.setColName( column.getName() );
            documentFullText.setColComment( column.getComment() );
        }
        if ( tableDescription != null ) {
            documentFullText.setTableDescription( tableDescription );
        }

        return documentFullText;
    }
}

package com.databasir.core.domain.description.converter;

import com.databasir.core.domain.description.data.DocumentDescriptionSaveRequest;
import com.databasir.dao.tables.pojos.DocumentDescription;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DocumentDescriptionConverterImpl implements DocumentDescriptionConverter {

    @Override
    public DocumentDescription of(Integer projectId, Integer updateBy, DocumentDescriptionSaveRequest request) {
        if ( projectId == null && updateBy == null && request == null ) {
            return null;
        }

        DocumentDescription documentDescription = new DocumentDescription();

        if ( projectId != null ) {
            documentDescription.setProjectId( projectId );
        }
        if ( updateBy != null ) {
            documentDescription.setUpdateBy( updateBy );
        }
        if ( request != null ) {
            documentDescription.setContent( request.getContent() );
            documentDescription.setTableName( request.getTableName() );
            documentDescription.setColumnName( request.getColumnName() );
        }

        return documentDescription;
    }
}

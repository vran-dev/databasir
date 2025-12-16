package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DocumentTemplatePropertiesResponse.DocumentTemplatePropertyResponse;
import com.databasir.dao.tables.pojos.DocumentTemplateProperty;
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
public class DocumentTemplatePropertyResponseConverterImpl implements DocumentTemplatePropertyResponseConverter {

    @Override
    public List<DocumentTemplatePropertyResponse> of(List<DocumentTemplateProperty> pojoList) {
        if ( pojoList == null ) {
            return null;
        }

        List<DocumentTemplatePropertyResponse> list = new ArrayList<DocumentTemplatePropertyResponse>( pojoList.size() );
        for ( DocumentTemplateProperty documentTemplateProperty : pojoList ) {
            list.add( documentTemplatePropertyToDocumentTemplatePropertyResponse( documentTemplateProperty ) );
        }

        return list;
    }

    protected DocumentTemplatePropertyResponse documentTemplatePropertyToDocumentTemplatePropertyResponse(DocumentTemplateProperty documentTemplateProperty) {
        if ( documentTemplateProperty == null ) {
            return null;
        }

        DocumentTemplatePropertyResponse documentTemplatePropertyResponse = new DocumentTemplatePropertyResponse();

        documentTemplatePropertyResponse.setCreateAt( documentTemplateProperty.getCreateAt() );
        documentTemplatePropertyResponse.setDefaultValue( documentTemplateProperty.getDefaultValue() );
        documentTemplatePropertyResponse.setKey( documentTemplateProperty.getKey() );
        documentTemplatePropertyResponse.setType( documentTemplateProperty.getType() );
        documentTemplatePropertyResponse.setValue( documentTemplateProperty.getValue() );

        return documentTemplatePropertyResponse;
    }
}

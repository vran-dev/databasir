package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DocumentTemplatePropertiesUpdateRequest.PropertyRequest;
import com.databasir.dao.enums.DocumentTemplatePropertyType;
import com.databasir.dao.tables.pojos.DocumentTemplateProperty;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class DocumentTemplatePropertiesUpdateRequestConverterImpl implements DocumentTemplatePropertiesUpdateRequestConverter {

    @Override
    public DocumentTemplateProperty toPojo(PropertyRequest property, DocumentTemplatePropertyType type) {
        if ( property == null && type == null ) {
            return null;
        }

        DocumentTemplateProperty documentTemplateProperty = new DocumentTemplateProperty();

        if ( property != null ) {
            documentTemplateProperty.setKey( property.getKey() );
            documentTemplateProperty.setValue( property.getValue() );
        }
        if ( type != null ) {
            documentTemplateProperty.setType( type );
        }
        documentTemplateProperty.setDefaultValue( "" );

        return documentTemplateProperty;
    }
}

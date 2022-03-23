package com.databasir.dao.converter;

import com.databasir.dao.enums.DocumentTemplatePropertyType;
import org.jooq.impl.EnumConverter;

public class DocumentTemplatePropertyTypeConverter extends EnumConverter<String, DocumentTemplatePropertyType> {

    public DocumentTemplatePropertyTypeConverter() {
        super(String.class, DocumentTemplatePropertyType.class);
    }

}

package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DocumentTemplatePropertiesUpdateRequest;
import com.databasir.dao.enums.DocumentTemplatePropertyType;
import com.databasir.dao.tables.pojos.DocumentTemplatePropertyPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DocumentTemplatePropertiesUpdateRequestConverter {

    @Mapping(target = "defaultValue", constant = "")
    DocumentTemplatePropertyPojo toPojo(DocumentTemplatePropertiesUpdateRequest.PropertyRequest property,
                                        DocumentTemplatePropertyType type);

    default List<DocumentTemplatePropertyPojo> toPojo(DocumentTemplatePropertiesUpdateRequest request) {
        return request.getProperties().stream()
                .map(prop -> toPojo(prop, request.getType()))
                .collect(Collectors.toList());
    }

}

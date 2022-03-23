package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DocumentTemplatePropertiesResponse;
import com.databasir.dao.tables.pojos.DocumentTemplatePropertyPojo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentTemplatePropertyResponseConverter {

    List<DocumentTemplatePropertiesResponse.DocumentTemplatePropertyResponse> of(
            List<DocumentTemplatePropertyPojo> pojoList);

}

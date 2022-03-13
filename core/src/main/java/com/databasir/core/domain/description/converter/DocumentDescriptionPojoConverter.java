package com.databasir.core.domain.description.converter;

import com.databasir.core.domain.description.data.DocumentDescriptionSaveRequest;
import com.databasir.dao.tables.pojos.DocumentDescriptionPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentDescriptionPojoConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    DocumentDescriptionPojo of(Integer projectId,
                               Integer updateBy,
                               DocumentDescriptionSaveRequest request);
}

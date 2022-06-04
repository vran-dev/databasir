package com.databasir.core.domain.description.converter;

import com.databasir.core.domain.description.data.DocumentDescriptionSaveRequest;
import com.databasir.dao.tables.pojos.DocumentDescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentDescriptionConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    DocumentDescription of(Integer projectId,
                               Integer updateBy,
                               DocumentDescriptionSaveRequest request);
}

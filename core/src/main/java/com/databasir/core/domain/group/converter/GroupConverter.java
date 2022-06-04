package com.databasir.core.domain.group.converter;

import com.databasir.core.domain.group.data.GroupCreateRequest;
import com.databasir.core.domain.group.data.GroupUpdateRequest;
import com.databasir.dao.tables.pojos.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    Group of(GroupCreateRequest groupCreateRequest);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    Group of(GroupUpdateRequest groupUpdateRequest);

    default String nullToEmpty(String description) {
        return description == null ? "" : description;
    }
}

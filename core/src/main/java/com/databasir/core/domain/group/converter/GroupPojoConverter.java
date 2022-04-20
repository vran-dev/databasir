package com.databasir.core.domain.group.converter;

import com.databasir.core.domain.group.data.GroupCreateRequest;
import com.databasir.core.domain.group.data.GroupUpdateRequest;
import com.databasir.dao.tables.pojos.GroupPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupPojoConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    GroupPojo of(GroupCreateRequest groupCreateRequest);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    GroupPojo of(GroupUpdateRequest groupUpdateRequest);

    default String nullToEmpty(String description) {
        return description == null ? "" : description;
    }
}

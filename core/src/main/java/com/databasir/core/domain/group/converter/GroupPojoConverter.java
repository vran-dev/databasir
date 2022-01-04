package com.databasir.core.domain.group.converter;

import com.databasir.core.domain.group.data.GroupCreateRequest;
import com.databasir.core.domain.group.data.GroupUpdateRequest;
import com.databasir.dao.tables.pojos.GroupPojo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupPojoConverter {

    GroupPojo of(GroupCreateRequest groupCreateRequest);

    GroupPojo of(GroupUpdateRequest groupUpdateRequest);
}

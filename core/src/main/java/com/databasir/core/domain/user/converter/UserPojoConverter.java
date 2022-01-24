package com.databasir.core.domain.user.converter;

import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.dao.tables.pojos.UserPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPojoConverter {

    @Mapping(target = "password", source = "hashedPassword")
    UserPojo of(UserCreateRequest request, String hashedPassword);
}

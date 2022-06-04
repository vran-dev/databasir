package com.databasir.core.domain.user.converter;

import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.dao.tables.pojos.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mapping(target = "password", source = "hashedPassword")
    User of(UserCreateRequest request, String hashedPassword);
}

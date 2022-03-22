package com.databasir.core.domain.user.event.converter;

import com.databasir.core.domain.user.event.UserCreated;
import com.databasir.core.domain.user.event.UserPasswordRenewed;
import com.databasir.dao.tables.pojos.UserPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserEventConverter {

    UserPasswordRenewed userPasswordRenewed(UserPojo pojo,
                                            Integer renewByUserId,
                                            LocalDateTime renewTime,
                                            String newPassword);

    @Mapping(target = "userId", source = "userId")
    UserCreated userCreated(UserPojo pojo, String source, String rawPassword, Integer userId);
}

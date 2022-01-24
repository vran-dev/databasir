package com.databasir.core.domain.user.converter;

import com.databasir.core.domain.user.data.UserDetailResponse;
import com.databasir.core.domain.user.data.UserPageResponse;
import com.databasir.dao.tables.pojos.UserPojo;
import com.databasir.dao.tables.pojos.UserRolePojo;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserResponseConverter {


    default UserDetailResponse detailResponse(UserPojo user,
                                              List<UserRolePojo> userRoles,
                                              Map<Integer, String> groupNameMapById) {
        List<UserDetailResponse.UserRoleDetailResponse> roles = userRoles.stream()
                .map(pojo -> userRoleDetailResponse(pojo, groupNameMapById.get(pojo.getGroupId())))
                .collect(Collectors.toList());
        return detailResponse(user, roles);
    }

    UserDetailResponse detailResponse(UserPojo pojo, List<UserDetailResponse.UserRoleDetailResponse> roles);

    UserDetailResponse.UserRoleDetailResponse userRoleDetailResponse(UserRolePojo pojo, String groupName);

    UserPageResponse pageResponse(UserPojo pojo, Boolean isSysOwner, List<Integer> inGroupIds);

}

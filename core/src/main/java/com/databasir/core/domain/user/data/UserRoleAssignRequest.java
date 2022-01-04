package com.databasir.core.domain.user.data;

import lombok.Data;

@Data
public class UserRoleAssignRequest {

    private Integer userId;

    private String role;

    private Integer groupId;
}

package com.databasir.core.domain.group.data;

import lombok.Data;

@Data
public class GroupMemberCreateRequest {

    private Integer userId;

    private String role;

}

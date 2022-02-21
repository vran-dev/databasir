package com.databasir.core.domain.group.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GroupMemberRoleUpdateRequest {

    @NotBlank
    private String role;

}

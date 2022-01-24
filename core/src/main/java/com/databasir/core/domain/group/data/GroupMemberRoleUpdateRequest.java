package com.databasir.core.domain.group.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class GroupMemberRoleUpdateRequest {

    @NotBlank
    private String role;

}

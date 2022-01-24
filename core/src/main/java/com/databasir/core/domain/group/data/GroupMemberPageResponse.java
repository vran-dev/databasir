package com.databasir.core.domain.group.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupMemberPageResponse {

    private Integer userId;

    private String role;

    private String username;

    private String nickname;

    private String email;

    private LocalDateTime createAt;
}

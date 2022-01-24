package com.databasir.dao.value;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupMemberDetailPojo {

    private Integer userId;

    private String role;

    private Integer groupId;

    private String username;

    private String nickname;

    private String email;

    private LocalDateTime createAt;
}

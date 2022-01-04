package com.databasir.core.domain.user.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDetailResponse {

    private Integer id;

    private String username;

    private String nickname;

    private String avatar;

    private String email;

    private Boolean enabled;

    private List<UserRoleDetailResponse> roles = new ArrayList<>();

    private LocalDateTime createAt;

    @Data
    public static class UserRoleDetailResponse {

        private String role;

        private Integer groupId;

        private String groupName;

        private LocalDateTime createAt;
    }
}

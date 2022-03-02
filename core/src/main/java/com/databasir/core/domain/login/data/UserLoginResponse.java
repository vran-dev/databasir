package com.databasir.core.domain.login.data;

import lombok.Data;

import java.util.List;

@Data
public class UserLoginResponse {

    private Integer id;

    private String nickname;

    private String email;

    private String username;

    private String avatar;

    private String accessToken;

    private long accessTokenExpireAt;

    private String refreshToken;

    private List<RoleResponse> roles;

    @Data
    public static class RoleResponse {

        private String role;

        private Integer groupId;
    }
}


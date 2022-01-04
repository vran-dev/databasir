package com.databasir.core.domain.login.data;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LoginKeyResponse {

    private Integer userId;

    private String accessToken;

    private LocalDateTime accessTokenExpireAt;

    private String refreshToken;

    private LocalDateTime refreshTokenExpireAt;

    public boolean accessTokenIsExpired() {
        return accessTokenExpireAt.isBefore(LocalDateTime.now());
    }

    public boolean refreshTokenIsExpired() {
        return refreshTokenExpireAt.isBefore(LocalDateTime.now());
    }
}

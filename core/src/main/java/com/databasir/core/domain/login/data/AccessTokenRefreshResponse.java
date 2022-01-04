package com.databasir.core.domain.login.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenRefreshResponse {

    private String accessToken;

    private Long accessTokenExpireAt;
}

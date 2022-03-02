package com.databasir.core.domain.app.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OAuthAppDetailResponse {

    private Integer id;

    private String appName;

    private String appIcon;

    private String appType;

    private String registrationId;

    private String clientId;

    private String clientSecret;

    private String authUrl;

    private String resourceUrl;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;
}

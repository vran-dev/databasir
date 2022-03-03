package com.databasir.core.domain.app.data;

import com.databasir.dao.enums.OAuthAppType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OAuthAppCreateRequest {

    @NotNull
    private String registrationId;

    @NotBlank
    private String appName;

    @NotNull
    private OAuthAppType appType;

    private String appIcon;

    @NotBlank
    private String authUrl;

    @NotBlank
    private String resourceUrl;

    @NotBlank
    private String clientId;

    @NotBlank
    private String clientSecret;

    private String scope;
}

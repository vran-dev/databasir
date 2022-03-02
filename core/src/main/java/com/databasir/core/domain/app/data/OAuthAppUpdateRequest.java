package com.databasir.core.domain.app.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OAuthAppUpdateRequest {

    @NotNull
    private Integer id;

    @NotBlank
    private String registrationId;

    @NotBlank
    private String appName;

    @NotBlank
    private String appType;

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

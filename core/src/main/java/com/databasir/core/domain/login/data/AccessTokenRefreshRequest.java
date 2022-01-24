package com.databasir.core.domain.login.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccessTokenRefreshRequest {

    @NotBlank
    private String refreshToken;

}

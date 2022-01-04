package com.databasir.core.domain.user.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}

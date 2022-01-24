package com.databasir.core.domain.user.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserCreateRequest {

    private String avatar;

    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Boolean enabled;

}

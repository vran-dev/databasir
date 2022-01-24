package com.databasir.core.domain.user.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserPasswordUpdateRequest {

    @NotBlank
    private String originPassword;

    @NotBlank
    @Size(min = 6, max = 32)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 32)
    private String confirmNewPassword;

}

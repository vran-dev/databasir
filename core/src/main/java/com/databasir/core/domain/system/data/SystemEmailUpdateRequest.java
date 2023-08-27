package com.databasir.core.domain.system.data;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SystemEmailUpdateRequest {

    @NotBlank
    private String username;

    private String password;

    @Email
    private String mailFrom;

    @NotBlank
    private String smtpHost;

    @NotNull
    @Min(0L)
    @Max(65535L)
    private Integer smtpPort;

    @NotNull
    private Boolean useSSL;

}

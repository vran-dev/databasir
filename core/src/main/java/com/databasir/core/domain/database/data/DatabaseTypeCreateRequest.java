package com.databasir.core.domain.database.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DatabaseTypeCreateRequest {

    @NotBlank
    private String databaseType;

    private String icon;

    @NotBlank
    private String description;

    @NotBlank
    private String jdbcDriverFileUrl;

    @NotBlank
    private String jdbcDriverClassName;

    @NotBlank
    private String jdbcProtocol;
}

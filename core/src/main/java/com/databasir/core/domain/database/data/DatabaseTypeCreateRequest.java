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

    private String jdbcDriverFileUrl;

    private String jdbcDriverFilePath;

    @NotBlank
    private String jdbcDriverClassName;

    @NotBlank
    private String jdbcProtocol;

    @NotBlank
    private String urlPattern;

}

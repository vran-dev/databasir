package com.databasir.core.domain.database.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DatabaseTypeUpdateRequest {

    @NotNull
    private Integer id;

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

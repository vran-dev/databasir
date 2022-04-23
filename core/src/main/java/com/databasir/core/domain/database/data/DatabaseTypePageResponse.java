package com.databasir.core.domain.database.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DatabaseTypePageResponse {

    private Integer id;

    private String databaseType;

    private String icon;

    private String description;

    private String jdbcDriverFileUrl;

    private String jdbcDriverFilePath;

    private String jdbcDriverClassName;

    private String jdbcProtocol;

    private Boolean deleted;

    private Integer deletedToken;

    private Integer projectCount;

    private String urlPattern;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;
}

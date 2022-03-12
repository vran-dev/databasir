package com.databasir.core.domain.database.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DatabaseTypeDetailResponse {

    private Integer id;

    private String databaseType;

    private String icon;

    private String description;

    private String jdbcDriverFileUrl;

    private String jdbcDriverClassName;

    private String jdbcProtocol;

    private String urlPattern;

    private Boolean deleted;

    private Integer deletedToken;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;
}

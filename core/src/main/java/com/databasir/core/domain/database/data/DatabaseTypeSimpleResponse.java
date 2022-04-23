package com.databasir.core.domain.database.data;

import lombok.Data;

@Data
public class DatabaseTypeSimpleResponse {

    private String databaseType;

    private String description;

    private String urlPattern;

    private String jdbcProtocol;

    private String icon;
}

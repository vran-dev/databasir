package com.databasir.core.domain.database.data;

import lombok.Data;

@Data
public class DriverClassNameResolveRequest {

    private String databaseType;

    private String jdbcDriverFileUrl;

    private String jdbcDriverFilePath;
}

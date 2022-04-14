package com.databasir.core.domain.database.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DriverClassNameResolveRequest {

    private String databaseType;

    @NotBlank
    private String jdbcDriverFileUrl;

}

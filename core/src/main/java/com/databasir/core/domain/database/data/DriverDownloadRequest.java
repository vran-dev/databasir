package com.databasir.core.domain.database.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DriverDownloadRequest {

    @NotBlank
    private String databaseType;

    @NotBlank
    private String jdbcDriverFileUrl;

}

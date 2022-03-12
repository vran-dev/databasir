package com.databasir.core.domain.project.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectTestConnectionRequest {

    private Integer projectId;

    @NotBlank
    private String username;

    private String password;

    @NotBlank
    private String url;

    @NotBlank
    private String databaseName;

    @NotBlank
    private String schemaName;

    @NotBlank
    private String databaseType;

    private List<DataSourcePropertyValue> properties = new ArrayList<>();
}

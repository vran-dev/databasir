package com.databasir.core.domain.project.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectUpdateRequest {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private ProjectUpdateRequest.DataSourceUpdateRequest dataSource;

    @NotNull
    private ProjectUpdateRequest.ProjectSyncRuleUpdateRequest projectSyncRule;

    @Data
    public static class DataSourceUpdateRequest {

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

    @Data
    public static class ProjectSyncRuleUpdateRequest {

        private List<String> ignoreTableNameRegexes = new ArrayList<>();

        private List<String> ignoreColumnNameRegexes = new ArrayList<>();

        @NotNull
        private Boolean isAutoSync;

        private String autoSyncCron;

    }
}

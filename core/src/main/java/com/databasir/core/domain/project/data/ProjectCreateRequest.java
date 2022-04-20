package com.databasir.core.domain.project.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class ProjectCreateRequest {

    @NotBlank
    private String name;

    private Optional<String> description = Optional.empty();

    @NotNull
    private Integer groupId;

    @NotNull
    private ProjectCreateRequest.DataSourceCreateRequest dataSource;

    @NotNull
    private ProjectCreateRequest.ProjectSyncRuleCreateRequest projectSyncRule;

    @Data
    public static class DataSourceCreateRequest {

        @NotBlank
        private String username;

        @NotBlank
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
    public static class ProjectSyncRuleCreateRequest {

        private List<String> ignoreTableNameRegexes = new ArrayList<>();

        private List<String> ignoreColumnNameRegexes = new ArrayList<>();

        private Boolean isAutoSync = false;

        private String autoSyncCron;

    }

}

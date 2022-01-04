package com.databasir.core.domain.project.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

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
        private String databaseType;

        private List<DataSourcePropertyValue> properties = new ArrayList<>();

    }

    @Data
    public static class ProjectSyncRuleCreateRequest {

        private List<String> ignoreTableNameRegexes = new ArrayList<>();

        private List<String> ignoreColumnNameRegexes = new ArrayList<>();

    }

}

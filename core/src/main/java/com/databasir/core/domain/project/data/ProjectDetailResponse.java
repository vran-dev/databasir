package com.databasir.core.domain.project.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDetailResponse {

    private Integer id;

    private String name;

    private String description;

    private DataSourceResponse dataSource;

    private ProjectSyncRuleResponse projectSyncRule;

    private LocalDateTime createAt;

    @Data
    public static class DataSourceResponse {

        private Integer id;

        private String username;

        private String url;

        private String databaseName;

        private String databaseType;

        private List<DataSourcePropertyValue> properties = new ArrayList<>();

        private LocalDateTime updateAt;

        private LocalDateTime createAt;

    }

    @Data
    public static class ProjectSyncRuleResponse {

        private Integer id;

        private List<String> ignoreTableNameRegexes = new ArrayList<>();

        private List<String> ignoreColumnNameRegexes = new ArrayList<>();

        private LocalDateTime createAt;
    }
}

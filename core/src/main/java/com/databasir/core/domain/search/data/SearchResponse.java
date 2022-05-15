package com.databasir.core.domain.search.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponse {

    @Builder.Default
    private List<GroupSearchResult> groups = Collections.emptyList();

    @Builder.Default
    private List<ProjectSearchResult> projects = Collections.emptyList();

    @Data
    public static class GroupSearchResult {

        private Integer id;

        private String name;

        private String description;
    }

    @Data
    public static class ProjectSearchResult {

        private Integer projectId;

        private Integer groupId;

        private String groupName;

        private String projectName;

        private String projectDescription;

        private String databaseName;

        private String schemaName;

    }
}

package com.databasir.core.domain.document.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableDocumentResponse {

    private Integer id;

    private String name;

    private String type;

    private String comment;

    private Integer discussionCount;

    private String description;

    @Builder.Default
    private List<ColumnDocumentResponse> columns = new ArrayList<>();

    @Builder.Default
    private List<IndexDocumentResponse> indexes = new ArrayList<>();

    @Builder.Default
    private List<ForeignKeyDocumentResponse> foreignKeys = new ArrayList<>();

    @Builder.Default
    private List<TriggerDocumentResponse> triggers = new ArrayList<>();

    private LocalDateTime createAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ColumnDocumentResponse {
        private Integer id;

        private String name;

        private String type;

        private Integer size;

        private Integer decimalDigits;

        private String comment;

        private String description;

        private Boolean isPrimaryKey;

        private String nullable;

        private String autoIncrement;

        private String defaultValue;

        private Integer discussionCount;

        private LocalDateTime createAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IndexDocumentResponse {

        private Integer id;

        private String name;

        private Boolean isUnique;

        @Builder.Default
        private List<String> columnNames = new ArrayList<>();

        private LocalDateTime createAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ForeignKeyDocumentResponse {

        private Integer id;

        private String fkName;

        private String fkTableName;

        private String fkColumnName;

        private String pkName;

        private String pkTableName;

        private String pkColumnName;

        private String updateRule;

        private String deleteRule;

        private LocalDateTime createAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TriggerDocumentResponse {

        private Integer id;

        private String name;

        private String timing;

        private String manipulation;

        private String statement;

        private String triggerCreateAt;

        private LocalDateTime createAt;
    }
}
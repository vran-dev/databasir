package com.databasir.core.domain.document.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DatabaseDocumentSimpleResponse {

    private Integer id;

    private String databaseName;

    private String schemaName;

    private String productName;

    private String productVersion;

    private Integer documentVersion;

    private List<TableData> tables = new ArrayList<>();

    private LocalDateTime createAt;

    @Data
    public static class TableData {

        private Integer id;

        private String name;

        private String type;

        private String comment;

        private Integer discussionCount;

        private String description;
    }
}

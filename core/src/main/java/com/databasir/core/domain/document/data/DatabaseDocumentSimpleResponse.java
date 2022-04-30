package com.databasir.core.domain.document.data;

import com.databasir.core.diff.data.DiffType;
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

    private DiffType diffType;

    @Data
    public static class TableData implements DiffAble<TableData> {

        private Integer id;

        private String name;

        private String type;

        private String comment;

        private Integer discussionCount;

        private String description;

        private DiffType diffType;

        private TableData original;
    }
}

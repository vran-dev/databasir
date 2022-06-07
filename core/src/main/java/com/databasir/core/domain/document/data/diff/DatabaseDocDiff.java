package com.databasir.core.domain.document.data.diff;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
public class DatabaseDocDiff {

    private Integer id;

    private Integer projectId;

    private String databaseName;

    private String schemaName;

    private String productName;

    private String productVersion;

    private Long version;

    private List<TableDocDiff> tables = Collections.emptyList();

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

}

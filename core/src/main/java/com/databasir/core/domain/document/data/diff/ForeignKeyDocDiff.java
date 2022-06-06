package com.databasir.core.domain.document.data.diff;

import com.databasir.core.diff.data.DiffType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ForeignKeyDocDiff implements DiffAble<ForeignKeyDocDiff> {

    private Integer id;

    private Integer tableDocumentId;

    private Integer databaseDocumentId;

    private Integer keySeq;

    private String fkName;

    private String fkTableName;

    private String fkColumnName;

    private String pkName;

    private String pkTableName;

    private String pkColumnName;

    private String updateRule;

    private String deleteRule;

    private LocalDateTime createAt;

    private DiffType diffType;

    private ForeignKeyDocDiff original;
}

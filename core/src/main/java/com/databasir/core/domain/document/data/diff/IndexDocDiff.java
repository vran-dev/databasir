package com.databasir.core.domain.document.data.diff;

import com.databasir.core.diff.data.DiffType;
import lombok.Data;
import org.jooq.JSON;

import java.time.LocalDateTime;

@Data
public class IndexDocDiff implements DiffAble<IndexDocDiff> {

    private Integer id;

    private Integer tableDocumentId;

    private Integer databaseDocumentId;

    private String name;

    private Boolean isUnique;

    private JSON columnNameArray;

    private LocalDateTime createAt;

    private DiffType diffType;

    private IndexDocDiff original;
}

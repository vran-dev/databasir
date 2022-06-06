package com.databasir.core.domain.document.data.diff;

import com.databasir.core.diff.data.DiffType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ColumnDocDiff implements DiffAble<ColumnDocDiff> {

    private Integer id;

    private Integer tableDocumentId;

    private Integer databaseDocumentId;

    private String name;

    private String type;

    private Integer dataType;

    private String comment;

    private String defaultValue;

    private Integer size;

    private Integer decimalDigits;

    private Boolean isPrimaryKey;

    private String nullable;

    private String autoIncrement;

    private LocalDateTime createAt;

    private DiffType diffType;

    private ColumnDocDiff original;
}

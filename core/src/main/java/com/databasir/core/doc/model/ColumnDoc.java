package com.databasir.core.doc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnDoc {

    private String name;

    private String comment;

    private String type;

    private String defaultValue;

    private Integer size;

    private Integer decimalDigits;

    private Boolean isNullable;

    private Boolean isAutoIncrement;

}
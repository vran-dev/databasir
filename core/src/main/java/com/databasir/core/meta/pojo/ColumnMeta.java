package com.databasir.core.meta.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnMeta {

    private String name;

    private String comment;

    private String type;

    private String defaultValue;

    private Integer size;

    private Integer decimalDigits;

    private Boolean isNullable;

    private Boolean isAutoIncrement;

}
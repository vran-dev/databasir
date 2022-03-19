package com.databasir.core.meta.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnMeta {

    private String name;

    private String comment;

    private String type;

    /**
     * if default value is empty string, will be converted to ''.
     */
    private String defaultValue;

    private Integer size;

    private Integer decimalDigits;

    private String nullable;

    private String autoIncrement;

    private Boolean isPrimaryKey;
}
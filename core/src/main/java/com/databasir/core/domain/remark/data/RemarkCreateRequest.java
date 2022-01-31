package com.databasir.core.domain.remark.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RemarkCreateRequest {

    @NotBlank
    private String remark;

    @NotNull
    private String tableName;

    private String columnName;
}

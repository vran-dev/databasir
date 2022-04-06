package com.databasir.core.domain.mock.data;

import com.databasir.dao.enums.MockDataType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ColumnMockRuleSaveRequest {

    @NotBlank
    private String tableName;

    @NotBlank
    private String columnName;

    private String dependentTableName;

    private String dependentColumnName;

    @NotNull
    private MockDataType mockDataType;

    private String mockDataScript;

}

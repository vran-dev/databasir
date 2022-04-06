package com.databasir.core.domain.mock.data;

import com.databasir.dao.enums.MockDataType;
import lombok.Data;

@Data
public class MockDataRuleResponse {

    private String tableName;

    private String columnName;

    private String columnType;

    private String dependentTableName;

    private String dependentColumnName;

    private MockDataType mockDataType;

    private String mockDataScript;

}

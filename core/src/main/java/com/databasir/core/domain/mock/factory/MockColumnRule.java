package com.databasir.core.domain.mock.factory;

import com.databasir.dao.enums.MockDataType;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class MockColumnRule {

    private String tableName;

    private String columnName;

    private String columnType;

    private Integer dataType;

    private MockDataType mockDataType;

    private String mockDataScript;

    public static MockColumnRule auto(String tableName, String columnName) {
        return MockColumnRule.builder()
                .tableName(tableName)
                .columnName(columnName)
                .mockDataType(MockDataType.AUTO)
                .build();
    }

    public Optional<String> getMockDataScript() {
        return Optional.ofNullable(mockDataScript);
    }
}

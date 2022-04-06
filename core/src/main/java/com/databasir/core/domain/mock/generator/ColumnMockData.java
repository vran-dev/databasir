package com.databasir.core.domain.mock.generator;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColumnMockData {

    private String columnName;

    private String columnType;

    private String mockData;

}

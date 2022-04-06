package com.databasir.core.domain.mock.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class TableMockData {

    private String tableName;

    private List<ColumnMockData> columnMockData;

    public static TableMockData of(String tableName, List<ColumnMockData> columnMockData) {
        return new TableMockData(tableName, columnMockData);
    }

    public void addColumnIfNotExists(ColumnMockData data) {
        boolean present = columnMockData.stream()
                .anyMatch(col -> Objects.equals(col.getColumnName(), data.getColumnName()));
        if (present) {
            return;
        }
        this.columnMockData.add(data);
    }

    public boolean containsColumn(String columnName) {
        return columnMockData.stream()
                .anyMatch(col -> Objects.equals(col.getColumnName(), columnName));
    }
}

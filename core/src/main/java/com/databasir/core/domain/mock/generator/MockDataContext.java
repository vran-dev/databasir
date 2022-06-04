package com.databasir.core.domain.mock.generator;

import com.databasir.core.domain.DomainErrors;
import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.pojos.MockDataRule;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import lombok.Builder;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Builder
public class MockDataContext {

    private Integer projectId;

    private Integer databaseDocumentId;

    @Builder.Default
    private Map<String, TableMockData> tableMockDataMap = new LinkedHashMap<>();

    @Builder.Default
    private Map<String, Set<String>> fromReference = new LinkedHashMap<>();

    @Builder.Default
    private Map<String, Set<String>> toReference = new LinkedHashMap<>();

    @Builder.Default
    private Map<String, Map<String, MockDataRule>> ruleMap = new LinkedHashMap<>(16);

    @Builder.Default
    private Map<String, Map<String, TableColumnDocument>> tableColumnMap = new LinkedHashMap<>(16);

    @Builder.Default
    private Map<String, Set<String>> mockInProgress = new HashMap<>();

    public void addTableMockRules(String tableName, List<MockDataRule> rules) {
        var columnRuleMap = rules.stream()
                .collect(Collectors.toMap(MockDataRule::getColumnName, Function.identity()));
        this.ruleMap.put(tableName, columnRuleMap);
    }

    public boolean containMockRule(String tableName) {
        return ruleMap.containsKey(tableName);
    }

    public boolean containMockRule(String tableName, String columnName) {
        if (!ruleMap.containsKey(tableName)) {
            return false;
        }
        return ruleMap.get(tableName).containsKey(columnName);
    }

    public Optional<MockDataRule> getMockRule(String tableName, String columnName) {
        if (!ruleMap.containsKey(tableName)) {
            return Optional.empty();
        }
        return Optional.ofNullable(ruleMap.get(tableName).get(columnName));
    }

    public void addTableColumns(String tableName, List<TableColumnDocument> columns) {
        Map<String, TableColumnDocument> columnMap = new LinkedHashMap<>();
        for (TableColumnDocument column : columns) {
            columnMap.put(column.getName(), column);
        }
        this.tableColumnMap.put(tableName, columnMap);
    }

    public boolean containsTable(String tableName) {
        return tableColumnMap.containsKey(tableName);
    }

    public boolean containsTableColumn(String tableName, String columnName) {
        if (!tableColumnMap.containsKey(tableName)) {
            return false;
        }
        return tableColumnMap.get(tableName).containsKey(columnName);
    }

    public TableColumnDocument getTableColumn(String tableName, String columnName) {
        if (!tableColumnMap.containsKey(tableName)) {
            return null;
        }
        return tableColumnMap.get(tableName).get(columnName);
    }

    public boolean containsTableMockData(String tableName) {
        return tableMockDataMap.containsKey(tableName);
    }

    public boolean containsColumnMockData(String tableName, String columName) {
        return tableMockDataMap.containsKey(tableName) && tableMockDataMap.get(tableName).containsColumn(columName);
    }

    public void addTableMockData(TableMockData tableMockData) {
        this.tableMockDataMap.put(tableMockData.getTableName(), tableMockData);
    }

    public TableMockData getTableMockData(String tableName) {
        return this.tableMockDataMap.get(tableName);
    }

    public void addColumnMockData(String tableName, ColumnMockData columnMockData) {
        TableMockData mock =
                tableMockDataMap.computeIfAbsent(tableName, key -> TableMockData.of(tableName, new ArrayList<>()));
        mock.addColumnIfNotExists(columnMockData);
        // sort to last
        tableMockDataMap.remove(tableName);
        tableMockDataMap.put(tableName, mock);
    }

    public String getRawColumnMockData(String tableName, String columnName) {
        if (!this.containsTableMockData(tableName)) {
            throw new DataNotExistsException("can't find table mock data by " + tableName);
        }
        return getTableMockData(tableName)
                .getColumnMockData()
                .stream()
                .filter(t -> t.getColumnName().equals(columnName))
                .findFirst()
                .map(ColumnMockData::getMockData)
                .orElseThrow(DataNotExistsException::new);
    }

    public boolean isMockInProgress(String tableName, String columnName) {
        return this.mockInProgress.containsKey(tableName) && this.mockInProgress.get(tableName).contains(columnName);
    }

    public void addMockInProgress(String tableName, String columnName) {
        Set<String> inProgress = this.mockInProgress.computeIfAbsent(tableName, key -> new HashSet<>());
        inProgress.add(columnName);
    }

    public void removeMockInProgress(String tableName, String columnName) {
        Set<String> inProgress = this.mockInProgress.computeIfAbsent(tableName, key -> new HashSet<>());
        inProgress.remove(columnName);
    }

    public String toInsertSql() {
        return tableMockDataMap.entrySet()
                .stream()
                .map(entry -> {
                    String tableName = entry.getKey();
                    List<ColumnMockData> columns = entry.getValue().getColumnMockData();
                    String format = "insert into %s (%s)\nvalues\n(%s);";
                    String columnNames = columns.stream()
                            .map(c -> "" + c.getColumnName() + "")
                            .collect(Collectors.joining(", "));
                    String columnValues = columns.stream()
                            .map(ColumnMockData::getMockData)
                            .collect(Collectors.joining(", "));
                    return String.format(format, tableName, columnNames, columnValues);
                })
                .collect(Collectors.joining("\n\n"));
    }

    public void saveReference(String fromTable, String fromColumn, String toTable, String toColumn) {
        if (toReference.containsKey(fromTable) && toReference.get(fromTable).contains(fromColumn)) {
            if (fromReference.containsKey(toTable) && fromReference.get(toTable).contains(toColumn)) {
                String format = "%s 和 %s 出现了循环引用";
                String message = String.format(format, fromTable + "." + fromColumn, toTable + "." + toColumn);
                throw DomainErrors.CIRCLE_REFERENCE.exception(message);
            }
        }
        Set<String> fromColumns = this.fromReference.computeIfAbsent(fromTable, key -> new HashSet<>());
        fromColumns.add(fromColumn);
        Set<String> toColumns = this.toReference.computeIfAbsent(toTable, key -> new HashSet<>());
        toColumns.add(toColumn);
    }
}

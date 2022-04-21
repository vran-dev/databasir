package com.databasir.core.meta.repository.impl.jdbc;

import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.repository.ColumnMetaRepository;
import com.databasir.core.meta.repository.condition.TableCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JdbcColumnMetaRepository implements ColumnMetaRepository {

    @Override
    public List<ColumnMeta> selectColumns(Connection connection, TableCondition tableCondition) {
        try {
            return doSelect(connection, tableCondition);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<ColumnMeta> doSelect(Connection connection, TableCondition tableCondition) throws SQLException {
        List<ColumnMeta> columnDocs = new ArrayList<>();
        String databaseName = tableCondition.getDatabaseName();
        String tableName = tableCondition.getTableName();
        List<String> primaryKeyColumns = selectPrimaryKeyColumns(connection.getMetaData(), tableCondition);
        ResultSet columnsResult;
        try {
            columnsResult = connection.getMetaData()
                    .getColumns(databaseName, tableCondition.getSchemaName(), tableName, null);
        } catch (SQLException e) {
            log.warn("warn: ignore columns in " + databaseName + "." + tableName + ", error: " + e.getMessage());
            return columnDocs;
        }
        try {
            while (columnsResult.next()) {
                String columnName = columnsResult.getString("COLUMN_NAME");
                if (tableCondition.columnIsIgnored(columnName)) {
                    if (log.isWarnEnabled()) {
                        log.warn("ignore column: " + columnName);
                    }
                } else {
                    String defaultValue = columnsResult.getString("COLUMN_DEF");
                    String isNullable = columnsResult.getString("IS_NULLABLE");
                    if (isNullable.trim().equals("")) {
                        isNullable = "UNKNOWN";
                    }
                    String isAutoIncrement = columnsResult.getString("IS_AUTOINCREMENT");
                    if (isAutoIncrement.trim().equals("")) {
                        isAutoIncrement = "UNKNOWN";
                    }
                    if (defaultValue != null && defaultValue.trim().equals("")) {
                        defaultValue = "'" + defaultValue + "'";
                    }
                    Integer decimalDigits;
                    if (columnsResult.getObject("DECIMAL_DIGITS") == null) {
                        decimalDigits = null;
                    } else {
                        decimalDigits = columnsResult.getInt("DECIMAL_DIGITS");
                    }
                    Integer columnSize = columnsResult.getInt("COLUMN_SIZE");
                    String columnType = columnsResult.getString("TYPE_NAME");
                    String columnComment = columnsResult.getString("REMARKS");
                    int dataType = columnsResult.getInt("DATA_TYPE");
                    ColumnMeta columnMeta = ColumnMeta.builder()
                            .name(columnName)
                            .dataType(dataType)
                            .type(columnType)
                            .size(columnSize)
                            .decimalDigits(decimalDigits)
                            .nullable(isNullable)
                            .autoIncrement(isAutoIncrement)
                            .comment(columnComment)
                            .defaultValue(defaultValue)
                            .isPrimaryKey(primaryKeyColumns.contains(columnName))
                            .build();
                    columnDocs.add(columnMeta);
                }
            }
        } finally {
            columnsResult.close();
        }
        return columnDocs;
    }

    private List<String> selectPrimaryKeyColumns(DatabaseMetaData meta,
                                                 TableCondition tableCondition) throws SQLException {
        ResultSet result = meta.getPrimaryKeys(tableCondition.getDatabaseName(),
                tableCondition.getSchemaName(), tableCondition.getTableName());
        List<String> columns = new ArrayList<>();
        while (result.next()) {
            String columnName = result.getString("COLUMN_NAME");
            columns.add(columnName);
        }
        return columns;
    }
}

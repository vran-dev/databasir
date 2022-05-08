package com.databasir.core.meta.provider.jdbc;

import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.provider.ColumnMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
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
public class JdbcColumnMetaProvider implements ColumnMetaProvider {

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
                    String autoIncrement = retrieveAutoIncrement(columnsResult);
                    ColumnMeta columnMeta = ColumnMeta.builder()
                            .name(columnName)
                            .dataType(dataType)
                            .type(columnType)
                            .size(columnSize)
                            .decimalDigits(decimalDigits)
                            .nullable(isNullable)
                            .autoIncrement(autoIncrement)
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

    private String retrieveAutoIncrement(ResultSet columnsResult) {
        try {
            return retrieveAutoIncrement(columnsResult, "IS_AUTOINCREMENT");
        } catch (SQLException e) {
            log.warn("get is_autoincrement failed, fallback to is_auto_increment, error: " + e.getMessage());
            try {
                // hive jdbc driver doesn't support is_autoincrement, fallback to is_auto_increment
                return retrieveAutoIncrement(columnsResult, "is_auto_increment");
            } catch (SQLException ex) {
                log.warn("get is_auto_increment failed, error: " + ex.getMessage());
                return "UNKNOWN";
            }
        }
    }

    private String retrieveAutoIncrement(ResultSet columnsResult, String columnName) throws SQLException {
        String isAutoIncrement = columnsResult.getString(columnName);
        if (isAutoIncrement.trim().equals("")) {
            return "UNKNOWN";
        }
        return isAutoIncrement;
    }

    private List<String> selectPrimaryKeyColumns(DatabaseMetaData meta,
                                                 TableCondition tableCondition) throws SQLException {
        ResultSet result = meta.getPrimaryKeys(tableCondition.getDatabaseName(),
                tableCondition.getSchemaName(), tableCondition.getTableName());
        try {
            List<String> columns = new ArrayList<>();
            while (result.next()) {
                String columnName = result.getString("COLUMN_NAME");
                columns.add(columnName);
            }
            return columns;
        } finally {
            result.close();
        }
    }
}

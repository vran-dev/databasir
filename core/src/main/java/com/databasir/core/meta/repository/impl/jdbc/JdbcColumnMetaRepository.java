package com.databasir.core.meta.repository.impl.jdbc;

import com.databasir.core.meta.pojo.ColumnMeta;
import com.databasir.core.meta.repository.ColumnMetaRepository;
import com.databasir.core.meta.repository.condition.TableCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        ResultSet columnsResult;
        try {
            columnsResult = connection.getMetaData().getColumns(databaseName, null, tableName, null);
        } catch (SQLException e) {
            log.warn("warn: ignore columns in " + databaseName + "." + tableName);
            return columnDocs;
        }
        while (columnsResult.next()) {
            String columnName = columnsResult.getString("COLUMN_NAME");
            if (tableCondition.columnIsIgnored(columnName)) {
                if (log.isWarnEnabled()) {
                    log.warn("ignore column: " + columnName);
                }
            } else {
                String columnType = columnsResult.getString("TYPE_NAME");
                Integer columnSize = columnsResult.getInt("COLUMN_SIZE");
                Integer decimalDigits = columnsResult.getInt("DECIMAL_DIGITS");
                String defaultValue = columnsResult.getString("COLUMN_DEF");
                boolean isNullable = Objects.equals("YES", columnsResult.getString("IS_NULLABLE"));
                boolean isAutoIncrement = Objects.equals("YES", columnsResult.getString("IS_AUTOINCREMENT"));
                String columnComment = columnsResult.getString("REMARKS");
                ColumnMeta columnMeta = ColumnMeta.builder()
                        .name(columnName)
                        .type(columnType)
                        .size(columnSize)
                        .decimalDigits(decimalDigits)
                        .isNullable(isNullable)
                        .isAutoIncrement(isAutoIncrement)
                        .comment(columnComment)
                        .defaultValue(defaultValue)
                        .build();
                columnDocs.add(columnMeta);
            }

        }
        return columnDocs;
    }
}

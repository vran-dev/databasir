package com.databasir.core.meta.provider.sqlserver;

import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.provider.ColumnMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
import com.databasir.core.meta.provider.jdbc.JdbcColumnMetaProvider;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class SqlServerColumnMetaProvider implements ColumnMetaProvider {

    private final ColumnMetaProvider columnMetaProvider = new JdbcColumnMetaProvider();

    @Override
    public List<ColumnMeta> selectColumns(Connection connection, TableCondition condition) {
        Map<String, String> columnRemarksMap = getColumnRemarks(connection, condition);
        return columnMetaProvider.selectColumns(connection, condition)
                .stream()
                .map(column -> {
                    String remark = columnRemarksMap.getOrDefault(column.getName(), "");
                    column.setComment(remark);
                    return column;
                })
                .collect(Collectors.toList());
    }

    private Map<String, String> getColumnRemarks(Connection connection,
                                                 TableCondition condition) {
        String sql = "SELECT col.name AS COLUMN_NAME,\n"
                + "       ep.value AS REMARKS\n"
                + "FROM sys.tables AS tab\n"
                + "         INNER JOIN sys.columns AS col\n"
                + "                    ON tab.object_id = col.object_id\n"
                + "         LEFT JOIN sys.extended_properties AS ep "
                + "ON ep.major_id = col.object_id AND ep.minor_id = col.column_id\n"
                + "WHERE tab.name LIKE ?\n"
                + "  AND SCHEMA_NAME(tab.schema_id) LIKE ?\n"
                + "ORDER BY tab.name, column_id;";

        Map<String, String> map = new HashMap<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, condition.getTableName());
            preparedStatement.setString(2, condition.getSchemaName());
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                String name = result.getString("COLUMN_NAME");
                String remarks = result.getString("REMARKS");
                if (name == null || remarks == null) {
                    continue;
                } else {
                    map.put(name, remarks);
                }
            }
        } catch (SQLException e) {
            log.error("", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
        return map;
    }
}

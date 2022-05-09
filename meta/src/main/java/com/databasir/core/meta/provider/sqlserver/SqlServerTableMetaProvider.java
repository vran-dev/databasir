package com.databasir.core.meta.provider.sqlserver;

import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.meta.provider.*;
import com.databasir.core.meta.provider.condition.Condition;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SqlServerTableMetaProvider implements TableMetaProvider {

    private final ColumnMetaProvider columnMetaProvider;

    private final IndexMetaProvider indexMetaProvider;

    private final ForeignKeyMetaProvider foreignKeyMetaProvider;

    private final TriggerMetaProvider triggerMetaProvider;

    @Override
    public List<TableMeta> selectTables(Connection connection, Condition condition) {
        String sql = "SELECT sys.objects.name                            AS TABLE_NAME,\n" +
                "       sys.objects.type_desc                            AS TABLE_TYPE,\n" +
                "       CAST(extended_properties.value AS NVARCHAR(500)) AS REMARKS\n" +
                "FROM sys.objects\n" +
                "         LEFT JOIN sys.schemas ON sys.objects.schema_id = sys.schemas.schema_id\n" +
                "         LEFT JOIN sys.extended_properties " +
                "ON sys.objects.object_id = sys.extended_properties.major_id\n" +
                "WHERE (type = 'U' OR type = 'V')\n" +
                "  AND sys.extended_properties.minor_id = 0\n" +
                "  AND sys.schemas.name LIKE ?;\n";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, condition.getSchemaName());
            ResultSet results = preparedStatement.executeQuery();
            return doSelect(results, connection, condition);
        } catch (SQLException e) {
            log.warn("get table meta failed {}", e.getMessage());
            if (log.isDebugEnabled()) {
                log.debug("get table meta failed", e);
            }
            return Collections.emptyList();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    private List<TableMeta> doSelect(ResultSet tablesResult,
                                     Connection connection,
                                     Condition condition) throws SQLException {
        List<TableMeta> tableMetas = new ArrayList<>();
        try {
            while (tablesResult.next()) {
                String tableName = tablesResult.getString("TABLE_NAME");
                if (condition.tableIsIgnored(tableName)) {
                    if (log.isWarnEnabled()) {
                        log.warn("ignored table: " + condition.getSchemaName() + "." + tableName);
                    }
                } else {
                    String tableType = tablesResult.getString("TABLE_TYPE");
                    String tableComment = tablesResult.getString("REMARKS");
                    TableCondition tableCondition = TableCondition.of(condition, tableName);
                    List<ColumnMeta> columns = columnMetaProvider.selectColumns(connection, tableCondition);
                    if (columns.isEmpty()) {
                        if (log.isWarnEnabled()) {
                            log.warn("ignored table: " + condition.getSchemaName() + "." + tableName
                                    + ", caused by get empty columns");
                        }
                        continue;
                    }
                    TableMeta tableMeta = TableMeta.builder()
                            .name(tableName)
                            .type(tableType)
                            .comment(tableComment)
                            .columns(columns)
                            .foreignKeys(foreignKeyMetaProvider.selectForeignKeys(connection, tableCondition))
                            .indexes(indexMetaProvider.selectIndexes(connection, tableCondition))
                            .triggers(triggerMetaProvider.selectTriggers(connection, tableCondition))
                            .build();
                    tableMetas.add(tableMeta);
                }
            }
        } finally {
            tablesResult.close();
        }
        return tableMetas;
    }

}

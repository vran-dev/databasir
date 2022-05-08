package com.databasir.core.meta.provider.jdbc;

import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.meta.provider.*;
import com.databasir.core.meta.provider.condition.Condition;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JdbcTableMetaProvider implements TableMetaProvider {

    private final ColumnMetaProvider columnMetaProvider;

    private final IndexMetaProvider indexMetaProvider;

    private final TriggerMetaProvider triggerMetaProvider;

    private final ForeignKeyMetaProvider foreignKeyMetaProvider;

    @Override
    public List<TableMeta> selectTables(Connection connection, Condition condition) {
        try {
            return doSelect(connection, condition);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<TableMeta> doSelect(Connection connection, Condition condition) throws SQLException {
        List<TableMeta> tableMetas = new ArrayList<>();
        String databaseName = condition.getDatabaseName();
        ResultSet tablesResult = connection.getMetaData()
                .getTables(databaseName, condition.getSchemaName(), null, new String[]{"TABLE"});
        try {
            while (tablesResult.next()) {
                String tableName = tablesResult.getString("TABLE_NAME");
                if (condition.tableIsIgnored(tableName)) {
                    if (log.isWarnEnabled()) {
                        log.warn("ignored table: " + databaseName + "." + tableName);
                    }
                } else {
                    String tableType = tablesResult.getString("TABLE_TYPE");
                    String tableComment = tablesResult.getString("REMARKS");
                    TableCondition tableCondition = TableCondition.of(condition, tableName);
                    List<ColumnMeta> columns = columnMetaProvider.selectColumns(connection, tableCondition);
                    if (columns.isEmpty()) {
                        if (log.isWarnEnabled()) {
                            log.warn("ignored table: " + databaseName + "." + tableName
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

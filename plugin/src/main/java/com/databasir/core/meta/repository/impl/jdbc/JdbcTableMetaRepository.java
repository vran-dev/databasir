package com.databasir.core.meta.repository.impl.jdbc;

import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.meta.repository.*;
import com.databasir.core.meta.repository.condition.Condition;
import com.databasir.core.meta.repository.condition.TableCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JdbcTableMetaRepository implements TableMetaRepository {

    private final ColumnMetaRepository columnMetaRepository;

    private final IndexMetaRepository indexMetaRepository;

    private final TriggerMetaRepository triggerMetaRepository;

    private final ForeignKeyMetaRepository foreignKeyMetaRepository;

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
                    List<ColumnMeta> columns = columnMetaRepository.selectColumns(connection, tableCondition);
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
                            .foreignKeys(foreignKeyMetaRepository.selectForeignKeys(connection, tableCondition))
                            .indexes(indexMetaRepository.selectIndexes(connection, tableCondition))
                            .triggers(triggerMetaRepository.selectTriggers(connection, tableCondition))
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

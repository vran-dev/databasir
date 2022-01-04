package com.databasir.core.meta.repository.impl.jdbc;

import com.databasir.core.meta.data.IndexMeta;
import com.databasir.core.meta.repository.IndexMetaRepository;
import com.databasir.core.meta.repository.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class JdbcIndexMetaRepository implements IndexMetaRepository {
    @Override
    public List<IndexMeta> selectIndexes(Connection connection, TableCondition condition) {
        try {
            return doCreateIndexDocs(connection, condition);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<IndexMeta> doCreateIndexDocs(Connection connection, TableCondition condition)
            throws SQLException {
        String databaseName = condition.getDatabaseName();
        String tableName = condition.getTableName();
        List<IndexMeta> indexMetas = new ArrayList<>();
        ResultSet indexResults;
        try {
            indexResults = connection.getMetaData().getIndexInfo(databaseName, null, tableName, false, false);
        } catch (SQLException e) {
            log.warn("warn: ignore " + databaseName + "." + tableName);
            return indexMetas;
        }

        Map<String, IndexMeta> pojoGroupByName = new HashMap<>();
        while (indexResults.next()) {
            Boolean nonUnique = indexResults.getBoolean("NON_UNIQUE");
            String indexName = indexResults.getString("INDEX_NAME");
            String columnName = indexResults.getString("COLUMN_NAME");
            if (pojoGroupByName.containsKey(indexName)) {
                pojoGroupByName.get(indexName).getColumnNames().add(columnName);
            } else {
                List<String> columns = new ArrayList<>();
                columns.add(columnName);
                IndexMeta indexMeta = IndexMeta.builder()
                        .name(indexName)
                        .columnNames(columns)
                        .isPrimaryKey(Objects.equals("PRIMARY", indexName))
                        .isUniqueKey(Objects.equals(nonUnique, false))
                        .build();
                pojoGroupByName.put(indexName, indexMeta);
            }
        }
        return new ArrayList<>(pojoGroupByName.values());
    }

}

package com.databasir.core.meta.provider.jdbc;

import com.databasir.core.meta.data.IndexMeta;
import com.databasir.core.meta.provider.IndexMetaProvider;
import com.databasir.core.meta.provider.condition.TableCondition;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class JdbcIndexMetaProvider implements IndexMetaProvider {
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
            indexResults = connection.getMetaData()
                    .getIndexInfo(databaseName, condition.getSchemaName(), tableName, false, false);
        } catch (SQLException e) {
            log.warn("warn: ignore " + databaseName + "." + tableName + ", error=" + e.getMessage());
            return indexMetas;
        }

        Map<String, IndexMeta> metaGroupByName = new HashMap<>();
        try {
            while (indexResults.next()) {
                Boolean nonUnique = indexResults.getBoolean("NON_UNIQUE");
                String indexName = indexResults.getString("INDEX_NAME");
                String columnName = indexResults.getString("COLUMN_NAME");
                if (indexName == null) {
                    continue;
                }
                if (metaGroupByName.containsKey(indexName)) {
                    metaGroupByName.get(indexName).getColumnNames().add(columnName);
                } else {
                    List<String> columns = new ArrayList<>();
                    columns.add(columnName);
                    IndexMeta indexMeta = IndexMeta.builder()
                            .name(indexName)
                            .columnNames(columns)
                            .isUniqueKey(Objects.equals(nonUnique, false))
                            .build();
                    metaGroupByName.put(indexName, indexMeta);
                }
            }
        } finally {
            indexResults.close();
        }
        return new ArrayList<>(metaGroupByName.values());
    }

}

package com.databasir.core.doc.factory.jdbc;

import com.databasir.core.doc.factory.DatabaseDocConfig;
import com.databasir.core.doc.factory.TableIndexDocFactory;
import com.databasir.core.doc.model.IndexDoc;
import lombok.extern.slf4j.Slf4j;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class JdbcTableIndexDocFactory implements TableIndexDocFactory {

    @Override
    public List<IndexDoc> create(String tableName, DatabaseMetaData metaData, DatabaseDocConfig configuration) {
        try {
            return doCreateIndexDocs(tableName, metaData, configuration);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<IndexDoc> doCreateIndexDocs(String tableName,
                                             DatabaseMetaData metaData,
                                             DatabaseDocConfig configuration)
            throws SQLException {
        List<IndexDoc> indexDocs = new ArrayList<>();
        String databaseName = configuration.getDatabaseName();
        if (tableName == null || metaData == null) {
            return indexDocs;
        }
        ResultSet indexResults;
        try {
            indexResults = metaData.getIndexInfo(databaseName, null, tableName, false, false);
        } catch (SQLException e) {
            log.warn("warn: ignore " + databaseName + "." + tableName);
            return indexDocs;
        }

        Map<String, IndexDoc> docsGroupByName = new HashMap<>();
        while (indexResults.next()) {
            Boolean nonUnique = indexResults.getBoolean("NON_UNIQUE");
            String indexName = indexResults.getString("INDEX_NAME");
            String columnName = indexResults.getString("COLUMN_NAME");
            if (docsGroupByName.containsKey(indexName)) {
                docsGroupByName.get(indexName).getColumnNames().add(columnName);
            } else {
                List<String> columns = new ArrayList<>();
                columns.add(columnName);
                IndexDoc columnDoc = IndexDoc.builder()
                        .indexName(indexName)
                        .columnNames(columns)
                        .isPrimaryKey(Objects.equals("PRIMARY", indexName))
                        .isUniqueKey(Objects.equals(nonUnique, false))
                        .build();
                docsGroupByName.put(indexName, columnDoc);
            }
        }
        return new ArrayList<>(docsGroupByName.values());
    }

}

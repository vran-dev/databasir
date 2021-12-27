package com.databasir.core.doc.factory.jdbc;

import com.databasir.core.doc.factory.DatabaseDocConfiguration;
import com.databasir.core.doc.factory.*;
import com.databasir.core.doc.model.TableDoc;
import lombok.extern.slf4j.Slf4j;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcTableDocFactory implements TableDocFactory {

    @Override
    public List<TableDoc> create(DatabaseMetaData metaData,
                                 DatabaseDocConfiguration configuration) {
        try {
            return doCreateTableDoc(metaData, configuration);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<TableDoc> doCreateTableDoc(DatabaseMetaData metaData,
                                            DatabaseDocConfiguration configuration) throws SQLException {
        List<TableDoc> tableDocs = new ArrayList<>();
        if (metaData == null) {
            return tableDocs;
        }

        String databaseName = configuration.getDatabaseName();
        ResultSet tablesResult = metaData.getTables(databaseName, null, null, null);
        while (tablesResult.next()) {
            String tableName = tablesResult.getString("TABLE_NAME");
            if (configuration.ignoredTable(tableName)) {
                if (log.isDebugEnabled()) {
                    log.debug("ignore table: " + tableName);
                }
            } else {
                String tableType = tablesResult.getString("TABLE_TYPE");
                String tableComment = tablesResult.getString("REMARKS");
                TableDoc tableDoc = TableDoc.builder()
                        .tableName(tableName)
                        .tableType(tableType)
                        .tableComment(tableComment)
                        .columns(configuration.getTableColumnDocFactory().create(tableName, metaData, configuration))
                        .indexes(configuration.getTableIndexDocFactory().create(tableName, metaData, configuration))
                        .triggers(configuration.getTableTriggerDocFactory().create(tableName, metaData, configuration))
                        .build();
                tableDocs.add(tableDoc);
            }
        }
        return tableDocs;
    }

}

package com.databasir.core.doc.factory.jdbc;

import com.databasir.core.doc.factory.DatabaseDocConfig;
import com.databasir.core.doc.factory.TableColumnDocFactory;
import com.databasir.core.doc.model.ColumnDoc;
import lombok.extern.slf4j.Slf4j;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class JdbcTableColumnDocFactory implements TableColumnDocFactory {

    @Override
    public List<ColumnDoc> create(String tableName,
                                  DatabaseMetaData metaData,
                                  DatabaseDocConfig configuration) {
        try {
            return doCreate(tableName, metaData, configuration);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<ColumnDoc> doCreate(String tableName,
                                     DatabaseMetaData metaData,
                                     DatabaseDocConfig configuration) throws SQLException {
        List<ColumnDoc> columnDocs = new ArrayList<>();
        String database = configuration.getDatabaseName();
        ResultSet columnsResult;
        try {
            columnsResult = metaData.getColumns(database, null, tableName, null);
        } catch (SQLException e) {
            log.warn("warn: ignore columns in " + database + "." + tableName);
            return columnDocs;
        }
        while (columnsResult.next()) {
            String columnName = columnsResult.getString("COLUMN_NAME");
            if (configuration.columnIsIgnored(columnName)) {
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
                ColumnDoc columnDoc = ColumnDoc.builder()
                        .name(columnName)
                        .type(columnType)
                        .size(columnSize)
                        .decimalDigits(decimalDigits)
                        .isNullable(isNullable)
                        .isAutoIncrement(isAutoIncrement)
                        .comment(columnComment)
                        .defaultValue(defaultValue)
                        .build();
                columnDocs.add(columnDoc);
            }

        }
        return columnDocs;
    }

}

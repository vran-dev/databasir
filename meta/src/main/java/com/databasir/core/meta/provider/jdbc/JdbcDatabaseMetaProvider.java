package com.databasir.core.meta.provider.jdbc;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.meta.provider.DatabaseMetaProvider;
import com.databasir.core.meta.provider.TableMetaProvider;
import com.databasir.core.meta.provider.condition.Condition;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcDatabaseMetaProvider implements DatabaseMetaProvider {

    private final TableMetaProvider tableMetaProvider;

    @Override
    public Optional<DatabaseMeta> select(Connection connection, Condition condition) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                String catalogName = catalogs.getString("TABLE_CAT");
                if (Objects.equals(condition.getDatabaseName(), catalogName)) {
                    List<TableMeta> tableDocs = tableMetaProvider.selectTables(connection, condition);
                    DatabaseMeta meta = DatabaseMeta.builder()
                            .productName(metaData.getDatabaseProductName())
                            .productVersion(metaData.getDatabaseProductVersion())
                            .databaseName(catalogName)
                            .schemaName(condition.getSchemaName())
                            .tables(tableDocs)
                            .build();
                    return Optional.of(meta);
                }
            }

            ResultSet schemas = metaData.getSchemas();
            while (schemas.next()) {
                String schemaName = schemas.getString("TABLE_SCHEM");
                if (Objects.equals(condition.getSchemaName(), schemaName)) {
                    List<TableMeta> tableDocs = tableMetaProvider.selectTables(connection, condition);
                    DatabaseMeta meta = DatabaseMeta.builder()
                            .productName(metaData.getDatabaseProductName())
                            .productVersion(metaData.getDatabaseProductVersion())
                            .databaseName(condition.getDatabaseName())
                            .schemaName(condition.getSchemaName())
                            .tables(tableDocs)
                            .build();
                    return Optional.of(meta);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}

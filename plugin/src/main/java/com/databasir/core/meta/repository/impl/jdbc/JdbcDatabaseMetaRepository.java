package com.databasir.core.meta.repository.impl.jdbc;

import com.databasir.core.meta.data.DatabaseMeta;
import com.databasir.core.meta.data.TableMeta;
import com.databasir.core.meta.repository.DatabaseMetaRepository;
import com.databasir.core.meta.repository.TableMetaRepository;
import com.databasir.core.meta.repository.condition.Condition;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcDatabaseMetaRepository implements DatabaseMetaRepository {

    private final TableMetaRepository tableMetaRepository;

    @Override
    public Optional<DatabaseMeta> select(Connection connection, Condition condition) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                String catalogName = catalogs.getString("TABLE_CAT");
                if (Objects.equals(condition.getDatabaseName(), catalogName)) {
                    List<TableMeta> tableDocs = tableMetaRepository.selectTables(connection, condition);
                    DatabaseMeta meta = DatabaseMeta.builder()
                            .productName(metaData.getDatabaseProductName())
                            .productVersion(metaData.getDatabaseProductVersion())
                            .databaseName(catalogName)
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

package com.databasir.core.doc.factory.jdbc;

import com.databasir.core.doc.factory.DatabaseDocConfig;
import com.databasir.core.doc.factory.*;
import com.databasir.core.doc.model.DatabaseDoc;
import com.databasir.core.doc.model.TableDoc;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JdbcDatabaseDocFactory implements DatabaseDocFactory {

    public static JdbcDatabaseDocFactory of() {
        return new JdbcDatabaseDocFactory();
    }

    @Override
    public Optional<DatabaseDoc> create(DatabaseDocConfig configuration) {
        try {
            DatabaseMetaData metaData = configuration.getConnection().getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                String catalogName = catalogs.getString("TABLE_CAT");
                if (Objects.equals(configuration.getDatabaseName(), catalogName)) {
                    TableDocFactory tableDocFactory = configuration.getTableDocFactory();
                    List<TableDoc> tableDocs = tableDocFactory.create(metaData, configuration);
                    DatabaseDoc databaseDoc = DatabaseDoc.builder()
                            .productName(metaData.getDatabaseProductName())
                            .productVersion(metaData.getDatabaseProductVersion())
                            .databaseName(catalogName)
                            .tables(tableDocs)
                            .build();
                    return Optional.of(databaseDoc);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}

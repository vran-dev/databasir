package com.databasir.core.meta.provider;

import com.databasir.core.meta.provider.jdbc.*;
import com.databasir.core.meta.provider.maria.MariaTriggerMetaProvider;
import com.databasir.core.meta.provider.mysql.MysqlTableTriggerMetaProvider;
import com.databasir.core.meta.provider.postgresql.PostgresqlTriggerMetaProvider;
import com.databasir.core.meta.provider.sqlserver.SqlServerColumnMetaProvider;
import com.databasir.core.meta.provider.sqlserver.SqlServerTableMetaProvider;
import com.databasir.core.meta.provider.sqlserver.SqlServerTriggerMetaProvider;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Slf4j
public class MetaProviders {

    public static DatabaseMetaProvider jdbc() {
        var columnMetaProvider = new JdbcColumnMetaProvider();
        var foreignKeyMetaProvider = new JdbcForeignKeyMetaProvider();
        var indexMetaProvider = new JdbcIndexMetaProvider();
        var triggerMetaProvider = new JdbcTriggerMetaProvider();
        var tableMetaProvider = new JdbcTableMetaProvider(
                columnMetaProvider,
                indexMetaProvider,
                triggerMetaProvider,
                foreignKeyMetaProvider
        );
        return new JdbcDatabaseMetaProvider(tableMetaProvider);
    }

    public static DatabaseMetaProvider of(Connection connection) {
        String url;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            url = metaData.getURL();
        } catch (SQLException e) {
            log.warn("failed to get connect url, {}, fallback to jdbc provider", e.getMessage());
            return jdbc();
        }
        if (url.contains(":sqlserver:")) {
            return sqlServer();
        }
        if (url.contains(":mysql:")) {
            return mysql();
        }
        if (url.contains(":postgresql:") || url.contains(":pgsql:")) {
            return postgresql();
        }
        if (url.contains(":mariadb:")) {
            return mariaDB();
        }
        return jdbc();
    }

    private static DatabaseMetaProvider mysql() {
        var columnMetaProvider = new JdbcColumnMetaProvider();
        var foreignKeyMetaProvider = new JdbcForeignKeyMetaProvider();
        var indexMetaProvider = new JdbcIndexMetaProvider();
        var triggerMetaProvider = new MysqlTableTriggerMetaProvider();
        var tableMetaProvider = new JdbcTableMetaProvider(
                columnMetaProvider,
                indexMetaProvider,
                triggerMetaProvider,
                foreignKeyMetaProvider
        );
        return new JdbcDatabaseMetaProvider(tableMetaProvider);
    }

    private static DatabaseMetaProvider sqlServer() {
        var columnMetaProvider = new SqlServerColumnMetaProvider();
        var foreignKeyMetaProvider = new JdbcForeignKeyMetaProvider();
        var indexMetaProvider = new JdbcIndexMetaProvider();
        var triggerMetaProvider = new SqlServerTriggerMetaProvider();
        var tableMetaProvider = new SqlServerTableMetaProvider(
                columnMetaProvider,
                indexMetaProvider,
                foreignKeyMetaProvider,
                triggerMetaProvider
        );
        return new JdbcDatabaseMetaProvider(tableMetaProvider);
    }

    private static DatabaseMetaProvider postgresql() {
        var columnMetaProvider = new JdbcColumnMetaProvider();
        var foreignKeyMetaProvider = new JdbcForeignKeyMetaProvider();
        var indexMetaProvider = new JdbcIndexMetaProvider();
        var triggerMetaProvider = new PostgresqlTriggerMetaProvider();
        var tableMetaProvider = new JdbcTableMetaProvider(
                columnMetaProvider,
                indexMetaProvider,
                triggerMetaProvider,
                foreignKeyMetaProvider
        );
        return new JdbcDatabaseMetaProvider(tableMetaProvider);
    }

    private static DatabaseMetaProvider mariaDB() {
        var columnMetaProvider = new JdbcColumnMetaProvider();
        var foreignKeyMetaProvider = new JdbcForeignKeyMetaProvider();
        var indexMetaProvider = new JdbcIndexMetaProvider();
        var triggerMetaProvider = new MariaTriggerMetaProvider();
        var tableMetaProvider = new JdbcTableMetaProvider(
                columnMetaProvider,
                indexMetaProvider,
                triggerMetaProvider,
                foreignKeyMetaProvider
        );
        return new JdbcDatabaseMetaProvider(tableMetaProvider);
    }
}

package com.databasir.core.infrastructure.connection;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
public class PostgresqlDatabaseConnectionFactory implements DatabaseConnectionFactory {

    @Override
    public boolean support(String databaseType) {
        return DatabaseTypes.POSTGRESQL.equalsIgnoreCase(databaseType);
    }

    @Override
    public Connection getConnection(Context context) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        Properties info = new Properties();
        info.put("user", context.getUsername());
        info.put("password", context.getPassword());
        info.putAll(context.getProperties());
        String jdbcUrl = "jdbc:postgresql://" + context.getUrl() + "/" + context.getSchema();
        return DriverManager.getConnection(jdbcUrl, info);
    }
}

package com.databasir.core.infrastructure.connection;


import com.databasir.core.domain.DomainErrors;
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
    public Connection getConnection(String username, String password, String url, String schema, Properties properties) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        Properties info = new Properties();
        info.put("user", username);
        info.put("password", password);
        info.putAll(properties);
        String jdbcUrl = "jdbc:postgresql://" + url + "/" + schema;
        try {
            return DriverManager.getConnection(jdbcUrl, info);
        } catch (SQLException e) {
            throw DomainErrors.CONNECT_DATABASE_FAILED.exception(e);
        }
    }
}

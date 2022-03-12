package com.databasir.core.infrastructure.connection;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Component
@Order(1)
public class MysqlDatabaseConnectionFactory implements DatabaseConnectionFactory {

    @Override
    public boolean support(String databaseType) {
        return DatabaseTypes.MYSQL.equalsIgnoreCase(databaseType);
    }

    @Override
    public Connection getConnection(Context context) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        Properties info = new Properties();
        info.put("user", context.getUsername());
        info.put("password", context.getPassword());
        info.putAll(context.getProperties());
        String jdbcUrl = "jdbc:mysql://" + context.getUrl() + "/" + context.getSchema();
        return DriverManager.getConnection(jdbcUrl, info);
    }

}

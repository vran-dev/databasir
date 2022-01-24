package com.databasir.core.infrastructure.connection;

import java.sql.Connection;
import java.util.Properties;

public interface DatabaseConnectionFactory {

    boolean support(String databaseType);

    Connection getConnection(String username,
                             String password,
                             String url,
                             String schema,
                             Properties properties);
}

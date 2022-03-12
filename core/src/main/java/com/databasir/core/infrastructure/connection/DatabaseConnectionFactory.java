package com.databasir.core.infrastructure.connection;

import lombok.Builder;
import lombok.Data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface DatabaseConnectionFactory {

    boolean support(String databaseType);

    Connection getConnection(Context context) throws SQLException;

    @Builder
    @Data
    class Context {

        private String databaseType;

        private String username;

        private String password;

        private String url;

        private String databaseName;

        private String schemaName;

        private Properties properties;
    }
}

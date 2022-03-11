package com.databasir.core.infrastructure.connection;

import java.util.Objects;

public interface DatabaseTypes {

    String MYSQL = "mysql";

    String POSTGRESQL = "postgresql";

    static boolean has(String name) {
        if (name == null) {
            return false;
        }
        return Objects.equals(MYSQL, name.toLowerCase())
                || Objects.equals(POSTGRESQL, name.toLowerCase());
    }

}

package com.databasir.api.validator;

import org.springframework.stereotype.Component;

import static com.databasir.core.domain.DomainErrors.*;

@Component
public class DatabaseTypeValidator {

    public void isValidUrlPattern(String urlPattern) {
        if (urlPattern == null) {
            throw INVALID_DATABASE_TYPE_URL_PATTERN.exception();
        }
        if (!urlPattern.contains("{{jdbc.protocol}}")) {
            throw MISS_JDBC_PROTOCOL.exception();
        }
        if (!urlPattern.contains("{{db.url}}")) {
            throw MISS_DB_URL.exception();
        }
        if (!urlPattern.contains("{{db.schema}}") && !urlPattern.contains("{{db.name}}")) {
            throw MISS_DB_SCHEMA.exception();
        }
    }
}

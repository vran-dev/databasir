package com.databasir.api.validator;

import com.databasir.core.domain.DomainErrors;
import org.springframework.stereotype.Component;

@Component
public class DatabaseTypeValidator {

    public void isValidUrlPattern(String urlPattern) {
        if (urlPattern == null) {
            throw DomainErrors.INVALID_DATABASE_TYPE_URL_PATTERN.exception("url pattern 不能为空");
        }
        if (!urlPattern.contains("{{jdbc.protocol}}")) {
            throw DomainErrors.INVALID_DATABASE_TYPE_URL_PATTERN.exception("必须包含变量{{jdbc.protocol}}");
        }
        if (!urlPattern.contains("{{db.url}}")) {
            throw DomainErrors.INVALID_DATABASE_TYPE_URL_PATTERN.exception("必须包含变量{{db.url}}不能为空");
        }
        if (!urlPattern.contains("{{db.name}}")) {
            throw DomainErrors.INVALID_DATABASE_TYPE_URL_PATTERN.exception("必须包含变量{{db.name}}不能为空");
        }
    }
}

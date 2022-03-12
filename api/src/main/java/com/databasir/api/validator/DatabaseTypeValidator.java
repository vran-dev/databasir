package com.databasir.api.validator;

import org.springframework.stereotype.Component;

import static com.databasir.core.domain.DomainErrors.INVALID_DATABASE_TYPE_URL_PATTERN;

@Component
public class DatabaseTypeValidator {

    public void isValidUrlPattern(String urlPattern) {
        if (urlPattern == null) {
            throw INVALID_DATABASE_TYPE_URL_PATTERN.exception("url pattern 不能为空");
        }
        if (!urlPattern.contains("{{jdbc.protocol}}")) {
            throw INVALID_DATABASE_TYPE_URL_PATTERN.exception("必须包含变量{{jdbc.protocol}}");
        }
        if (!urlPattern.contains("{{db.url}}")) {
            throw INVALID_DATABASE_TYPE_URL_PATTERN.exception("必须包含变量{{db.url}}不能为空");
        }
        if (!urlPattern.contains("{{db.schema}}") && !urlPattern.contains("{{db.name}}")) {
            throw INVALID_DATABASE_TYPE_URL_PATTERN.exception("{{db.schema}} 和 {{db.name}} 至少设置一个");
        }
    }
}

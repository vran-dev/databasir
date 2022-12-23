package com.databasir.core.domain.app.common;

import com.databasir.dao.tables.pojos.OauthAppProperty;

import java.util.List;

public interface CommonProperties {

    String AUTH_HOST = "auth_host";

    String RESOURCE_HOST = "resource_host";

    CommonProperties INSTANCE = new CommonProperties() {
    };

    default String get(List<OauthAppProperty> properties, String key) {
        return properties.stream()
                .filter(p -> p.getName().equals(key))
                .map(OauthAppProperty::getValue)
                .findFirst()
                .orElseThrow();
    }

    default String getAuthHost(List<OauthAppProperty> properties) {
        return get(properties, AUTH_HOST);
    }

    default String getResourceHost(List<OauthAppProperty> properties) {
        return get(properties, RESOURCE_HOST);
    }
}

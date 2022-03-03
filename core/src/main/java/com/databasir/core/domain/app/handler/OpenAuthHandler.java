package com.databasir.core.domain.app.handler;

import com.databasir.dao.enums.OAuthAppType;
import com.databasir.dao.tables.pojos.OauthAppPojo;

import java.util.Map;

public interface OpenAuthHandler {

    boolean support(OAuthAppType oauthAppType);

    String authorizationUrl(OauthAppPojo app, Map<String, String[]> requestParams);

    OAuthProcessResult process(OauthAppPojo app, Map<String, String[]> requestParams);
}

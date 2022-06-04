package com.databasir.core.domain.app.handler;

import com.databasir.dao.enums.OAuthAppType;
import com.databasir.dao.tables.pojos.OauthApp;

import java.util.Map;

public interface OpenAuthHandler {

    boolean support(OAuthAppType oauthAppType);

    String authorizationUrl(OauthApp app, Map<String, String[]> requestParams);

    OAuthProcessResult process(OauthApp app, Map<String, String[]> requestParams);
}

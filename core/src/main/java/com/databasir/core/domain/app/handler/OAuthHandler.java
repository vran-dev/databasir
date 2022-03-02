package com.databasir.core.domain.app.handler;

public interface OAuthHandler {

    boolean support(String oauthAppType);

    String authorization(String registrationId);

    OAuthProcessResult process(OAuthProcessContext context);
}

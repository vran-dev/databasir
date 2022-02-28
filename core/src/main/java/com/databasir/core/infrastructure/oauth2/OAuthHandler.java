package com.databasir.core.infrastructure.oauth2;

public interface OAuthHandler {

    boolean support(String oauthAppType);

    String authorization(String registrationId);

    OAuthProcessResult process(OAuthProcessContext context);
}

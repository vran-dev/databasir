package com.databasir.core.domain.app.handler;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.app.exception.DatabasirAuthenticationException;
import com.databasir.core.infrastructure.remote.gitlab.GitlabRemoteService;
import com.databasir.dao.enums.OAuthAppType;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GitlabOpenAuthHandler implements OpenAuthHandler {

    private final GitlabRemoteService gitlabRemoteService;

    @Override
    public boolean support(String oauthAppType) {
        return OAuthAppType.GITLAB.isSame(oauthAppType);
    }

    @Override
    public String authorizationUrl(OauthAppPojo app, Map<String, String[]> params) {
        if (!params.containsKey("redirect_uri")) {
            throw DomainErrors.MISS_REQUIRED_PARAMETERS.exception("缺少参数 redirect_uri", null);
        }
        String authUrl = app.getAuthUrl();
        String clientId = app.getClientId();
        String authorizeUrl = authUrl + "/oauth/authorize";
        String redirectUri = params.get("redirect_uri")[0];
        String url = UriComponentsBuilder.fromUriString(authorizeUrl)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("state", redirectUri)
                .encode()
                .build()
                .toUriString();
        return url;
    }

    @Override
    public OAuthProcessResult process(OauthAppPojo app, Map<String, String[]> requestParams) {
        if (!requestParams.containsKey("redirect_uri")) {
            throw DomainErrors.MISS_REQUIRED_PARAMETERS.exception("缺少参数 redirect_uri", null);
        }
        String url = app.getAuthUrl();
        String code = requestParams.get("code")[0];
        String state = requestParams.get("state")[0];
        String redirectUri = requestParams.get("redirect_uri")[0];
        JsonNode accessTokenData =
                gitlabRemoteService.getAccessToken(url, code, app.getClientId(), app.getClientSecret(), redirectUri);
        if (accessTokenData == null) {
            throw new DatabasirAuthenticationException(DomainErrors.NETWORK_ERROR.exception());
        }
        String accessToken = accessTokenData.get("access_token").asText();
        JsonNode userData = gitlabRemoteService.getUser(app.getResourceUrl(), accessToken);
        if (userData == null) {
            throw new DatabasirAuthenticationException(DomainErrors.NETWORK_ERROR.exception());
        }
        String email = userData.get("email").asText();
        String avatar = userData.get("avatar_url").asText();
        String name = userData.get("name").asText();
        OAuthProcessResult result = new OAuthProcessResult();
        result.setAvatar(avatar);
        result.setEmail(email);
        result.setNickname(name);
        result.setUsername(email);
        return result;
    }

}

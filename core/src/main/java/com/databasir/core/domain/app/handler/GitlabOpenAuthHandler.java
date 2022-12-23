package com.databasir.core.domain.app.handler;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.app.common.CommonProperties;
import com.databasir.core.domain.app.common.GitlabProperties;
import com.databasir.core.domain.app.exception.DatabasirAuthenticationException;
import com.databasir.core.infrastructure.remote.gitlab.GitlabRemoteService;
import com.databasir.dao.enums.OAuthAppType;
import com.databasir.dao.tables.pojos.OauthApp;
import com.databasir.dao.tables.pojos.OauthAppProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GitlabOpenAuthHandler implements OpenAuthHandler {

    private final GitlabRemoteService gitlabRemoteService;

    @Override
    public boolean support(OAuthAppType oauthAppType) {
        return OAuthAppType.GITLAB == oauthAppType;
    }

    @Override
    public String authorizationUrl(OauthApp app,
                                   List<OauthAppProperty> properties,
                                   Map<String, String[]> params) {
        if (!params.containsKey("redirect_uri")) {
            throw DomainErrors.MISS_REQUIRED_PARAMETERS.exception("缺少参数 redirect_uri", null);
        }

        String authUrl = CommonProperties.INSTANCE.getAuthHost(properties);
        String clientId = CommonProperties.INSTANCE.get(properties, GitlabProperties.CLIENT_ID);
        String authorizeUrl = authUrl + "/oauth/authorize";
        String redirectUri = params.get("redirect_uri")[0];
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(authorizeUrl)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "read_user");
        return builder
                .encode()
                .build()
                .toUriString();
    }

    @Override
    public OAuthProcessResult process(OauthApp app,
                                      List<OauthAppProperty> properties,
                                      Map<String, String[]> requestParams) {
        if (!requestParams.containsKey("redirect_uri")) {
            throw DomainErrors.MISS_REQUIRED_PARAMETERS.exception("缺少参数 redirect_uri", null);
        }

        String url = CommonProperties.INSTANCE.getAuthHost(properties);
        String code = requestParams.get("code")[0];
        String state = requestParams.get("state")[0];
        String redirectUri = requestParams.get("redirect_uri")[0];
        String clientId = CommonProperties.INSTANCE.get(properties, GitlabProperties.CLIENT_ID);
        String secret = CommonProperties.INSTANCE.get(properties, GitlabProperties.CLIENT_SECRET);
        JsonNode accessTokenData =
                gitlabRemoteService.getAccessToken(url, code, clientId, secret, redirectUri);
        if (accessTokenData == null) {
            throw new DatabasirAuthenticationException(DomainErrors.NETWORK_ERROR.exception());
        }
        String accessToken = accessTokenData.get("access_token").asText();

        String resourceUrl = CommonProperties.INSTANCE.getResourceHost(properties);
        JsonNode userData = gitlabRemoteService.getUser(resourceUrl, accessToken);
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

package com.databasir.core.domain.app.handler;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.app.common.CommonProperties;
import com.databasir.core.domain.app.common.GithubProperties;
import com.databasir.core.domain.app.exception.DatabasirAuthenticationException;
import com.databasir.core.infrastructure.remote.github.GithubRemoteService;
import com.databasir.dao.enums.OAuthAppType;
import com.databasir.dao.tables.pojos.OauthApp;
import com.databasir.dao.tables.pojos.OauthAppProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.jooq.tools.StringUtils;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GithubOpenAuthHandler implements OpenAuthHandler {

    private final GithubRemoteService githubRemoteService;

    @Override
    public boolean support(OAuthAppType oauthAppType) {
        return OAuthAppType.GITHUB == oauthAppType;
    }

    @Override
    public String authorizationUrl(OauthApp app,
                                   List<OauthAppProperty> properties,
                                   Map<String, String[]> requestParams) {
        String authUrl = CommonProperties.INSTANCE.getAuthHost(properties);
        String clientId = CommonProperties.INSTANCE.get(properties, GithubProperties.CLIENT_ID);
        String authorizeUrl = authUrl + "/login/oauth/authorize";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(authorizeUrl)
                .queryParam("client_id", clientId)
                .queryParam("scope", "read:user user:email");
        String url = builder
                .encode()
                .build()
                .toUriString();
        return url;
    }

    @Override
    public OAuthProcessResult process(OauthApp app,
                                      List<OauthAppProperty> properties,
                                      Map<String, String[]> requestParams) {
        String authUrl = CommonProperties.INSTANCE.getAuthHost(properties);
        String clientId = CommonProperties.INSTANCE.get(properties, GithubProperties.CLIENT_ID);
        String clientSecret = CommonProperties.INSTANCE.get(properties, GithubProperties.CLIENT_SECRET);

        String code = requestParams.get("code")[0];
        JsonNode tokenNode = githubRemoteService.getToken(authUrl, clientId, clientSecret, code)
                .get("access_token");
        if (tokenNode == null) {
            throw new DatabasirAuthenticationException(DomainErrors.NETWORK_ERROR.exception());
        }
        String accessToken = tokenNode.asText();
        if (StringUtils.isBlank(accessToken)) {
            throw new CredentialsExpiredException("授权失效，请重新登陆");
        }

        String resourceUrl = CommonProperties.INSTANCE.get(properties, GithubProperties.RESOURCE_HOST);
        String email = null;
        for (JsonNode node : githubRemoteService.getEmail(resourceUrl, accessToken)) {
            if (node.get("primary").asBoolean()) {
                email = node.get("email").asText();
            }
        }
        if (StringUtils.isBlank(email)) {
            throw new CredentialsExpiredException("授权失效，请重新登陆");
        }
        JsonNode profile = githubRemoteService.getProfile(resourceUrl, accessToken);
        String nickname = profile.get("name").asText();
        String avatar = profile.get("avatar_url").asText();
        OAuthProcessResult result = new OAuthProcessResult();
        result.setEmail(email);
        result.setNickname(nickname);
        result.setUsername(email);
        result.setAvatar(avatar);
        return result;
    }
}

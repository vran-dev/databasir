package com.databasir.core.infrastructure.oauth2;


import com.databasir.core.infrastructure.remote.github.GithubRemoteService;
import com.databasir.dao.enums.OAuthAppType;
import com.databasir.dao.impl.OAuthAppDao;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GithubOauthHandler implements OAuthHandler {

    private final GithubRemoteService githubRemoteService;

    private final OAuthAppDao oAuthAppDao;

    @Override
    public boolean support(String oauthAppType) {
        return OAuthAppType.GITHUB.isSame(oauthAppType);
    }

    @Override
    public String authorization(String registrationId) {
        OauthAppPojo app = oAuthAppDao.selectByRegistrationId(registrationId);
        String authUrl = app.getAuthUrl();
        String clientId = app.getClientId();
        String authorizeUrl = authUrl + "/login/oauth/authorize";
        String url = UriComponentsBuilder.fromUriString(authorizeUrl)
                .queryParam("client_id", clientId)
                .queryParam("scope", "read:user user:email")
                .encode()
                .build()
                .toUriString();
        return url;
    }

    @Override
    public OAuthProcessResult process(OAuthProcessContext context) {
        OauthAppPojo authApp = oAuthAppDao.selectByRegistrationId(context.getRegistrationId());
        String clientId = authApp.getClientId();
        String clientSecret = authApp.getClientSecret();
        String baseUrl = authApp.getResourceUrl();

        Map<String, String> params = context.getCallbackParameters();
        String code = params.get("code");
        String accessToken = githubRemoteService.getToken(baseUrl, clientId, clientSecret, code)
                .get("access_token")
                .asText();
        String email = null;
        for (JsonNode node : githubRemoteService.getEmail(baseUrl, accessToken)) {
            if (node.get("primary").asBoolean()) {
                email = node.get("email").asText();
            }
        }
        JsonNode profile = githubRemoteService.getProfile(baseUrl, accessToken);
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

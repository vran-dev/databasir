package com.databasir.core.domain.app.handler;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.app.common.WeWorkProperties;
import com.databasir.core.infrastructure.remote.wework.WeWorkRemoteService;
import com.databasir.dao.enums.OAuthAppType;
import com.databasir.dao.tables.pojos.OauthApp;
import com.databasir.dao.tables.pojos.OauthAppProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static com.databasir.core.domain.app.common.CommonProperties.INSTANCE;

@Component
@Slf4j
@RequiredArgsConstructor
public class WeWorkOpenAuthHandler implements OpenAuthHandler {

    private final WeWorkRemoteService weWorkRemoteService;

    @Override
    public boolean support(OAuthAppType oauthAppType) {
        return oauthAppType == OAuthAppType.WE_WORK;
    }

    @Override
    public String authorizationUrl(OauthApp app,
                                   List<OauthAppProperty> properties,
                                   Map<String, String[]> requestParams) {
        String authUrl = INSTANCE.getAuthHost(properties);
        String authorizeUrl = authUrl + "/wwopen/sso/qrConnect";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(authorizeUrl)
            .queryParam("appid", INSTANCE.get(properties, WeWorkProperties.APP_ID))
            .queryParam("agentid", INSTANCE.get(properties, WeWorkProperties.AGENT_ID))
            .queryParam("redirect_uri", INSTANCE.get(properties, WeWorkProperties.REDIRECT_URL));
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
        if (!requestParams.containsKey("redirect_uri")) {
            throw DomainErrors.MISS_REDIRECT_URI.exception();
        }
        String code = requestParams.get("code")[0];

        String resourceUrl = INSTANCE.getResourceHost(properties);
        String clientId = INSTANCE.get(properties, WeWorkProperties.APP_ID);
        String secret = INSTANCE.get(properties, WeWorkProperties.SECRET);
        String tokenUrl = resourceUrl + "/cgi-bin/gettoken";
        String token = weWorkRemoteService.getToken(tokenUrl, clientId, secret);

        String userIdUrl = resourceUrl + "/cgi-bin/auth/getuserinfo";
        String userId = weWorkRemoteService.getUserId(userIdUrl, token, code);

        String userInfoUrl = resourceUrl + "/cgi-bin/user/get";
        JsonNode userInfo = weWorkRemoteService.getUserInfo(userInfoUrl, token, userId);

        OAuthProcessResult result = new OAuthProcessResult();
        result.setAvatar(userInfo.get("avatar").asText());
        result.setEmail(userInfo.get("biz_mail").asText());
        result.setNickname(userInfo.get("name").asText());
        result.setUsername(userInfo.get("biz_mail").asText());
        return result;
    }
}

package com.databasir.core.domain.app.handler;

import com.databasir.core.domain.app.exception.DatabasirAuthenticationException;
import com.databasir.dao.impl.OauthAppDao;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.databasir.core.domain.DomainErrors.REGISTRATION_ID_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class OpenAuthHandlers {

    private final List<OpenAuthHandler> handlers;

    private final OauthAppDao oauthAppDao;

    public String authorizeUrl(String registrationId, Map<String, String[]> parameters) {
        OauthAppPojo app = oauthAppDao.selectByRegistrationId(registrationId);
        return handlers.stream()
                .filter(handler -> handler.support(app.getAppType()))
                .findFirst()
                .map(handler -> handler.authorizationUrl(app, parameters))
                .orElseThrow();
    }

    public OAuthProcessResult process(String registrationId, Map<String, String[]> parameters) {
        OauthAppPojo app = oauthAppDao.selectOptionByRegistrationId(registrationId)
                .orElseThrow(() -> {
                    var bizErr = REGISTRATION_ID_NOT_FOUND.exception("应用 ID [" + registrationId + "] 不存在");
                    return new DatabasirAuthenticationException(bizErr);
                });
        return handlers.stream()
                .filter(handler -> handler.support(app.getAppType()))
                .findFirst()
                .map(handler -> handler.process(app, parameters))
                .orElseThrow();
    }
}

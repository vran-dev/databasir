package com.databasir.core.domain.app.handler;

import com.databasir.core.domain.app.exception.DatabasirAuthenticationException;
import com.databasir.dao.impl.OauthAppDao;
import com.databasir.dao.impl.OauthAppPropertyDao;
import com.databasir.dao.tables.pojos.OauthApp;
import com.databasir.dao.tables.pojos.OauthAppProperty;
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

    private final OauthAppPropertyDao oauthAppPropertyDao;

    public String authorizeUrl(String registrationId, Map<String, String[]> parameters) {
        OauthApp app = oauthAppDao.selectByRegistrationId(registrationId);
        List<OauthAppProperty> properties = oauthAppPropertyDao.selectByOauthAppId(app.getId());
        return handlers.stream()
                .filter(handler -> handler.support(app.getAppType()))
                .findFirst()
                .map(handler -> handler.authorizationUrl(app, properties, parameters))
                .orElseThrow();
    }

    public OAuthProcessResult process(String registrationId, Map<String, String[]> parameters) {
        OauthApp app = oauthAppDao.selectOptionByRegistrationId(registrationId)
                .orElseThrow(() -> {
                    var bizErr = REGISTRATION_ID_NOT_FOUND.exception("应用 ID [" + registrationId + "] 不存在");
                    return new DatabasirAuthenticationException(bizErr);
                });
        List<OauthAppProperty> properties = oauthAppPropertyDao.selectByOauthAppId(app.getId());
        return handlers.stream()
                .filter(handler -> handler.support(app.getAppType()))
                .findFirst()
                .map(handler -> handler.process(app, properties, parameters))
                .orElseThrow();
    }
}

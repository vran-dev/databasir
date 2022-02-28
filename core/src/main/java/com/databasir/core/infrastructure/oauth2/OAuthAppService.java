package com.databasir.core.infrastructure.oauth2;

import com.databasir.core.domain.login.data.LoginKeyResponse;
import com.databasir.core.domain.login.service.LoginService;
import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.core.domain.user.service.UserService;
import com.databasir.core.infrastructure.oauth2.data.OAuthAppResponse;
import com.databasir.dao.impl.OAuthAppDao;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthAppService {

    private final List<OAuthHandler> oAuthHandlers;

    private final OAuthAppDao oAuthAppDao;

    private final UserService userService;

    private final LoginService loginService;

    public LoginKeyResponse oauthCallback(String registrationId, Map<String, String> params) {

        // match handler
        OauthAppPojo app = oAuthAppDao.selectByRegistrationId(registrationId);
        OAuthHandler oAuthHandler = oAuthHandlers.stream()
                .filter(handler -> handler.support(app.getAppType()))
                .findFirst()
                .orElseThrow();

        // process by handler
        OAuthProcessContext context = OAuthProcessContext.builder()
                .callbackParameters(params)
                .registrationId(registrationId)
                .build();
        OAuthProcessResult result = oAuthHandler.process(context);

        // create new user
        UserCreateRequest user = new UserCreateRequest();
        user.setUsername(result.getUsername());
        user.setNickname(result.getNickname());
        user.setEmail(result.getEmail());
        user.setAvatar(result.getAvatar());
        user.setPassword(UUID.randomUUID().toString().substring(0, 6));
        Integer userId = userService.create(user);

        return loginService.generate(userId);
    }

    public List<OAuthAppResponse> listAll() {
        return null;
    }

}

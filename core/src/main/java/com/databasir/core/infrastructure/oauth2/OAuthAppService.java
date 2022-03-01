package com.databasir.core.infrastructure.oauth2;

import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.core.domain.user.data.UserDetailResponse;
import com.databasir.core.domain.user.service.UserService;
import com.databasir.core.infrastructure.oauth2.converter.OAuthAppResponseConverter;
import com.databasir.core.infrastructure.oauth2.data.OAuthAppResponse;
import com.databasir.dao.impl.OAuthAppDao;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OAuthAppService {

    private final List<OAuthHandler> oAuthHandlers;

    private final OAuthAppDao oAuthAppDao;

    private final UserService userService;

    private final OAuthAppResponseConverter oAuthAppResponseConverter;

    public UserDetailResponse oauthCallback(String registrationId, Map<String, String[]> params) {

        // match handler
        OauthAppPojo app = oAuthAppDao.selectByRegistrationId(registrationId);
        OAuthHandler oAuthHandler = oAuthHandlers.stream()
                .filter(handler -> handler.support(app.getAppType()))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("暂不支持该类型登陆"));

        // process by handler
        OAuthProcessContext context = OAuthProcessContext.builder()
                .callbackParameters(params)
                .registrationId(registrationId)
                .build();
        OAuthProcessResult result = oAuthHandler.process(context);

        // get  or create new user
        return userService.get(result.getEmail())
                .orElseGet(() -> {
                    UserCreateRequest user = new UserCreateRequest();
                    user.setUsername(result.getUsername());
                    user.setNickname(result.getNickname());
                    user.setEmail(result.getEmail());
                    user.setAvatar(result.getAvatar());
                    user.setPassword(UUID.randomUUID().toString().substring(0, 6));
                    Integer id = userService.create(user);
                    return userService.get(id);
                });
    }

    public List<OAuthAppResponse> listAll() {
        List<OauthAppPojo> apps = oAuthAppDao.selectAll();
        return apps.stream()
                .map(oAuthAppResponseConverter::to)
                .collect(Collectors.toList());
    }

}

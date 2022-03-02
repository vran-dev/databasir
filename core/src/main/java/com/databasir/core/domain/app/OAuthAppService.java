package com.databasir.core.domain.app;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.app.converter.OAuthAppPojoConverter;
import com.databasir.core.domain.app.converter.OAuthAppResponseConverter;
import com.databasir.core.domain.app.data.*;
import com.databasir.core.domain.app.handler.OAuthHandler;
import com.databasir.core.domain.app.handler.OAuthProcessContext;
import com.databasir.core.domain.app.handler.OAuthProcessResult;
import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.core.domain.user.data.UserDetailResponse;
import com.databasir.core.domain.user.service.UserService;
import com.databasir.dao.impl.OAuthAppDao;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OAuthAppService {

    private final List<OAuthHandler> oAuthHandlers;

    private final OAuthAppDao oAuthAppDao;

    private final UserService userService;

    private final OAuthAppResponseConverter oAuthAppResponseConverter;

    private final OAuthAppPojoConverter oAuthAppPojoConverter;

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
                    user.setEnabled(true);
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

    public void deleteById(Integer id) {
        if (oAuthAppDao.existsById(id)) {
            oAuthAppDao.deleteById(id);
        }
    }

    public void updateById(OAuthAppUpdateRequest request) {
        OauthAppPojo pojo = oAuthAppPojoConverter.of(request);
        try {
            oAuthAppDao.updateById(pojo);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.REGISTRATION_ID_DUPLICATE.exception();
        }
    }

    public Integer create(OAuthAppCreateRequest request) {
        OauthAppPojo pojo = oAuthAppPojoConverter.of(request);
        try {
            return oAuthAppDao.insertAndReturnId(pojo);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.REGISTRATION_ID_DUPLICATE.exception();
        }
    }

    public Page<OAuthAppPageResponse> listPage(Pageable page, OAuthAppPageCondition condition) {
        return oAuthAppDao.selectByPage(page, condition.toCondition()).map(oAuthAppPojoConverter::toPageResponse);
    }

    public Optional<OAuthAppDetailResponse> getOne(Integer id) {
        return oAuthAppDao.selectOptionalById(id).map(oAuthAppPojoConverter::toDetailResponse);
    }
}

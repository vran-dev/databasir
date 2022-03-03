package com.databasir.core.domain.app;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.app.converter.OAuthAppPojoConverter;
import com.databasir.core.domain.app.converter.OAuthAppResponseConverter;
import com.databasir.core.domain.app.data.*;
import com.databasir.core.domain.app.handler.OAuthProcessResult;
import com.databasir.core.domain.app.handler.OpenAuthHandlers;
import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.core.domain.user.data.UserDetailResponse;
import com.databasir.core.domain.user.service.UserService;
import com.databasir.dao.impl.OauthAppDao;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAuthAppService {

    private final OpenAuthHandlers openAuthHandlers;

    private final OauthAppDao oauthAppDao;

    private final UserService userService;

    private final OAuthAppResponseConverter oauthAppResponseConverter;

    private final OAuthAppPojoConverter oauthAppPojoConverter;

    public UserDetailResponse oauthCallback(String registrationId, Map<String, String[]> params) {
        // process by handler
        OAuthProcessResult result = openAuthHandlers.process(registrationId, params);

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
        List<OauthAppPojo> apps = oauthAppDao.selectAll();
        return apps.stream()
                .map(oauthAppResponseConverter::to)
                .collect(Collectors.toList());
    }

    public void deleteById(Integer id) {
        if (oauthAppDao.existsById(id)) {
            oauthAppDao.deleteById(id);
        }
    }

    public void updateById(OAuthAppUpdateRequest request) {
        OauthAppPojo pojo = oauthAppPojoConverter.of(request);
        try {
            oauthAppDao.updateById(pojo);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.REGISTRATION_ID_DUPLICATE.exception();
        }
    }

    public Integer create(OAuthAppCreateRequest request) {
        OauthAppPojo pojo = oauthAppPojoConverter.of(request);
        try {
            return oauthAppDao.insertAndReturnId(pojo);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.REGISTRATION_ID_DUPLICATE.exception();
        }
    }

    public Page<OAuthAppPageResponse> listPage(Pageable page, OAuthAppPageCondition condition) {
        return oauthAppDao.selectByPage(page, condition.toCondition()).map(oauthAppPojoConverter::toPageResponse);
    }

    public Optional<OAuthAppDetailResponse> getOne(Integer id) {
        return oauthAppDao.selectOptionalById(id).map(oauthAppPojoConverter::toDetailResponse);
    }
}

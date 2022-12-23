package com.databasir.core.domain.app;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.app.converter.OAuthAppResponseConverter;
import com.databasir.core.domain.app.converter.OauthAppConverter;
import com.databasir.core.domain.app.data.*;
import com.databasir.core.domain.app.handler.OAuthProcessResult;
import com.databasir.core.domain.app.handler.OpenAuthHandlers;
import com.databasir.core.domain.app.validator.OauthPropertiesValidator;
import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.core.domain.user.data.UserDetailResponse;
import com.databasir.core.domain.user.service.UserService;
import com.databasir.dao.impl.OauthAppDao;
import com.databasir.dao.impl.OauthAppPropertyDao;
import com.databasir.dao.tables.pojos.OauthApp;
import com.databasir.dao.tables.pojos.OauthAppProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpenAuthAppService {

    private final OpenAuthHandlers openAuthHandlers;

    private final OauthAppDao oauthAppDao;

    private final OauthAppPropertyDao oauthAppPropertyDao;

    private final UserService userService;

    private final OAuthAppResponseConverter oauthAppResponseConverter;

    private final OauthAppConverter oauthAppConverter;

    private final ObjectMapper objectMapper;

    private final OauthPropertiesValidator oauthPropertiesValidator;

    private List<OAuthAppPlatformResponse> platforms = new ArrayList<>();

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
                    Integer id = userService.create(user, registrationId);
                    return userService.get(id);
                });
    }

    public List<OAuthAppResponse> listAll() {
        List<OauthApp> apps = oauthAppDao.selectAll();
        return apps.stream()
                .map(oauthAppResponseConverter::to)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        if (oauthAppDao.existsById(id)) {
            oauthAppDao.deleteById(id);
            oauthAppPropertyDao.deleteByOauthAppId(id);
        }
    }

    @Transactional
    public void updateById(OAuthAppUpdateRequest request) {
        oauthPropertiesValidator.validate(request, listPlatforms());
        OauthApp pojo = oauthAppConverter.of(request);
        try {
            oauthAppDao.updateById(pojo);
            oauthAppPropertyDao.deleteByOauthAppId(pojo.getId());
            List<OauthAppProperty> properties = oauthAppConverter.toProperty(pojo.getId(), request.getProperties());
            oauthAppPropertyDao.batchInsert(properties);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.REGISTRATION_ID_DUPLICATE.exception();
        }
    }

    @Transactional
    public Integer create(OAuthAppCreateRequest request) {
        oauthPropertiesValidator.validate(request, listPlatforms());
        OauthApp pojo = oauthAppConverter.of(request);
        try {
            Integer oauthAppId = oauthAppDao.insertAndReturnId(pojo);
            List<OauthAppProperty> properties = oauthAppConverter.toProperty(oauthAppId, request.getProperties());
            oauthAppPropertyDao.batchInsert(properties);
            return oauthAppId;
        } catch (DuplicateKeyException e) {
            throw DomainErrors.REGISTRATION_ID_DUPLICATE.exception();
        }
    }

    public Page<OAuthAppPageResponse> listPage(Pageable page, OAuthAppPageCondition condition) {
        return oauthAppDao.selectByPage(page, condition.toCondition()).map(oauthAppConverter::toPageResponse);
    }

    public Optional<OAuthAppDetailResponse> getOne(Integer id) {
        return oauthAppDao.selectOptionalById(id).map(oauthApp -> {
            List<OauthAppProperty> properties = oauthAppPropertyDao.selectByOauthAppId(id);
            return oauthAppConverter.toDetailResponse(oauthApp, properties);
        });
    }

    public List<OAuthAppPlatformResponse> listPlatforms() {
        if (platforms == null || platforms.isEmpty()) {
            String schemaPath = "classpath:/oauth/platform-properties-schema.json";
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(schemaPath);
            try (InputStream inputStream = resource.getInputStream()) {
                return objectMapper.readValue(inputStream, new TypeReference<List<OAuthAppPlatformResponse>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return platforms;
        }
    }
}

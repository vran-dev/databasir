package com.databasir.core.domain.app.validator;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.app.data.OAuthAppCreateRequest;
import com.databasir.core.domain.app.data.OAuthAppPlatformResponse;
import com.databasir.core.domain.app.data.OAuthAppPlatformResponse.OAuthAppPlatformProperty;
import com.databasir.core.domain.app.data.OAuthAppUpdateRequest;
import com.databasir.core.domain.app.data.OauthAppPropertyData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class OauthPropertiesValidator {

    public void validate(OAuthAppCreateRequest request, List<OAuthAppPlatformResponse> platforms) {
        Map<String, OauthAppPropertyData> propertyMapByName = request.getProperties()
                .stream()
                .collect(Collectors.toMap(i -> i.getName(), i -> i));
        platforms.stream()
                .filter(platform -> platform.getAuthAppType() == request.getAppType())
                .forEach(platform -> {
                    List<OAuthAppPlatformProperty> properties = platform.getProperties();
                    properties.forEach(property -> {
                        if (Objects.equals(true, property.getRequired())) {
                            if (!propertyMapByName.containsKey(property.getName())) {
                                throw DomainErrors.MISS_REQUIRED_PARAMETERS.exception(
                                        "参数 " + property.getName() + " 不能为空");
                            }
                        }
                    });
                });
    }

    public void validate(OAuthAppUpdateRequest request, List<OAuthAppPlatformResponse> platforms) {
        Map<String, OauthAppPropertyData> propertyMapByName = request.getProperties()
                .stream()
                .collect(Collectors.toMap(i -> i.getName(), i -> i));
        platforms.stream()
                .filter(platform -> platform.getAuthAppType() == request.getAppType())
                .forEach(platform -> {
                    List<OAuthAppPlatformProperty> properties = platform.getProperties();
                    properties.forEach(property -> {
                        if (Objects.equals(true, property.getRequired())) {
                            if (!propertyMapByName.containsKey(property.getName())) {
                                throw DomainErrors.MISS_REQUIRED_PARAMETERS.exception(
                                        "参数 " + property.getName() + " 不能为空");
                            }
                        }
                    });
                });
    }
}

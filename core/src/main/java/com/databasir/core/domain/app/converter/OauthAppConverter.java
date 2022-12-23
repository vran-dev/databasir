package com.databasir.core.domain.app.converter;

import com.databasir.core.domain.app.data.*;
import com.databasir.dao.tables.pojos.OauthApp;
import com.databasir.dao.tables.pojos.OauthAppProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OauthAppConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    OauthApp of(OAuthAppCreateRequest request);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    OauthApp of(OAuthAppUpdateRequest request);

    OAuthAppPageResponse toPageResponse(OauthApp pojo);

    OAuthAppDetailResponse toDetailResponse(OauthApp pojo, List<OauthAppProperty> properties);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    OauthAppProperty toProperty(Integer oauthAppId, OauthAppPropertyData property);

    default List<OauthAppProperty> toProperty(Integer oauthAppId, List<OauthAppPropertyData> properties) {
        if (properties == null) {
            return Collections.emptyList();
        }
        return properties.stream().map(prop -> toProperty(oauthAppId, prop)).collect(Collectors.toList());
    }

}

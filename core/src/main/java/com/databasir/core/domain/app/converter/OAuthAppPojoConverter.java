package com.databasir.core.domain.app.converter;

import com.databasir.core.domain.app.data.OAuthAppCreateRequest;
import com.databasir.core.domain.app.data.OAuthAppDetailResponse;
import com.databasir.core.domain.app.data.OAuthAppPageResponse;
import com.databasir.core.domain.app.data.OAuthAppUpdateRequest;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OAuthAppPojoConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    OauthAppPojo of(OAuthAppCreateRequest request);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    OauthAppPojo of(OAuthAppUpdateRequest request);

    OAuthAppPageResponse toPageResponse(OauthAppPojo pojo);

    OAuthAppDetailResponse toDetailResponse(OauthAppPojo pojo);
}

package com.databasir.core.domain.app.converter;

import com.databasir.core.domain.app.data.OAuthAppCreateRequest;
import com.databasir.core.domain.app.data.OAuthAppDetailResponse;
import com.databasir.core.domain.app.data.OAuthAppPageResponse;
import com.databasir.core.domain.app.data.OAuthAppUpdateRequest;
import com.databasir.dao.tables.pojos.OauthApp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

    OAuthAppDetailResponse toDetailResponse(OauthApp pojo);
}

package com.databasir.core.infrastructure.oauth2.converter;

import com.databasir.core.infrastructure.oauth2.data.OAuthAppResponse;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OAuthAppResponseConverter {

    OAuthAppResponse to(OauthAppPojo pojo);
}

package com.databasir.core.domain.app.converter;

import com.databasir.core.domain.app.data.OAuthAppResponse;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OAuthAppResponseConverter {

    OAuthAppResponse to(OauthAppPojo pojo);
}

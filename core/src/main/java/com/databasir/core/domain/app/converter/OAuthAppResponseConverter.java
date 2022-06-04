package com.databasir.core.domain.app.converter;

import com.databasir.core.domain.app.data.OAuthAppResponse;
import com.databasir.dao.tables.pojos.OauthApp;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OAuthAppResponseConverter {

    OAuthAppResponse to(OauthApp pojo);
}

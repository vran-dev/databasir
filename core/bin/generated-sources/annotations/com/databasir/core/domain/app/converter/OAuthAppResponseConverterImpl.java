package com.databasir.core.domain.app.converter;

import com.databasir.core.domain.app.data.OAuthAppResponse;
import com.databasir.core.domain.app.data.OAuthAppResponse.OAuthAppResponseBuilder;
import com.databasir.dao.tables.pojos.OauthApp;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class OAuthAppResponseConverterImpl implements OAuthAppResponseConverter {

    @Override
    public OAuthAppResponse to(OauthApp pojo) {
        if ( pojo == null ) {
            return null;
        }

        OAuthAppResponseBuilder oAuthAppResponse = OAuthAppResponse.builder();

        oAuthAppResponse.appIcon( pojo.getAppIcon() );
        oAuthAppResponse.appName( pojo.getAppName() );
        oAuthAppResponse.appType( pojo.getAppType() );
        oAuthAppResponse.createAt( pojo.getCreateAt() );
        oAuthAppResponse.id( pojo.getId() );
        oAuthAppResponse.registrationId( pojo.getRegistrationId() );

        return oAuthAppResponse.build();
    }
}

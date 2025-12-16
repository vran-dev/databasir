package com.databasir.core.domain.app.converter;

import com.databasir.core.domain.app.data.OAuthAppCreateRequest;
import com.databasir.core.domain.app.data.OAuthAppDetailResponse;
import com.databasir.core.domain.app.data.OAuthAppPageResponse;
import com.databasir.core.domain.app.data.OAuthAppUpdateRequest;
import com.databasir.core.domain.app.data.OauthAppPropertyData;
import com.databasir.core.domain.app.data.OauthAppPropertyData.OauthAppPropertyDataBuilder;
import com.databasir.dao.tables.pojos.OauthApp;
import com.databasir.dao.tables.pojos.OauthAppProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class OauthAppConverterImpl implements OauthAppConverter {

    @Override
    public OauthApp of(OAuthAppCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        OauthApp oauthApp = new OauthApp();

        oauthApp.setRegistrationId( request.getRegistrationId() );
        oauthApp.setAppName( request.getAppName() );
        oauthApp.setAppIcon( request.getAppIcon() );
        oauthApp.setAppType( request.getAppType() );

        return oauthApp;
    }

    @Override
    public OauthApp of(OAuthAppUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        OauthApp oauthApp = new OauthApp();

        oauthApp.setId( request.getId() );
        oauthApp.setRegistrationId( request.getRegistrationId() );
        oauthApp.setAppName( request.getAppName() );
        oauthApp.setAppIcon( request.getAppIcon() );
        oauthApp.setAppType( request.getAppType() );

        return oauthApp;
    }

    @Override
    public OAuthAppPageResponse toPageResponse(OauthApp pojo) {
        if ( pojo == null ) {
            return null;
        }

        OAuthAppPageResponse oAuthAppPageResponse = new OAuthAppPageResponse();

        oAuthAppPageResponse.setAppIcon( pojo.getAppIcon() );
        oAuthAppPageResponse.setAppName( pojo.getAppName() );
        oAuthAppPageResponse.setAppType( pojo.getAppType() );
        oAuthAppPageResponse.setCreateAt( pojo.getCreateAt() );
        oAuthAppPageResponse.setId( pojo.getId() );
        oAuthAppPageResponse.setRegistrationId( pojo.getRegistrationId() );
        oAuthAppPageResponse.setUpdateAt( pojo.getUpdateAt() );

        return oAuthAppPageResponse;
    }

    @Override
    public OAuthAppDetailResponse toDetailResponse(OauthApp pojo, List<OauthAppProperty> properties) {
        if ( pojo == null && properties == null ) {
            return null;
        }

        OAuthAppDetailResponse oAuthAppDetailResponse = new OAuthAppDetailResponse();

        if ( pojo != null ) {
            oAuthAppDetailResponse.setAppIcon( pojo.getAppIcon() );
            oAuthAppDetailResponse.setAppName( pojo.getAppName() );
            oAuthAppDetailResponse.setAppType( pojo.getAppType() );
            oAuthAppDetailResponse.setCreateAt( pojo.getCreateAt() );
            oAuthAppDetailResponse.setId( pojo.getId() );
            oAuthAppDetailResponse.setRegistrationId( pojo.getRegistrationId() );
            oAuthAppDetailResponse.setUpdateAt( pojo.getUpdateAt() );
        }
        if ( properties != null ) {
            oAuthAppDetailResponse.setProperties( oauthAppPropertyListToOauthAppPropertyDataList( properties ) );
        }

        return oAuthAppDetailResponse;
    }

    @Override
    public OauthAppProperty toProperty(Integer oauthAppId, OauthAppPropertyData property) {
        if ( oauthAppId == null && property == null ) {
            return null;
        }

        OauthAppProperty oauthAppProperty = new OauthAppProperty();

        if ( oauthAppId != null ) {
            oauthAppProperty.setOauthAppId( oauthAppId );
        }
        if ( property != null ) {
            oauthAppProperty.setName( property.getName() );
            oauthAppProperty.setValue( property.getValue() );
        }

        return oauthAppProperty;
    }

    protected OauthAppPropertyData oauthAppPropertyToOauthAppPropertyData(OauthAppProperty oauthAppProperty) {
        if ( oauthAppProperty == null ) {
            return null;
        }

        OauthAppPropertyDataBuilder oauthAppPropertyData = OauthAppPropertyData.builder();

        oauthAppPropertyData.name( oauthAppProperty.getName() );
        oauthAppPropertyData.value( oauthAppProperty.getValue() );

        return oauthAppPropertyData.build();
    }

    protected List<OauthAppPropertyData> oauthAppPropertyListToOauthAppPropertyDataList(List<OauthAppProperty> list) {
        if ( list == null ) {
            return null;
        }

        List<OauthAppPropertyData> list1 = new ArrayList<OauthAppPropertyData>( list.size() );
        for ( OauthAppProperty oauthAppProperty : list ) {
            list1.add( oauthAppPropertyToOauthAppPropertyData( oauthAppProperty ) );
        }

        return list1;
    }
}

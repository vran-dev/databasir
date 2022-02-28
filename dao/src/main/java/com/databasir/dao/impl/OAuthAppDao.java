package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.pojos.OauthAppPojo;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.databasir.dao.Tables.OAUTH_APP;

@Repository
public class OAuthAppDao extends BaseDao<OauthAppPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public OAuthAppDao() {
        super(OAUTH_APP, OauthAppPojo.class);
    }

    public Optional<OauthAppPojo> selectOptionByRegistrationId(String registrationId) {
        return this.getDslContext()
                .select(OAUTH_APP.fields()).from(OAUTH_APP).where(OAUTH_APP.REGISTRATION_ID.eq(registrationId))
                .fetchOptionalInto(OauthAppPojo.class);
    }

    public OauthAppPojo selectByRegistrationId(String registrationId) {
        return this.getDslContext()
                .select(OAUTH_APP.fields()).from(OAUTH_APP).where(OAUTH_APP.REGISTRATION_ID.eq(registrationId))
                .fetchOptionalInto(OauthAppPojo.class)
                .orElseThrow(() -> new DataNotExistsException("can not found oauth app by " + registrationId));
    }
}
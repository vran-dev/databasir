package com.databasir.dao.impl;

import com.databasir.dao.exception.DataNotExistsException;
import com.databasir.dao.tables.pojos.OauthApp;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.databasir.dao.Tables.OAUTH_APP;

@Repository
public class OauthAppDao extends BaseDao<OauthApp> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public OauthAppDao() {
        super(OAUTH_APP, OauthApp.class);
    }

    public Optional<OauthApp> selectOptionByRegistrationId(String registrationId) {
        return this.getDslContext()
                .select(OAUTH_APP.fields()).from(OAUTH_APP).where(OAUTH_APP.REGISTRATION_ID.eq(registrationId))
                .fetchOptionalInto(OauthApp.class);
    }

    public OauthApp selectByRegistrationId(String registrationId) {
        return this.selectOptionByRegistrationId(registrationId)
                .orElseThrow(() -> new DataNotExistsException("can not found oauth app by " + registrationId));
    }
}
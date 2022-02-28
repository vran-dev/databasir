package com.databasir.dao.impl;

import com.databasir.dao.tables.OauthApp;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.databasir.dao.Tables.OAUTH_APP;

@Repository
public class OAuthAppDao extends BaseDao<OauthApp> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public OAuthAppDao() {
        super(OAUTH_APP, OauthApp.class);
    }

}
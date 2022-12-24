package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.OauthAppProperty;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.databasir.dao.Tables.OAUTH_APP_PROPERTY;

@Repository
public class OauthAppPropertyDao extends BaseDao<OauthAppProperty> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public OauthAppPropertyDao() {
        super(OAUTH_APP_PROPERTY, OauthAppProperty.class);
    }

    public int deleteByOauthAppId(Integer oauthAppId) {
        return this.getDslContext()
                .deleteFrom(OAUTH_APP_PROPERTY)
                .where(OAUTH_APP_PROPERTY.OAUTH_APP_ID.eq(oauthAppId))
                .execute();
    }

    public List<OauthAppProperty> selectByOauthAppId(Integer oauthAppID) {
        return this.getDslContext()
                .select(OAUTH_APP_PROPERTY.fields())
                .from(OAUTH_APP_PROPERTY).where(OAUTH_APP_PROPERTY.OAUTH_APP_ID.eq(oauthAppID))
                .fetchInto(OauthAppProperty.class);
    }
}

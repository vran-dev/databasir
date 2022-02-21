package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.LoginPojo;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.databasir.dao.Tables.LOGIN;

@Repository
public class LoginDao extends BaseDao<LoginPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public LoginDao() {
        super(LOGIN, LoginPojo.class);
    }

    public void deleteByUserId(Integer userId) {
        getDslContext()
                .deleteFrom(LOGIN).where(LOGIN.USER_ID.eq(userId))
                .execute();
    }

    public Optional<LoginPojo> selectByUserId(Integer userId) {
        return getDslContext()
                .select(LOGIN.fields()).from(LOGIN).where(LOGIN.USER_ID.eq(userId))
                .fetchOptionalInto(LoginPojo.class);
    }

    public Optional<LoginPojo> selectByRefreshToken(String refreshToken) {
        return getDslContext()
                .select(LOGIN.fields()).from(LOGIN).where(LOGIN.REFRESH_TOKEN.eq(refreshToken))
                .fetchOptionalInto(LoginPojo.class);
    }

    public void updateAccessToken(String accessToken, LocalDateTime accessTokenExpireAt, Integer userId) {
        getDslContext()
                .update(LOGIN)
                .set(LOGIN.ACCESS_TOKEN, accessToken)
                .set(LOGIN.ACCESS_TOKEN_EXPIRE_AT, accessTokenExpireAt)
                .where(LOGIN.USER_ID.eq(userId))
                .execute();
    }

    public void insertOnDuplicateKeyUpdate(LoginPojo loginPojo) {
        getDslContext()
                .insertInto(LOGIN)
                .set(LOGIN.USER_ID, loginPojo.getUserId())
                .set(LOGIN.ACCESS_TOKEN, loginPojo.getAccessToken())
                .set(LOGIN.ACCESS_TOKEN_EXPIRE_AT, loginPojo.getAccessTokenExpireAt())
                .set(LOGIN.REFRESH_TOKEN, loginPojo.getRefreshToken())
                .set(LOGIN.REFRESH_TOKEN_EXPIRE_AT, loginPojo.getRefreshTokenExpireAt())
                .onDuplicateKeyUpdate()
                .set(LOGIN.ACCESS_TOKEN, loginPojo.getAccessToken())
                .set(LOGIN.ACCESS_TOKEN_EXPIRE_AT, loginPojo.getAccessTokenExpireAt())
                .set(LOGIN.REFRESH_TOKEN, loginPojo.getRefreshToken())
                .set(LOGIN.REFRESH_TOKEN_EXPIRE_AT, loginPojo.getRefreshTokenExpireAt())
                .execute();
    }
}

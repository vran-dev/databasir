package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.Login;
import lombok.Getter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.databasir.dao.Tables.LOGIN;

@Repository
public class LoginDao extends BaseDao<Login> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public LoginDao() {
        super(LOGIN, Login.class);
    }

    public void deleteByUserId(Integer userId) {
        getDslContext()
                .deleteFrom(LOGIN).where(LOGIN.USER_ID.eq(userId))
                .execute();
    }

    public Optional<Login> selectByUserId(Integer userId) {
        return getDslContext()
                .select(LOGIN.fields()).from(LOGIN).where(LOGIN.USER_ID.eq(userId))
                .fetchOptionalInto(Login.class);
    }

    public Optional<Login> selectByRefreshToken(String refreshToken) {
        return getDslContext()
                .select(LOGIN.fields()).from(LOGIN).where(LOGIN.REFRESH_TOKEN.eq(refreshToken))
                .fetchOptionalInto(Login.class);
    }

    public Optional<Login> selectByAccessToken(String accessToken) {
        return getDslContext()
                .selectFrom(LOGIN).where(LOGIN.ACCESS_TOKEN.eq(accessToken)
                        .and(LOGIN.ACCESS_TOKEN_EXPIRE_AT.ge(LocalDateTime.now())))
                .fetchOptionalInto(Login.class);
    }

    public void updateAccessToken(String accessToken, LocalDateTime accessTokenExpireAt, Integer userId) {
        getDslContext()
                .update(LOGIN)
                .set(LOGIN.ACCESS_TOKEN, accessToken)
                .set(LOGIN.ACCESS_TOKEN_EXPIRE_AT, accessTokenExpireAt)
                .where(LOGIN.USER_ID.eq(userId))
                .execute();
    }

    public void insertOnDuplicateKeyUpdate(Login login) {
        getDslContext()
                .insertInto(LOGIN)
                .set(LOGIN.USER_ID, login.getUserId())
                .set(LOGIN.ACCESS_TOKEN, login.getAccessToken())
                .set(LOGIN.ACCESS_TOKEN_EXPIRE_AT, login.getAccessTokenExpireAt())
                .set(LOGIN.REFRESH_TOKEN, login.getRefreshToken())
                .set(LOGIN.REFRESH_TOKEN_EXPIRE_AT, login.getRefreshTokenExpireAt())
                .onDuplicateKeyUpdate()
                .set(LOGIN.ACCESS_TOKEN, login.getAccessToken())
                .set(LOGIN.ACCESS_TOKEN_EXPIRE_AT, login.getAccessTokenExpireAt())
                .set(LOGIN.REFRESH_TOKEN, login.getRefreshToken())
                .set(LOGIN.REFRESH_TOKEN_EXPIRE_AT, login.getRefreshTokenExpireAt())
                .execute();
    }
}

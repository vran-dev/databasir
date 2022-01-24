package com.databasir.core.domain.login.service;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.login.data.AccessTokenRefreshRequest;
import com.databasir.core.domain.login.data.AccessTokenRefreshResponse;
import com.databasir.core.domain.login.data.LoginKeyResponse;
import com.databasir.core.infrastructure.jwt.JwtTokens;
import com.databasir.dao.impl.LoginDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.LoginPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final LoginDao loginDao;

    private final UserDao userDao;

    private final JwtTokens jwtTokens;

    public AccessTokenRefreshResponse refreshAccessTokens(AccessTokenRefreshRequest request) {
        LoginPojo login = loginDao.selectByRefreshToken(request.getRefreshToken())
                .orElseThrow(DomainErrors.ACCESS_TOKEN_REFRESH_INVALID::exception);
        // refresh-token 已过期
        if (login.getRefreshTokenExpireAt().isBefore(LocalDateTime.now())) {
            throw DomainErrors.REFRESH_TOKEN_EXPIRED.exception();
        }
        // access-token 未过期就开始刷新有可能是 refresh-token  泄露了，删除 refresh-token
        if (login.getAccessTokenExpireAt().isAfter(LocalDateTime.now())) {
            log.warn("invalid access token refresh operation: request = {}, login = {}", request, login);
            loginDao.deleteByUserId(login.getUserId());
            throw DomainErrors.ACCESS_TOKEN_REFRESH_INVALID.exception();
        }
        UserPojo user = userDao.selectById(login.getUserId());
        String accessToken = jwtTokens.accessToken(user.getEmail());
        LocalDateTime accessTokenExpireAt = jwtTokens.expireAt(accessToken);
        loginDao.updateAccessToken(accessToken, accessTokenExpireAt, user.getId());
        Instant instant = accessTokenExpireAt.atZone(ZoneId.systemDefault()).toInstant();
        long accessTokenExpireAtMilli = instant.toEpochMilli();
        return new AccessTokenRefreshResponse(accessToken, accessTokenExpireAtMilli);
    }

    public Optional<LoginKeyResponse> getLoginKey(Integer userId) {
        return loginDao.selectByUserId(userId)
                .map(loginPojo -> LoginKeyResponse.builder()
                        .accessToken(loginPojo.getAccessToken())
                        .accessTokenExpireAt(loginPojo.getAccessTokenExpireAt())
                        .refreshToken(loginPojo.getRefreshToken())
                        .refreshTokenExpireAt(loginPojo.getRefreshTokenExpireAt())
                        .build());
    }

    public LoginKeyResponse generate(Integer userId) {
        UserPojo user = userDao.selectById(userId);
        String accessToken = jwtTokens.accessToken(user.getEmail());
        LocalDateTime accessTokenExpireAt = jwtTokens.expireAt(accessToken);
        String refreshToken = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime refreshTokenExpireAt = LocalDateTime.now().plusDays(15);

        LoginPojo loginPojo = new LoginPojo();
        loginPojo.setAccessToken(accessToken);
        loginPojo.setAccessTokenExpireAt(accessTokenExpireAt);
        loginPojo.setRefreshToken(refreshToken);
        loginPojo.setRefreshTokenExpireAt(refreshTokenExpireAt);
        loginPojo.setUserId(userId);
        loginDao.insertOnDuplicateKeyUpdate(loginPojo);

        return LoginKeyResponse.builder()
                .userId(userId)
                .accessToken(accessToken)
                .accessTokenExpireAt(accessTokenExpireAt)
                .refreshToken(refreshToken)
                .refreshTokenExpireAt(refreshTokenExpireAt)
                .build();
    }
}

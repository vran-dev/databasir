package com.databasir.core.domain.login.service;

import com.databasir.common.DatabasirException;
import com.databasir.core.BaseTest;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.login.data.AccessTokenRefreshRequest;
import com.databasir.core.domain.login.data.AccessTokenRefreshResponse;
import com.databasir.core.domain.login.data.LoginKeyResponse;
import com.databasir.dao.impl.LoginDao;
import com.databasir.dao.tables.pojos.Login;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
class LoginServiceTest extends BaseTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginDao loginDao;

    @Test
    void refreshAccessTokensWhenRefreshTokenNotExists() {
        AccessTokenRefreshRequest request = new AccessTokenRefreshRequest();
        request.setRefreshToken(UUID.randomUUID().toString());
        DatabasirException err = Assertions.assertThrows(DatabasirException.class,
                () -> loginService.refreshAccessTokens(request));
        Assertions.assertEquals(DomainErrors.INVALID_REFRESH_TOKEN_OPERATION.getErrCode(), err.getErrCode());
    }

    @Test
    @Sql("classpath:sql/domain/login/RefreshAccessTokensWhenUserNotExists.sql")
    void refreshAccessTokensWhenUserNotExists() {
        AccessTokenRefreshRequest request = new AccessTokenRefreshRequest();
        request.setRefreshToken("2a884c14ef654e14b069f8ca32ce0261");
        DatabasirException err = Assertions.assertThrows(DatabasirException.class,
                () -> loginService.refreshAccessTokens(request));
        Assertions.assertEquals(DomainErrors.INVALID_REFRESH_TOKEN_OPERATION.getErrCode(), err.getErrCode());
    }

    @Test
    @Sql("classpath:sql/domain/login/RefreshAccessTokensWhenUserDisabled.sql")
    void refreshAccessTokensWhenUserDisabled() {
        AccessTokenRefreshRequest request = new AccessTokenRefreshRequest();
        request.setRefreshToken("2a884c14ef654e14b069f8ca32ce0261");
        DatabasirException err = Assertions.assertThrows(DatabasirException.class,
                () -> loginService.refreshAccessTokens(request));
        Assertions.assertEquals(DomainErrors.INVALID_REFRESH_TOKEN_OPERATION.getErrCode(), err.getErrCode());
    }

    @Test
    @Sql("classpath:sql/domain/login/RefreshAccessTokens.sql")
    void refreshAccessTokens() {
        AccessTokenRefreshRequest request = new AccessTokenRefreshRequest();
        request.setRefreshToken("2a884c14ef654e14b069f8ca32ce0261");

        AccessTokenRefreshResponse response = loginService.refreshAccessTokens(request);
        Assertions.assertNotNull(response);
        Assertions.assertNotEquals("xxxx", response.getAccessToken());
    }

    @Test
    @Sql("classpath:sql/domain/login/Generate.sql")
    void generate() {
        int userId = -1000;
        LoginKeyResponse token = loginService.generate(userId);
        Assertions.assertNotNull(token);
        Optional<Login> loginOpt = loginDao.selectByUserId(userId);
        Assertions.assertTrue(loginOpt.isPresent());
        Assertions.assertEquals(token.getAccessToken(), loginOpt.get().getAccessToken());
        Assertions.assertEquals(token.getRefreshToken(), loginOpt.get().getRefreshToken());
    }
}
package com.databasir.core.domain.user.service;

import com.databasir.core.BaseTest;
import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.core.domain.user.data.UserSource;
import com.databasir.core.infrastructure.event.subscriber.UserEventSubscriber;
import com.databasir.dao.impl.LoginDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.LoginPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEventSubscriber userEventSubscriber;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginDao loginDao;

    @Test
    void create() {
        doNothing().when(userEventSubscriber).onUserCreated(any());

        String username = UUID.randomUUID().toString().replace("-", "");
        UserCreateRequest request = new UserCreateRequest();
        request.setAvatar(username);
        request.setUsername(username);
        request.setNickname(username);
        request.setEmail(username + "@Databasir-ut.com");
        request.setPassword("123456");
        request.setEnabled(true);
        Integer id = userService.create(request, UserSource.MANUAL);
        assertNotNull(id);
        verify(userEventSubscriber, times(1)).onUserCreated(any());
    }

    @Test
    @Sql("classpath:sql/domain/user/RenewPassword.sql")
    void renewPassword() {
        doNothing().when(userEventSubscriber).onPasswordRenewed(any());
        String newPassword = userService.renewPassword(1, 2);
        assertNotNull(newPassword);
        assertEquals(8, newPassword.length());
        verify(userEventSubscriber, times(1)).onPasswordRenewed(any());
    }

    @Test
    @Sql("classpath:sql/domain/user/SwitchEnableStatus.sql")
    void switchEnableStatusToFalse() {
        Integer userId = 1;
        userService.switchEnableStatus(userId, false);
        UserPojo user = userDao.selectById(userId);
        assertNotNull(user);
        assertFalse(user.getEnabled());

        Optional<LoginPojo> loginPojoOpt = loginDao.selectByUserId(userId);
        assertTrue(loginPojoOpt.isEmpty());
    }

    @Test
    void removeSysOwnerFrom() {
        // TODO
    }

    @Test
    void addSysOwnerTo() {
        // TODO
    }

    @Test
    void updatePassword() {
        // TODO
    }

    @Test
    void updateNickname() {
        // TODO
    }
}
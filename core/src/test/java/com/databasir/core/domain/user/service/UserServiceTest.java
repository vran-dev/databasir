package com.databasir.core.domain.user.service;

import com.databasir.core.BaseTest;
import com.databasir.core.domain.user.data.UserCreateRequest;
import com.databasir.core.domain.user.data.UserNicknameUpdateRequest;
import com.databasir.core.domain.user.data.UserPasswordUpdateRequest;
import com.databasir.core.domain.user.data.UserSource;
import com.databasir.core.infrastructure.event.subscriber.UserEventSubscriber;
import com.databasir.dao.impl.LoginDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.impl.UserRoleDao;
import com.databasir.dao.tables.pojos.Login;
import com.databasir.dao.tables.pojos.User;
import com.databasir.dao.tables.pojos.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.databasir.core.infrastructure.constant.RoleConstants.SYS_OWNER;
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

    @Autowired
    private UserRoleDao userRoleDao;

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
        String newPassword = userService.renewPassword(-1, -2);
        assertNotNull(newPassword);
        assertEquals(8, newPassword.length());
        verify(userEventSubscriber, times(1)).onPasswordRenewed(any());
    }

    @Test
    @Sql("classpath:sql/domain/user/SwitchEnableStatus.sql")
    void switchEnableStatusToFalse() {
        Integer userId = -999;
        userService.switchEnableStatus(userId, false);
        User user = userDao.selectById(userId);
        assertNotNull(user);
        assertFalse(user.getEnabled());

        Optional<Login> login = loginDao.selectByUserId(userId);
        assertTrue(login.isEmpty());
    }

    @Test
    @Sql("classpath:sql/domain/user/RemoveSysOwnerFrom.sql")
    void removeSysOwnerFrom() {
        Integer userId = -998;
        userService.removeSysOwnerFrom(userId);
        List<UserRole> roles = userRoleDao.selectByUserIds(Collections.singletonList(userId))
                .stream().filter(role -> role.getRole().equals(SYS_OWNER))
                .collect(Collectors.toList());
        assertEquals(0, roles.size());
    }

    @Test
    @Sql("classpath:sql/domain/user/AddSysOwnerTo.sql")
    void addSysOwnerTo() {
        Integer userId = -999;
        userService.addSysOwnerTo(userId);
        List<UserRole> roles = userRoleDao.selectByUserIds(Collections.singletonList(userId))
                .stream().filter(role -> role.getRole().equals(SYS_OWNER))
                .collect(Collectors.toList());

        assertEquals(1, roles.size());
        assertEquals(SYS_OWNER, roles.iterator().next().getRole());
    }

    @Test
    @Sql("classpath:sql/domain/user/UpdatePassword.sql")
    void updatePassword() {
        UserPasswordUpdateRequest request = new UserPasswordUpdateRequest();
        request.setNewPassword("123456");
        request.setConfirmNewPassword("123456");
        request.setOriginPassword("123123");
        Integer userId = -999;
        userService.updatePassword(userId, request);
        // should delete login info
        Optional<Login> login = loginDao.selectByUserId(userId);
        assertTrue(login.isEmpty());
    }

    @Test
    @Sql("classpath:sql/domain/user/UpdateNickname.sql")
    void updateNickname() {
        Integer userId = -999;
        UserNicknameUpdateRequest request = new UserNicknameUpdateRequest();
        String nickname = UUID.randomUUID().toString();
        request.setNickname(nickname);
        userService.updateNickname(userId, request);

        User user = userDao.selectById(userId);
        assertNotNull(user);
        assertEquals(nickname, user.getNickname());
    }
}
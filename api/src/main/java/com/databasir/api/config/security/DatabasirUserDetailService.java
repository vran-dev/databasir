package com.databasir.api.config.security;

import com.databasir.core.domain.log.service.OperationLogService;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.impl.UserRoleDao;
import com.databasir.dao.tables.pojos.User;
import com.databasir.dao.tables.pojos.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabasirUserDetailService implements UserDetailsService {

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private final OperationLogService operationLogService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByUsername(username, null);
    }

    public UserDetails loadUserByUsername(String username, String registrationId) throws UsernameNotFoundException {
        User user = userDao.selectByEmailOrUsername(username)
                .orElseThrow(() -> {
                    String operationName = registrationId == null ? "登录" : registrationId + " 登录";
                    operationLogService.saveLoginFailedLog(username, operationName, "用户名不存在");
                    return new UsernameNotFoundException("用户名或密码错误");
                });
        List<UserRole> roles = userRoleDao.selectByUserIds(Collections.singletonList(user.getId()));
        return new DatabasirUserDetails(user, roles, registrationId);
    }
}

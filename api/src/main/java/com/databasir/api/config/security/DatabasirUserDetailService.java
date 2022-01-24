package com.databasir.api.config.security;

import com.databasir.dao.impl.LoginDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.impl.UserRoleDao;
import com.databasir.dao.tables.pojos.UserPojo;
import com.databasir.dao.tables.pojos.UserRolePojo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabasirUserDetailService implements UserDetailsService {

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private final LoginDao loginDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo user = userDao.selectByEmailOrUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));
        List<UserRolePojo> roles = userRoleDao.selectByUserIds(Collections.singletonList(user.getId()));
        return new DatabasirUserDetails(user, roles);
    }
}

package com.databasir.core.domain.user.service;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.user.converter.UserPojoConverter;
import com.databasir.core.domain.user.converter.UserResponseConverter;
import com.databasir.core.domain.user.data.*;
import com.databasir.core.infrastructure.mail.MailSender;
import com.databasir.dao.impl.GroupDao;
import com.databasir.dao.impl.SysMailDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.impl.UserRoleDao;
import com.databasir.dao.tables.pojos.GroupPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import com.databasir.dao.tables.pojos.UserRolePojo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private final GroupDao groupDao;

    private final SysMailDao sysMailDao;

    private final UserPojoConverter userPojoConverter;

    private final UserResponseConverter userResponseConverter;

    private final MailSender mailSender;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Page<UserPageResponse> list(Pageable pageable, UserPageCondition condition) {
        Page<UserPojo> users = userDao.selectByPage(pageable, condition.toCondition());
        List<Integer> userIds = users.getContent()
                .stream()
                .map(UserPojo::getId)
                .collect(toList());
        List<UserRolePojo> userRoles = userRoleDao.selectByUserIds(userIds);
        Map<Integer, List<Integer>> groupIdMapByUserId = userRoles
                .stream()
                .filter(ur -> ur.getGroupId() != null)
                .collect(groupingBy(UserRolePojo::getUserId, mapping(UserRolePojo::getGroupId, toList())));
        Map<Integer, List<UserRolePojo>> sysOwnerGroupByUserId = userRoles.stream()
                .filter(ur -> ur.getRole().equals("SYS_OWNER"))
                .collect(groupingBy(UserRolePojo::getUserId));
        return users.map(user ->
                userResponseConverter.pageResponse(user, sysOwnerGroupByUserId.containsKey(user.getId()),
                        groupIdMapByUserId.get(user.getId())));
    }

    @Transactional
    public void create(UserCreateRequest userCreateRequest) {
        userDao.selectByEmailOrUsername(userCreateRequest.getUsername()).ifPresent(data -> {
            throw DomainErrors.USERNAME_OR_EMAIL_DUPLICATE.exception();
        });
        String hashedPassword = bCryptPasswordEncoder.encode(userCreateRequest.getPassword());
        UserPojo pojo = userPojoConverter.of(userCreateRequest, hashedPassword);
        try {
            userDao.insertAndReturnId(pojo);
        } catch (DuplicateKeyException e) {
            throw DomainErrors.USERNAME_OR_EMAIL_DUPLICATE.exception();
        }
    }

    public UserDetailResponse get(Integer userId) {
        UserPojo pojo = userDao.selectById(userId);
        List<UserRolePojo> roles = userRoleDao.selectByUserIds(Collections.singletonList(userId));
        List<Integer> groupIds = roles.stream()
                .map(UserRolePojo::getGroupId)
                .filter(Objects::nonNull)
                .collect(toList());
        Map<Integer, String> groupNameMapById = groupDao.selectInIds(groupIds)
                .stream()
                .collect(toMap(GroupPojo::getId, GroupPojo::getName));
        return userResponseConverter.detailResponse(pojo, roles, groupNameMapById);
    }

    @Transactional
    public String renewPassword(Integer userId) {
        UserPojo userPojo = userDao.selectById(userId);
        String randomPassword = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 8);
        String hashedPassword = bCryptPasswordEncoder.encode(randomPassword);
        userDao.updatePassword(userId, hashedPassword);
        sysMailDao.selectOptionTopOne()
                .ifPresent(mailPojo -> {
                    String subject = "Databasir 密码重置提醒";
                    String message = "您的密码已被重置，新密码为：" + randomPassword;
                    mailSender.send(mailPojo, userPojo.getEmail(), subject, message);
                });
        return randomPassword;
    }

    public void switchEnableStatus(Integer userId, Boolean enable) {
        userDao.updateEnabledByUserId(userId, enable);
    }

    public void removeSysOwnerFrom(Integer userId) {
        if (userRoleDao.hasRole(userId, "SYS_OWNER")) {
            userRoleDao.deleteRole(userId, "SYS_OWNER");
        }
    }

    public void addSysOwnerTo(Integer userId) {
        if (!userRoleDao.hasRole(userId, "SYS_OWNER")) {
            UserRolePojo role = new UserRolePojo();
            role.setUserId(userId);
            role.setRole("SYS_OWNER");
            userRoleDao.insertAndReturnId(role);
        }
    }

    public void updatePassword(Integer userId, UserPasswordUpdateRequest request) {
        if (!Objects.equals(request.getNewPassword(), request.getConfirmNewPassword())) {
            throw DomainErrors.UPDATE_PASSWORD_CONFIRM_FAILED.exception();
        }
        UserPojo userPojo = userDao.selectById(userId);
        if (!bCryptPasswordEncoder.matches(request.getOriginPassword(), userPojo.getPassword())) {
            throw DomainErrors.ORIGIN_PASSWORD_NOT_CORRECT.exception();
        }
        String newHashedPassword = bCryptPasswordEncoder.encode(request.getNewPassword());
        userDao.updatePassword(userId, newHashedPassword);
    }

    public void updateNickname(Integer userId, UserNicknameUpdateRequest request) {
        UserPojo userPojo = userDao.selectById(userId);
        userPojo.setNickname(request.getNickname());
        userDao.updateById(userPojo);
    }
}

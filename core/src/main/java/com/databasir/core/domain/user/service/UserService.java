package com.databasir.core.domain.user.service;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.user.converter.UserPojoConverter;
import com.databasir.core.domain.user.converter.UserResponseConverter;
import com.databasir.core.domain.user.data.*;
import com.databasir.core.domain.user.event.UserCreated;
import com.databasir.core.domain.user.event.UserPasswordRenewed;
import com.databasir.core.domain.user.event.converter.UserEventConverter;
import com.databasir.core.infrastructure.event.EventPublisher;
import com.databasir.dao.impl.GroupDao;
import com.databasir.dao.impl.LoginDao;
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

import java.time.LocalDateTime;
import java.util.*;

import static com.databasir.core.infrastructure.constant.RoleConstants.SYS_OWNER;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private final GroupDao groupDao;

    private final LoginDao loginDao;

    private final UserPojoConverter userPojoConverter;

    private final UserResponseConverter userResponseConverter;

    private final UserEventConverter userEventConverter;

    private final EventPublisher eventPublisher;

    @SuppressWarnings("checkstyle:all")
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
                .filter(ur -> ur.getRole().equals(SYS_OWNER))
                .collect(groupingBy(UserRolePojo::getUserId));
        return users.map(user ->
                userResponseConverter.pageResponse(user, sysOwnerGroupByUserId.containsKey(user.getId()),
                        groupIdMapByUserId.get(user.getId())));
    }

    @Transactional
    public Integer create(UserCreateRequest userCreateRequest, String source) {
        userDao.selectByEmailOrUsername(userCreateRequest.getUsername()).ifPresent(data -> {
            throw DomainErrors.USERNAME_OR_EMAIL_DUPLICATE.exception();
        });
        String hashedPassword = bCryptPasswordEncoder.encode(userCreateRequest.getPassword());
        UserPojo pojo = userPojoConverter.of(userCreateRequest, hashedPassword);
        try {
            Integer id = userDao.insertAndReturnId(pojo);
            // publish event
            UserCreated event = userEventConverter.userCreated(pojo, source, userCreateRequest.getPassword(), id);
            eventPublisher.publish(event);
            return id;
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

    public Optional<UserDetailResponse> get(String email) {
        return userDao.selectByEmail(email)
                .map(user -> {
                    List<UserRolePojo> roles = userRoleDao.selectByUserIds(Collections.singletonList(user.getId()));
                    List<Integer> groupIds = roles.stream()
                            .map(UserRolePojo::getGroupId)
                            .filter(Objects::nonNull)
                            .collect(toList());
                    Map<Integer, String> groupNameMapById = groupDao.selectInIds(groupIds)
                            .stream()
                            .collect(toMap(GroupPojo::getId, GroupPojo::getName));
                    return userResponseConverter.detailResponse(user, roles, groupNameMapById);
                });
    }

    @Transactional
    public String renewPassword(Integer renewByUserId, Integer userId) {
        UserPojo pojo = userDao.selectById(userId);
        String randomPassword = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 8);
        String hashedPassword = bCryptPasswordEncoder.encode(randomPassword);
        userDao.updatePassword(userId, hashedPassword);

        // publish event
        UserPasswordRenewed event = userEventConverter.userPasswordRenewed(pojo,
                renewByUserId,
                LocalDateTime.now(),
                randomPassword);
        eventPublisher.publish(event);
        return randomPassword;
    }

    @Transactional
    public void switchEnableStatus(Integer userId, Boolean enable) {
        userDao.updateEnabledByUserId(userId, enable);
        if (!enable) {
            loginDao.deleteByUserId(userId);
        }
    }

    public void removeSysOwnerFrom(Integer userId) {
        if (userRoleDao.hasRole(userId, SYS_OWNER)) {
            userRoleDao.deleteRole(userId, SYS_OWNER);
        }
    }

    public void addSysOwnerTo(Integer userId) {
        if (!userRoleDao.hasRole(userId, SYS_OWNER)) {
            UserRolePojo role = new UserRolePojo();
            role.setUserId(userId);
            role.setRole(SYS_OWNER);
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
        loginDao.deleteByUserId(userId);
    }

    public void updateNickname(Integer userId, UserNicknameUpdateRequest request) {
        UserPojo userPojo = userDao.selectById(userId);
        userPojo.setNickname(request.getNickname());
        userDao.updateById(userPojo);
    }

    @Transactional
    public void deleteOne(Integer userId) {
        if (userDao.existsById(userId)) {
            userDao.deleteById(userId);
            loginDao.deleteByUserId(userId);
        }
    }
}

package com.databasir.core.domain.group.service;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.group.converter.GroupPojoConverter;
import com.databasir.core.domain.group.converter.GroupResponseConverter;
import com.databasir.core.domain.group.data.*;
import com.databasir.dao.impl.GroupDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.impl.UserRoleDao;
import com.databasir.dao.tables.pojos.GroupPojo;
import com.databasir.dao.tables.pojos.UserPojo;
import com.databasir.dao.tables.pojos.UserRolePojo;
import com.databasir.dao.value.GroupMemberSimplePojo;
import com.databasir.dao.value.GroupProjectCountPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupDao groupDao;

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    private final ProjectDao projectDao;

    private final GroupPojoConverter groupPojoConverter;

    private final GroupResponseConverter groupResponseConverter;

    @Transactional
    public void create(GroupCreateRequest request) {
        GroupPojo groupPojo = groupPojoConverter.of(request);
        Integer groupId = groupDao.insertAndReturnId(groupPojo);
        List<UserRolePojo> roles = request.getGroupOwnerUserIds()
                .stream()
                .map(userId -> {
                    UserRolePojo role = new UserRolePojo();
                    role.setUserId(userId);
                    role.setRole("GROUP_OWNER");
                    role.setGroupId(groupId);
                    return role;
                })
                .collect(Collectors.toList());
        userRoleDao.batchInsert(roles);
    }

    @Transactional
    public void update(GroupUpdateRequest request) {
        GroupPojo groupPojo = groupPojoConverter.of(request);
        groupDao.updateById(groupPojo);
        userRoleDao.deleteByRoleAndGroupId("GROUP_OWNER", groupPojo.getId());
        List<UserRolePojo> roles = request.getGroupOwnerUserIds()
                .stream()
                .map(userId -> {
                    UserRolePojo role = new UserRolePojo();
                    role.setUserId(userId);
                    role.setRole("GROUP_OWNER");
                    role.setGroupId(groupPojo.getId());
                    return role;
                })
                .collect(Collectors.toList());
        userRoleDao.batchInsert(roles);
    }

    public void delete(Integer groupId) {
        groupDao.deleteById(groupId);
        userRoleDao.deleteByGroupId(groupId);
    }

    public Page<GroupPageResponse> list(Pageable pageable, GroupPageCondition condition) {
        Page<GroupPojo> page = groupDao.selectByPage(pageable, condition.toCondition());
        List<Integer> groupIdList = page.getContent()
                .stream()
                .map(GroupPojo::getId)
                .collect(Collectors.toList());
        Map<Integer, List<GroupMemberSimplePojo>> ownersGroupByGroupId = userRoleDao.selectOwnerNamesByGroupIdIn(groupIdList)
                .stream()
                .collect(Collectors.groupingBy(GroupMemberSimplePojo::getGroupId));
        Map<Integer, GroupProjectCountPojo> projectCountMapByGroupId = projectDao.selectCountByGroupIds(groupIdList)
                .stream()
                .collect(Collectors.toMap(GroupProjectCountPojo::getGroupId, v -> v));
        return page.map(groupPojo -> {
            Integer groupId = groupPojo.getId();
            List<String> owners = ownersGroupByGroupId.getOrDefault(groupId, new ArrayList<>())
                    .stream()
                    .map(GroupMemberSimplePojo::getNickname)
                    .collect(Collectors.toList());
            GroupProjectCountPojo countPojo = projectCountMapByGroupId.get(groupId);
            Integer projectCount = countPojo == null ? 0 : countPojo.getCount();
            return groupResponseConverter.toResponse(groupPojo, owners, projectCount);
        });
    }

    public Page<GroupMemberPageResponse> listGroupMembers(Integer groupId,
                                                          Pageable pageable,
                                                          GroupMemberPageCondition condition) {
        return userDao.selectGroupMembers(groupId, pageable, condition.toCondition())
                .map(groupResponseConverter::toResponse);
    }

    public GroupResponse get(Integer groupId) {
        GroupPojo groupPojo = groupDao.selectById(groupId);
        List<UserPojo> users = userDao.selectLimitUsersByRoleAndGroup(groupId, "GROUP_OWNER", 50);
        return groupResponseConverter.toResponse(groupPojo, users);
    }

    public void removeMember(Integer groupId, Integer userId) {
        userRoleDao.deleteByUserIdAndGroupId(userId, groupId);
    }

    public void addMember(Integer groupId, GroupMemberCreateRequest request) {
        if (userRoleDao.hasRole(request.getUserId(), groupId)) {
            throw DomainErrors.USER_ROLE_DUPLICATE.exception();
        }
        UserRolePojo pojo = new UserRolePojo();
        pojo.setGroupId(groupId);
        pojo.setUserId(request.getUserId());
        pojo.setRole(request.getRole());
        userRoleDao.insertAndReturnId(pojo);
    }

    public void changeMemberRole(Integer groupId, Integer userId, String role) {
        if (!userRoleDao.hasRole(userId, groupId, role)) {
            // TODO 最多 20 个组长
            userRoleDao.deleteByUserIdAndGroupId(userId, groupId);
            UserRolePojo pojo = new UserRolePojo();
            pojo.setUserId(userId);
            pojo.setGroupId(groupId);
            pojo.setRole(role);
            userRoleDao.insertAndReturnId(pojo);
        }
    }
}

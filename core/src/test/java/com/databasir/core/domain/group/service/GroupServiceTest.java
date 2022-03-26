package com.databasir.core.domain.group.service;

import com.databasir.common.DatabasirException;
import com.databasir.core.BaseTest;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.group.data.GroupCreateRequest;
import com.databasir.core.domain.group.data.GroupMemberCreateRequest;
import com.databasir.core.domain.group.data.GroupUpdateRequest;
import com.databasir.core.infrastructure.constant.RoleConstants;
import com.databasir.dao.Tables;
import com.databasir.dao.impl.GroupDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.ProjectSyncRuleDao;
import com.databasir.dao.impl.UserRoleDao;
import com.databasir.dao.tables.pojos.ProjectSyncRulePojo;
import com.databasir.dao.tables.pojos.UserRolePojo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class GroupServiceTest extends BaseTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectSyncRuleDao projectSyncRuleDao;

    @Test
    void create() {
        GroupCreateRequest request = new GroupCreateRequest();
        request.setName(UUID.randomUUID().toString());
        request.setDescription("group service test");
        request.setGroupOwnerUserIds(List.of(1, 2, 3));
        Integer groupId = groupService.create(request);
        assertNotNull(groupId);

        List<UserRolePojo> roles = userRoleDao.selectByUserIds(List.of(1, 2, 3))
                .stream()
                .filter(r -> Objects.equals(r.getGroupId(), groupId) && r.getRole().equals("GROUP_OWNER"))
                .collect(Collectors.toList());
        assertEquals(3, roles.size());
    }

    @Test
    @Sql("classpath:sql/domain/group/GroupUpdate.sql")
    void update() {
        int groupId = -999;
        GroupUpdateRequest request = new GroupUpdateRequest();
        request.setId(groupId);
        request.setName(UUID.randomUUID().toString());
        request.setDescription(UUID.randomUUID().toString());
        request.setGroupOwnerUserIds(List.of(1000, 1001));
        groupService.update(request);

        List<UserRolePojo> members = userRoleDao.selectList(Tables.USER_ROLE.GROUP_ID.eq(groupId));
        assertEquals(3, members.size());
        List<Integer> ownerUserIds = members.stream()
                .filter(r -> r.getRole().equals("GROUP_OWNER"))
                .map(UserRolePojo::getUserId)
                .collect(Collectors.toList());
        assertEquals(2, ownerUserIds.size());
        assertTrue(ownerUserIds.contains(1000));
        assertTrue(ownerUserIds.contains(1001));
    }

    @Test
    @Sql("classpath:sql/domain/group/GroupDelete.sql")
    @SuppressWarnings("checkstyle:VariableDeclarationUsageDistance")
    void delete() {
        int groupId = -999;
        List<Integer> undeleteProjectIds = projectDao.selectProjectIdsByGroupId(groupId);
        groupService.delete(groupId);

        // should clear all projects
        List<Integer> projectIds = projectDao.selectProjectIdsByGroupId(groupId);
        assertEquals(0, projectIds.size());

        // should clear group members
        List<UserRolePojo> members = userRoleDao.selectList(Tables.USER_ROLE.GROUP_ID.eq(groupId));
        assertEquals(0, members.size());

        // should clear project sync schedule
        List<ProjectSyncRulePojo> syncRule = projectSyncRuleDao.selectInProjectIds(undeleteProjectIds);
        assertTrue(syncRule.stream().allMatch(r -> !r.getIsAutoSync()));
    }

    @Test
    @Sql("classpath:sql/domain/group/MemberRemove.sql")
    void removeMember() {
        int groupId = -999;
        // remove group member
        assertTrue(userRoleDao.hasRole(-1, groupId));
        groupService.removeMember(groupId, -1);
        assertFalse(userRoleDao.hasRole(-1, groupId));

        // remove group owner
        assertTrue(userRoleDao.hasRole(-2, groupId));
        groupService.removeMember(groupId, -2);
        assertFalse(userRoleDao.hasRole(-2, groupId));
        assertTrue(userRoleDao.hasRole(-2, -1000));
    }

    @Test
    @Sql("classpath:sql/domain/group/MemberAdd.sql")
    void addMember() {
        int groupId = -999;
        assertFalse(userRoleDao.hasRole(-1, groupId, RoleConstants.GROUP_MEMBER));

        GroupMemberCreateRequest request = new GroupMemberCreateRequest();
        request.setRole(RoleConstants.GROUP_MEMBER);
        request.setUserId(-1);
        groupService.addMember(groupId, request);

        assertTrue(userRoleDao.hasRole(-1, groupId, RoleConstants.GROUP_MEMBER));
    }

    @Test
    @Sql("classpath:sql/domain/group/MemberAdd.sql")
    void addMemberWhenWasGroupMember() {
        int groupId = -1000;
        GroupMemberCreateRequest request = new GroupMemberCreateRequest();
        request.setRole(RoleConstants.GROUP_MEMBER);
        request.setUserId(-2);
        var err = assertThrows(DatabasirException.class, () -> groupService.addMember(groupId, request));
        assertEquals(DomainErrors.USER_ROLE_DUPLICATE.getErrCode(), err.getErrCode());
    }

    @Test
    @Sql("classpath:sql/domain/group/MemberRoleChange.sql")
    void changeMemberRole() {
        int groupId = -999;
        int userId = -1;
        groupService.changeMemberRole(groupId, userId, RoleConstants.GROUP_MEMBER);
        assertTrue(userRoleDao.hasRole(userId, groupId, RoleConstants.GROUP_MEMBER));

        groupService.changeMemberRole(groupId, userId, RoleConstants.GROUP_OWNER);
        assertTrue(userRoleDao.hasRole(userId, groupId, RoleConstants.GROUP_OWNER));
        List<UserRolePojo> roles = userRoleDao.selectByUserIds(List.of(userId));
        assertEquals(1, roles.size());
    }
}
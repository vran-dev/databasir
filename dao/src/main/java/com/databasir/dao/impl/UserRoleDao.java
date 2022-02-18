package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.UserRolePojo;
import com.databasir.dao.value.GroupMemberSimplePojo;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static com.databasir.dao.Tables.USER;
import static com.databasir.dao.Tables.USER_ROLE;


@Repository
public class UserRoleDao extends BaseDao<UserRolePojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public UserRoleDao() {
        super(USER_ROLE, UserRolePojo.class);
    }

    public List<GroupMemberSimplePojo> selectOwnerNamesByGroupIdIn(List<Integer> groupIdList) {
        if (groupIdList == null || groupIdList.isEmpty()) {
            return Collections.emptyList();
        }
        return dslContext.select(USER.NICKNAME, USER_ROLE.GROUP_ID, USER_ROLE.ROLE).from(USER)
                .innerJoin(USER_ROLE).on(USER.ID.eq(USER_ROLE.USER_ID))
                .where(USER_ROLE.GROUP_ID.in(groupIdList)).and(USER_ROLE.ROLE.eq("GROUP_OWNER"))
                .fetchInto(GroupMemberSimplePojo.class);
    }

    public void deleteByUserIdAndGroupId(Integer userId, Integer groupId) {
        dslContext
                .deleteFrom(USER_ROLE).where(USER_ROLE.USER_ID.eq(userId).and(USER_ROLE.GROUP_ID.eq(groupId)))
                .execute();
    }

    public Page<UserRolePojo> selectPageByGroupId(Pageable pageable, Integer groupId) {
        return super.selectByPage(pageable, USER_ROLE.GROUP_ID.eq(groupId));
    }

    public void deleteByRoleAndGroupId(String role, Integer groupId) {
        dslContext
                .deleteFrom(USER_ROLE).where(USER_ROLE.GROUP_ID.eq(groupId).and(USER_ROLE.ROLE.eq(role)))
                .execute();
    }

    public void deleteByGroupId(Integer groupId) {
        dslContext
                .deleteFrom(USER_ROLE).where(USER_ROLE.GROUP_ID.eq(groupId))
                .execute();
    }

    public void deleteRole(Integer userId, String role) {
        dslContext
                .deleteFrom(USER_ROLE).where(USER_ROLE.USER_ID.eq(userId).and(USER_ROLE.ROLE.eq(role)))
                .execute();
    }

    public boolean hasRole(Integer userId, Integer groupId) {
        Condition condition = USER_ROLE.USER_ID.eq(userId)
                .and(USER_ROLE.GROUP_ID.eq(groupId));
        return dslContext.fetchExists(USER_ROLE, condition);
    }

    public boolean hasRole(Integer userId, String role) {
        Condition condition = USER_ROLE.USER_ID.eq(userId)
                .and(USER_ROLE.ROLE.eq(role));
        return dslContext.fetchExists(USER_ROLE, condition);
    }

    public boolean hasRole(Integer userId, Integer groupId, String role) {
        Condition condition = USER_ROLE.USER_ID.eq(userId)
                .and(USER_ROLE.GROUP_ID.eq(groupId))
                .and(USER_ROLE.ROLE.eq(role));
        return dslContext.fetchExists(USER_ROLE, condition);
    }

    public List<UserRolePojo> selectByUserIds(List<Integer> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyList();
        }
        return dslContext
                .select(USER_ROLE.fields()).from(USER_ROLE).where(USER_ROLE.USER_ID.in(userIds))
                .fetchInto(UserRolePojo.class);
    }
}

package com.databasir.dao.impl;

import com.databasir.dao.Databasir;
import com.databasir.dao.tables.pojos.UserPojo;
import com.databasir.dao.value.GroupMemberDetailPojo;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.databasir.dao.Tables.USER;
import static com.databasir.dao.Tables.USER_ROLE;


@Repository
public class UserDao extends BaseDao<UserPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public UserDao() {
        super(USER, UserPojo.class);
    }

    public void updateEnabledByUserId(Integer userId, Boolean enabled) {
        dslContext
                .update(USER).set(USER.ENABLED, enabled).where(USER.ID.eq(userId))
                .execute();
    }

    public void updatePassword(Integer userId, String password) {
        dslContext
                .update(USER).set(USER.PASSWORD, password).where(USER.ID.eq(userId))
                .execute();
    }

    public List<UserPojo> selectUserIdIn(Collection<Integer> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Collections.emptyList();
        }
        return dslContext
                .select(USER.fields()).from(USER)
                .where(USER.ID.in(userIds))
                .fetchInto(UserPojo.class);
    }

    public List<UserPojo> selectLimitUsersByRoleAndGroup(Integer groupId,
                                                         String role,
                                                         Integer size) {
        return dslContext
                .select(USER.fields()).from(USER)
                .innerJoin(USER_ROLE).on(USER_ROLE.USER_ID.eq(USER.ID))
                .where(USER_ROLE.GROUP_ID.eq(groupId).and(USER_ROLE.ROLE.eq(role)))
                .orderBy(USER_ROLE.ID.desc())
                .limit(size)
                .fetchInto(UserPojo.class);
    }

    public Optional<UserPojo> selectByEmail(String email) {
        return dslContext
                .select(USER.fields()).from(USER).where(USER.EMAIL.eq(email))
                .fetchOptionalInto(UserPojo.class);
    }

    public Optional<UserPojo> selectByEmailOrUsername(String emailOrUsername) {
        return dslContext
                .select(USER.fields()).from(USER)
                .where(USER.EMAIL.eq(emailOrUsername).or(USER.USERNAME.eq(emailOrUsername)))
                .fetchOptionalInto(UserPojo.class);
    }

    public Page<GroupMemberDetailPojo> selectGroupMembers(Integer groupId, Pageable request, Condition condition) {
        // total
        Integer count = dslContext
                .selectCount()
                .from(USER)
                .innerJoin(USER_ROLE).on(USER.ID.eq(USER_ROLE.USER_ID))
                .where(USER_ROLE.GROUP_ID.eq(groupId).and(condition))
                .fetchOne(0, int.class);

        // data
        List<GroupMemberDetailPojo> content = dslContext
                .select(USER.NICKNAME, USER.EMAIL, USER.USERNAME, USER.AVATAR, USER.ENABLED,
                        USER_ROLE.USER_ID, USER_ROLE.ROLE, USER_ROLE.GROUP_ID, USER_ROLE.CREATE_AT)
                .from(USER)
                .innerJoin(USER_ROLE).on(USER.ID.eq(USER_ROLE.USER_ID))
                .where(USER_ROLE.GROUP_ID.eq(groupId).and(condition))
                .orderBy(getSortFields(request.getSort()))
                .offset(request.getOffset()).limit(request.getPageSize())
                .fetchInto(GroupMemberDetailPojo.class);
        return new PageImpl<>(content, request, count);
    }

    protected Collection<SortField<?>> getSortFields(Sort sortSpecification) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();
        if (sortSpecification == null) {
            return querySortFields;
        }
        Iterator<Sort.Order> specifiedFields = sortSpecification.iterator();
        while (specifiedFields.hasNext()) {
            Sort.Order specifiedField = specifiedFields.next();
            String[] sortFields = specifiedField.getProperty().split("\\.");
            String fieldName;
            String tableName;
            if (sortFields.length == 1) {
                tableName = table().getName();
                fieldName = sortFields[0];
            } else {
                tableName = sortFields[0];
                fieldName = sortFields[1];
            }
            TableField tableField = tableField(tableName, fieldName);
            querySortFields.add(sortField(specifiedField, tableField));
        }
        return querySortFields;
    }

    private TableField tableField(String tableName, String fieldName) {
        return (TableField) Databasir.DATABASIR.getTable(tableName).field(fieldName);
    }

    private SortField<?> sortField(Sort.Order specifiedField, TableField tableField) {
        if (specifiedField.getDirection() == Sort.Direction.ASC) {
            return tableField.asc();
        } else {
            return tableField.desc();
        }
    }
}

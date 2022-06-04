package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.UserFavoriteProject;
import lombok.Getter;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static com.databasir.dao.Tables.*;

@Repository
public class UserFavoriteProjectDao extends BaseDao<UserFavoriteProject> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public UserFavoriteProjectDao() {
        super(USER_FAVORITE_PROJECT, UserFavoriteProject.class);
    }

    public boolean exists(Integer userId, Integer projectId) {
        return exists(USER_FAVORITE_PROJECT.USER_ID.eq(userId)
                .and(USER_FAVORITE_PROJECT.PROJECT_ID.eq(projectId)));
    }

    public void insert(Integer userId, Integer projectId) {
        UserFavoriteProject pojo = new UserFavoriteProject();
        pojo.setUserId(userId);
        pojo.setProjectId(projectId);
        this.insertAndReturnId(pojo);
    }

    public void delete(Integer userId, Integer projectId) {
        this.delete(USER_FAVORITE_PROJECT.USER_ID.eq(userId)
                .and(USER_FAVORITE_PROJECT.PROJECT_ID.eq(projectId)));
    }

    public Page<UserFavoriteProject> selectByCondition(Pageable request, Condition condition) {
        int total = getDslContext()
                .selectCount().from(USER_FAVORITE_PROJECT)
                .innerJoin(USER).on(USER.ID.eq(USER_FAVORITE_PROJECT.USER_ID))
                .innerJoin(PROJECT).on(PROJECT.ID.eq(USER_FAVORITE_PROJECT.PROJECT_ID))
                .where(PROJECT.DELETED.eq(false).and(condition))
                .fetchOne(0, int.class);
        List<UserFavoriteProject> data = getDslContext()
                .select(USER_FAVORITE_PROJECT.fields()).from(USER_FAVORITE_PROJECT)
                .innerJoin(USER).on(USER.ID.eq(USER_FAVORITE_PROJECT.USER_ID))
                .innerJoin(PROJECT).on(PROJECT.ID.eq(USER_FAVORITE_PROJECT.PROJECT_ID))
                .where(PROJECT.DELETED.eq(false).and(condition))
                .orderBy(getSortFields(request.getSort()))
                .offset(request.getOffset()).limit(request.getPageSize())
                .fetchInto(UserFavoriteProject.class);
        return new PageImpl<>(data, request, total);
    }

    public List<UserFavoriteProject> selectByUserIdAndProjectIds(Integer userId, List<Integer> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return Collections.emptyList();
        }
        return this.getDslContext()
                .select(USER_FAVORITE_PROJECT.fields()).from(USER_FAVORITE_PROJECT)
                .where(USER_FAVORITE_PROJECT.USER_ID.eq(userId).and(USER_FAVORITE_PROJECT.PROJECT_ID.in(projectIds)))
                .fetchInto(UserFavoriteProject.class);
    }
}

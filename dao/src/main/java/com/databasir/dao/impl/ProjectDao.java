package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.ProjectPojo;
import com.databasir.dao.tables.records.ProjectRecord;
import com.databasir.dao.value.GroupProjectCountPojo;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static com.databasir.dao.Tables.DATA_SOURCE;
import static com.databasir.dao.Tables.PROJECT;


@Repository
public class ProjectDao extends BaseDao<ProjectRecord, ProjectPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public ProjectDao() {
        super(PROJECT, ProjectPojo.class);
    }

    public int updateDeletedById(boolean b, Integer databaseId) {
        return dslContext
                .update(PROJECT).set(PROJECT.DELETED, b).where(PROJECT.ID.eq(databaseId))
                .execute();
    }

    @Override
    public Optional<ProjectPojo> selectOptionalById(Integer id) {
        return getDslContext()
                .select(PROJECT.fields()).from(PROJECT)
                .where(identity().eq(id).and(PROJECT.DELETED.eq(false)))
                .fetchOptionalInto(ProjectPojo.class);
    }

    @Override
    public boolean existsById(Integer id) {
        return getDslContext().fetchExists(table(), identity().eq(id).and(PROJECT.DELETED.eq(false)));
    }

    public boolean exists(Integer groupId, Integer projectId) {
        return getDslContext()
                .fetchExists(table(), identity().eq(projectId)
                        .and(PROJECT.GROUP_ID.eq(groupId))
                        .and(PROJECT.DELETED.eq(false)));
    }

    public Page<ProjectPojo> selectByCondition(Pageable request, Condition condition) {
        int total = getDslContext()
                .selectCount().from(PROJECT)
                .innerJoin(DATA_SOURCE).on(DATA_SOURCE.PROJECT_ID.eq(PROJECT.ID))
                .where(condition)
                .fetchOne(0, int.class);
        List<ProjectPojo> data = getDslContext()
                .select(PROJECT.fields()).from(PROJECT)
                .innerJoin(DATA_SOURCE).on(DATA_SOURCE.PROJECT_ID.eq(PROJECT.ID))
                .where(condition)
                .orderBy(getSortFields(request.getSort()))
                .offset(request.getOffset()).limit(request.getPageSize())
                .fetchInto(ProjectPojo.class);
        return new PageImpl<>(data, request, total);
    }

    public List<GroupProjectCountPojo> selectCountByGroupIds(List<Integer> groupIds) {
        if (groupIds == null || groupIds.isEmpty()) {
            return Collections.emptyList();
        }
        String groupIdIn = groupIds.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",", "(", ")"));
        String sql = "select `group`.id group_id, count(project.id) as count from project "
                + " inner join `group` on `group`.id = project.group_id "
                + " where project.deleted = false and `group`.id in " + groupIdIn + " group by `group`.id;";
        return dslContext.resultQuery(sql).fetchInto(GroupProjectCountPojo.class);
    }
}

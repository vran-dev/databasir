package com.databasir.dao.impl;

import com.databasir.dao.enums.ProjectSyncTaskStatus;
import com.databasir.dao.tables.pojos.ProjectSyncTaskPojo;
import com.databasir.dao.tables.records.ProjectSyncTaskRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.jooq.UpdateSetMoreStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static com.databasir.dao.Tables.PROJECT_SYNC_TASK;

@Repository
public class ProjectSyncTaskDao extends BaseDao<ProjectSyncTaskPojo> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public ProjectSyncTaskDao() {
        super(PROJECT_SYNC_TASK, ProjectSyncTaskPojo.class);
    }

    public boolean existsByProjectId(Integer projectId, Collection<ProjectSyncTaskStatus> statusIn) {
        if (statusIn == null || statusIn.isEmpty()) {
            throw new IllegalArgumentException("statusIn must not be empty");
        }
        return dslContext.fetchExists(PROJECT_SYNC_TASK,
                PROJECT_SYNC_TASK.PROJECT_ID.eq(projectId).and(PROJECT_SYNC_TASK.STATUS.in(statusIn)));
    }

    public List<ProjectSyncTaskPojo> listNewTasks(Integer size) {
        return dslContext
                .selectFrom(PROJECT_SYNC_TASK)
                .where(PROJECT_SYNC_TASK.STATUS.eq(ProjectSyncTaskStatus.NEW))
                .orderBy(PROJECT_SYNC_TASK.ID.asc())
                .limit(size)
                .fetchInto(ProjectSyncTaskPojo.class);
    }

    public int updateStatusAndResultById(Integer taskId, ProjectSyncTaskStatus status, String result) {
        UpdateSetMoreStep<ProjectSyncTaskRecord> updateStep = dslContext
                .update(PROJECT_SYNC_TASK)
                .set(PROJECT_SYNC_TASK.STATUS, status)
                .set(PROJECT_SYNC_TASK.RESULT, result);
        if (status == ProjectSyncTaskStatus.RUNNING) {
            updateStep = updateStep.set(PROJECT_SYNC_TASK.RUN_AT, LocalDateTime.now());
        }
        return updateStep
                .where(PROJECT_SYNC_TASK.ID.eq(taskId))
                .execute();
    }

}
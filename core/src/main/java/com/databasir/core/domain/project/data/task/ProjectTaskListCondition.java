package com.databasir.core.domain.project.data.task;

import com.databasir.dao.Tables;
import com.databasir.dao.enums.ProjectSyncTaskStatus;
import lombok.Data;
import org.jooq.Condition;

import java.util.Collection;
import java.util.Collections;

@Data
public class ProjectTaskListCondition {

    private Collection<Integer> taskIdIn = Collections.emptyList();

    private Collection<ProjectSyncTaskStatus> taskStatusIn = Collections.emptyList();

    public Condition toCondition(Integer projectId) {
        Condition condition = Tables.PROJECT_SYNC_TASK.PROJECT_ID.eq(projectId);
        if (taskIdIn != null && !taskIdIn.isEmpty()) {
            condition = condition.and(Tables.PROJECT_SYNC_TASK.ID.in(taskIdIn));
        }
        if (taskStatusIn != null && !taskStatusIn.isEmpty()) {
            condition = condition.and(Tables.PROJECT_SYNC_TASK.STATUS.in(taskStatusIn));
        }
        // ignore system user task
        return condition.and(Tables.PROJECT_SYNC_TASK.USER_ID.ne(-1));
    }
}

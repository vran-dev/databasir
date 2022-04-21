package com.databasir.core.domain.project.data.task;

import com.databasir.dao.enums.ProjectSyncTaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectSimpleTaskResponse {

    private Integer taskId;

    private ProjectSyncTaskStatus status;

    private String result;

    private LocalDateTime runAt;
}

package com.databasir.job;

import com.databasir.common.JsonData;
import com.databasir.core.domain.document.service.DocumentSyncService;
import com.databasir.core.domain.log.data.OperationLogRequest;
import com.databasir.core.domain.log.service.OperationLogService;
import com.databasir.dao.enums.ProjectSyncTaskStatus;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.ProjectSyncTaskDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.ProjectSyncTask;
import com.databasir.dao.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectSyncTaskScheduler {

    private final DocumentSyncService documentSyncService;

    private final OperationLogService operationLogService;

    private final UserDao userDao;

    private final ProjectSyncTaskDao projectSyncTaskDao;

    private final ProjectDao projectDao;

    private final ThreadPoolTaskExecutor projectSyncTaskThreadPoolTaskExecutor;

    /**
     * 每隔 5s 执行一次
     */
    @Scheduled(fixedRate = 5000L)
    public void startSyncTask() {
        final int size = 10;
        List<ProjectSyncTask> tasks = projectSyncTaskDao.listNewTasks(size);
        List<Integer> projectIds = tasks.stream()
                .map(ProjectSyncTask::getProjectId)
                .distinct()
                .collect(Collectors.toList());
        Map<Integer, Integer> groupIdAndProjectIdMap = projectDao.selectGroupIdsByProjectIdIn(projectIds);
        tasks.forEach(task -> {
            projectSyncTaskThreadPoolTaskExecutor.execute(() -> {
                Integer taskId = task.getId();
                Integer projectId = task.getProjectId();
                Integer groupId = groupIdAndProjectIdMap.get(projectId);
                Integer userId = task.getUserId();
                sync(taskId, groupId, projectId, userId);
            });
        });
    }

    private void sync(Integer taskId, Integer groupId, Integer projectId, Integer userId) {
        try {
            updateSyncTaskStatus(taskId, ProjectSyncTaskStatus.RUNNING, "running");
            documentSyncService.syncByProjectId(projectId);
            updateSyncTaskStatus(taskId, ProjectSyncTaskStatus.FINISHED, "ok");
            saveOperationLog(groupId, projectId, userId, null);
        } catch (Exception e) {
            String result = Objects.requireNonNullElse(e.getMessage(), "unknown");
            updateSyncTaskStatus(taskId, ProjectSyncTaskStatus.FAILED, result);
            saveOperationLog(groupId, projectId, userId, e);
            throw e;
        }
    }

    private void updateSyncTaskStatus(Integer taskId, ProjectSyncTaskStatus status, String result) {
        projectSyncTaskDao.updateStatusAndResultById(taskId, status, result);
    }

    private void saveOperationLog(Integer groupId, Integer projectId, Integer userId, Exception ex) {
        String operatorNickName;
        String operatorUsername;
        String operationName;
        if (Objects.equals(-1, userId)) {
            operatorNickName = "system";
            operatorUsername = "system";
            operationName = "定时同步";
        } else {
            User user = userDao.selectById(userId);
            operatorNickName = user.getNickname();
            operatorUsername = user.getUsername();
            operationName = "手动同步";
        }
        JsonData response;
        if (ex == null) {
            response = JsonData.ok();
        } else {
            response = JsonData.error("-1", ex.getMessage());
        }
        OperationLogRequest operationLog = OperationLogRequest.builder()
                .operatorNickname(operatorNickName)
                .operatorUsername(operatorUsername)
                .operatorUserId(userId)
                .operationName(operationName)
                .operationCode("autoSyncDocumentation")
                .operationModule("project")
                .operationResponse(response)
                .isSuccess(ex == null)
                .involvedProjectId(projectId)
                .involvedGroupId(groupId)
                .build();
        operationLogService.save(operationLog);
    }
}

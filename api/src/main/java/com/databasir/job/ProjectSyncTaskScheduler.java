package com.databasir.job;

import com.databasir.common.JsonData;
import com.databasir.core.domain.document.service.DocumentService;
import com.databasir.core.domain.log.data.OperationLogRequest;
import com.databasir.core.domain.log.service.OperationLogService;
import com.databasir.dao.enums.ProjectSyncTaskStatus;
import com.databasir.dao.impl.ProjectSyncTaskDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.UserPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectSyncTaskScheduler {

    private final DocumentService documentService;

    private final OperationLogService operationLogService;

    private final UserDao userDao;

    private final ProjectSyncTaskDao projectSyncTaskDao;

    private final ThreadPoolTaskExecutor projectSyncTaskThreadPoolTaskExecutor;

    /**
     * 每隔 5s 执行一次
     */
    @Scheduled(fixedRate = 5000L)
    public void startSyncTask() {
        final int size = 10;
        projectSyncTaskDao.listNewTasks(size).forEach(task -> {
            projectSyncTaskThreadPoolTaskExecutor.execute(() -> {
                Integer taskId = task.getId();
                Integer projectId = task.getProjectId();
                Integer userId = task.getUserId();
                sync(taskId, projectId, userId);
            });
        });
    }

    private void sync(Integer taskId, Integer projectId, Integer userId) {
        try {
            updateSyncTaskStatus(taskId, ProjectSyncTaskStatus.RUNNING, "running");
            documentService.syncByProjectId(projectId);
            updateSyncTaskStatus(taskId, ProjectSyncTaskStatus.FINISHED, "ok");
            saveOperationLog(projectId, userId, null);
        } catch (Exception e) {
            String result = Objects.requireNonNullElse(e.getMessage(), "unknown");
            updateSyncTaskStatus(taskId, ProjectSyncTaskStatus.FAILED, result);
            saveOperationLog(projectId, userId, e);
            throw e;
        }
    }

    private void updateSyncTaskStatus(Integer taskId, ProjectSyncTaskStatus status, String result) {
        projectSyncTaskDao.updateStatusAndResultById(taskId, status, result);
    }

    private void saveOperationLog(Integer projectId, Integer userId, Exception ex) {
        String operatorNickName;
        String operatorUsername;
        String operationName;
        if (Objects.equals(-1, userId)) {
            operatorNickName = "system";
            operatorUsername = "system";
            operationName = "文档定时同步";
        } else {
            UserPojo user = userDao.selectById(userId);
            operatorNickName = user.getNickname();
            operatorUsername = user.getUsername();
            operationName = "文档手动同步";
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
                .build();
        operationLogService.save(operationLog);
    }
}

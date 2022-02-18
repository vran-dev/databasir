package com.databasir.job;

import com.databasir.common.JsonData;
import com.databasir.core.domain.document.service.DocumentService;
import com.databasir.core.domain.log.data.OperationLogRequest;
import com.databasir.core.domain.log.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@RequiredArgsConstructor
@Slf4j
public class ProjectDocumentAutoSyncJob implements Job {

    private final OperationLogService operationLogService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        log.info("start sync project document: " + dataMap.toString());
        DocumentService documentService = (DocumentService) dataMap.get("documentService");
        Integer projectId = dataMap.getInt("projectId");

        try {
            documentService.syncByProjectId(projectId);
            OperationLogRequest request = OperationLogRequest.builder()
                    .isSuccess(true)
                    .operatorNickname("system")
                    .operatorUsername("system")
                    .operatorUserId(-1)
                    .operationName("文档自动同步")
                    .operationCode("autoSyncDocumentation")
                    .operationModule("project")
                    .operationResponse(JsonData.ok())
                    .isSuccess(true)
                    .involvedProjectId(projectId)
                    .build();
            operationLogService.save(request);
            log.info("sync project document {} over....", projectId);
        } catch (Exception e) {
            OperationLogRequest request = OperationLogRequest.builder()
                    .isSuccess(true)
                    .operatorNickname("system")
                    .operatorUsername("system")
                    .operatorUserId(-1)
                    .operationName("文档自动同步")
                    .operationCode("autoSyncDocumentation")
                    .operationModule("project")
                    .operationResponse(JsonData.error("-1", e.getMessage()))
                    .isSuccess(false)
                    .involvedProjectId(projectId)
                    .build();
            operationLogService.save(request);
            throw e;
        }
    }

}

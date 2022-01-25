package com.databasir.job;

import com.databasir.core.domain.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@RequiredArgsConstructor
@Slf4j
public class ProjectDocumentAutoSyncJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        log.info("start sync project document: " + dataMap.toString());
        DocumentService documentService = (DocumentService) dataMap.get("documentService");
        Integer projectId = dataMap.getInt("projectId");
        documentService.syncByProjectId(projectId);
        log.info("sync project document {} over....", projectId);
    }

}

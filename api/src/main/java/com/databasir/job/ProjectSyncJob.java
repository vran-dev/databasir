package com.databasir.job;

import com.databasir.core.domain.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@RequiredArgsConstructor
@Slf4j
public class ProjectSyncJob implements Job {

    private final ProjectService projectService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        Integer projectId = dataMap.getInt("projectId");
        projectService.createSyncTask(projectId, -1, true);
    }

}

package com.databasir.job;

import com.databasir.core.domain.document.service.DocumentService;
import com.databasir.dao.impl.ProjectSyncRuleDao;
import com.databasir.dao.tables.pojos.ProjectSyncRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用于启用 / 停用项目同步任务的调度器
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectSyncJobScheduler {

    private static final String JOB_IDENTITY_PATTERN = "JOB_PROJECT[%s]";

    private static final String TRIGGER_IDENTITY_PATTERN = "TRIGGER_PROJECT[%s]";

    private final Scheduler scheduler;

    private final ProjectSyncRuleDao projectSyncRuleDao;

    private final DocumentService documentService;

    /**
     * 启动 isAutoSync=true 的任务
     */
    @Scheduled(fixedRate = 1000)
    public void scheduleProjectSync() {
        List<Integer> currentlyTriggerProjectIds = getProjectJobDetails()
                .stream()
                .map(job -> job.getJobDataMap().getInt("projectId"))
                .collect(Collectors.toList());
        // 查询已开启自动同步但还未运行的任务
        projectSyncRuleDao.selectByIsAutoSyncAndNotInProjectIds(true, currentlyTriggerProjectIds)
                .stream()
                .map(this::jobAndTriggers)
                .forEach(jobAndTriggers -> {
                    try {
                        if (!scheduler.checkExists(jobAndTriggers.getSecond().getKey())) {
                            scheduler.scheduleJob(jobAndTriggers.getFirst(), jobAndTriggers.getSecond());
                            log.info("schedule jobs: {}", jobAndTriggers.getFirst());
                        }
                    } catch (SchedulerException e) {
                        log.error("failed to unScheduleProjectSync.scheduleJob ", e);
                    }
                });
    }

    /**
     * 停止 Rule.cron 更新过的任务
     */
    @Scheduled(fixedRate = 1000)
    public void reScheduleProjectSync() {
        List<TriggerKey> triggerKeys = getProjectJobDetails()
                .stream()
                .filter(jobDetail -> {
                    JobDataMap dataMap = jobDetail.getJobDataMap();
                    int projectId = dataMap.getInt("projectId");
                    String cron = dataMap.getString("cron");
                    return !projectSyncRuleDao.existsByProjectIdAndCron(projectId, cron);
                })
                .map(jobDetail -> jobDetail.getJobDataMap().getInt("projectId"))
                .map(this::triggerKey)
                .collect(Collectors.toList());
        try {
            if (!triggerKeys.isEmpty()) {
                scheduler.unscheduleJobs(triggerKeys);
                log.info("rescheduled jobs: {}", triggerKeys);
            }
        } catch (SchedulerException e) {
            log.error("failed to reScheduleProjectSync ", e);
        }
    }

    /**
     * 停止 isAutoSync=false 的项目
     */
    @Scheduled(fixedRate = 1000)
    public void unScheduleProjectSync() {
        List<Integer> projectIds = getProjectJobDetails()
                .stream()
                .map(job -> job.getJobDataMap().getInt("projectId"))
                .collect(Collectors.toList());
        // 查询已关闭自动同步但还在运行的任务
        List<TriggerKey> triggerKeys = projectSyncRuleDao.selectByIsAutoSyncAndProjectIds(false, projectIds)
                .stream()
                .map(p -> triggerKey(p.getProjectId()))
                .collect(Collectors.toList());
        try {
            if (!triggerKeys.isEmpty()) {
                scheduler.unscheduleJobs(triggerKeys);
                log.info("unscheduled jobs: {}", triggerKeys);
            }
        } catch (SchedulerException e) {
            log.error("failed to unScheduleProjectSync ", e);
        }
    }

    private List<JobDetail> getProjectJobDetails() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey -> {
                        try {
                            JobDetail job = scheduler.getJobDetail(jobKey);
                            if (job.getJobDataMap().containsKey("projectId")) {
                                return Optional.of(job);
                            } else {
                                return Optional.<JobDetail>empty();
                            }
                        } catch (SchedulerException e) {
                            log.error("getJobDetail error " + jobKey, e);
                            return Optional.<JobDetail>empty();
                        }
                    })
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (SchedulerException e) {
            log.error("get jobKeys error", e);
            return Collections.emptyList();
        }
    }

    private Pair<JobDetail, Trigger> jobAndTriggers(ProjectSyncRule rule) {
        JobDataMap dataMap = new JobDataMap();
        Integer projectId = rule.getProjectId();
        Integer ruleId = rule.getId();
        dataMap.put("projectId", projectId);
        dataMap.put("ruleId", ruleId);
        dataMap.put("cron", rule.getAutoSyncCron());
        dataMap.put("documentService", documentService);
        JobDetail job = JobBuilder.newJob()
                .ofType(ProjectSyncJob.class)
                .withIdentity(jobKey(projectId))
                .withDescription("auto sync project document")
                .usingJobData(dataMap)
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(rule.getAutoSyncCron());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey(projectId))
                .forJob(job)
                .withSchedule(scheduleBuilder)
                .startNow()
                .build();
        return Pair.of(job, trigger);
    }

    private TriggerKey triggerKey(Integer projectId) {
        return TriggerKey.triggerKey(String.format(TRIGGER_IDENTITY_PATTERN, projectId));
    }

    private JobKey jobKey(Integer projectId) {
        return JobKey.jobKey(String.format(JOB_IDENTITY_PATTERN, projectId));
    }
}

package com.databasir.api.validator;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.project.data.ProjectCreateRequest;
import com.databasir.core.domain.project.data.ProjectUpdateRequest;
import org.quartz.CronExpression;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class CronExpressionValidator {

    public void isValidCron(ProjectUpdateRequest request) {
        if (request.getProjectSyncRule().getIsAutoSync()) {
            isValidCron(request.getProjectSyncRule().getAutoSyncCron());
        }
    }

    public void isValidCron(ProjectCreateRequest request) {
        if (request.getProjectSyncRule().getIsAutoSync()) {
            isValidCron(request.getProjectSyncRule().getAutoSyncCron());
        }
    }

    public void isValidCron(String cron) {
        try {
            new CronExpression(cron);
        } catch (ParseException pe) {
            throw DomainErrors.INVALID_CRON_EXPRESSION.exception("错误的 CRON 表达式：" + pe.getMessage(), pe);
        }
    }
}

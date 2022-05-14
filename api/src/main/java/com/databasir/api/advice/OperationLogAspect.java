package com.databasir.api.advice;

import com.databasir.api.config.security.DatabasirUserDetails;
import com.databasir.common.JsonData;
import com.databasir.core.domain.log.annotation.AuditLog;
import com.databasir.core.domain.log.data.OperationLogRequest;
import com.databasir.core.domain.log.service.OperationLogService;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.tables.pojos.ProjectPojo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class OperationLogAspect {

    private final OperationLogService operationLogService;

    private final ProjectDao projectDao;

    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @AfterReturning(value = "@annotation(operation)", returning = "returnValue")
    public void log(JoinPoint joinPoint, Object returnValue, AuditLog operation) {
        if (returnValue instanceof JsonData) {
            saveLog(operation, joinPoint, (JsonData<Object>) returnValue);
        } else {
            saveLog(operation, joinPoint, JsonData.ok());
        }
    }

    @AfterThrowing(value = "@annotation(operation)", throwing = "ex")
    public void log(JoinPoint joinPoint, RuntimeException ex, AuditLog operation) {
        saveLog(operation, joinPoint, JsonData.error("-1", ex.getMessage()));
        throw ex;
    }

    private void saveLog(AuditLog operation, JoinPoint joinPoint, JsonData<Object> result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] arguments = joinPoint.getArgs();

        DatabasirUserDetails principal = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Integer involvedProjectId = getValueBySPEL(method, arguments, operation.involvedProjectId(), Integer.class)
                .orElse(null);
        Integer involvedGroupId = getValueBySPEL(method, arguments, operation.involvedGroupId(), Integer.class)
                .orElse(null);
        Integer involvedUserId = getValueBySPEL(method, arguments, operation.involvedUserId(), Integer.class)
                .orElse(null);
        // auto fill involvedProjectId
        if (involvedGroupId == null
                && operation.retrieveInvolvedGroupId()
                && involvedProjectId != null) {
            involvedGroupId = projectDao.selectOptionalById(involvedProjectId)
                    .map(ProjectPojo::getGroupId)
                    .orElse(null);
        }
        int userId = userId();
        String username = principal.getUserPojo().getUsername();
        String nickname = principal.getUserPojo().getNickname();
        if (userId == AuditLog.Types.SYSTEM_USER_ID) {
            username = "system";
            nickname = "system";
        }
        OperationLogRequest request = OperationLogRequest.builder()
                .operatorUserId(userId)
                .operatorUsername(username)
                .operatorNickname(nickname)
                .operationModule(operation.module())
                .operationCode(method.getName())
                .operationName(operation.name())
                .operationResponse(result)
                .isSuccess(result.getErrCode() == null)
                .involvedProjectId(involvedProjectId)
                .involvedGroupId(involvedGroupId)
                .involvedUserId(involvedUserId)
                .build();
        operationLogService.save(request);
    }

    private int userId() {
        DatabasirUserDetails principal = (DatabasirUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.getUserPojo().getId();
    }

    private <T> Optional<T> getValueBySPEL(Method method,
                                           Object[] arguments,
                                           String expression,
                                           Class<T> valueType) {
        if (expression == null || "N/A".equals(expression)) {
            return Optional.empty();
        }
        String[] parameterNames =
                Objects.requireNonNullElse(parameterNameDiscoverer.getParameterNames(method), new String[0]);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < parameterNames.length; len++) {
            context.setVariable(parameterNames[len], arguments[len]);
        }
        try {
            Expression expr = spelExpressionParser.parseExpression(expression);
            return Optional.ofNullable(expr.getValue(context, valueType));
        } catch (Exception e) {
            log.warn("parse expression error: " + expression, e);
            return Optional.empty();
        }
    }

}

package com.databasir.core.domain.log.service;

import com.databasir.common.JsonData;
import com.databasir.core.domain.log.annotation.AuditLog;
import com.databasir.core.domain.log.converter.OperationLogConverter;
import com.databasir.core.domain.log.converter.OperationLogRequestConverter;
import com.databasir.core.domain.log.data.OperationLogPageCondition;
import com.databasir.core.domain.log.data.OperationLogPageResponse;
import com.databasir.core.domain.log.data.OperationLogRequest;
import com.databasir.dao.impl.GroupDao;
import com.databasir.dao.impl.OperationLogDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.UserDao;
import com.databasir.dao.tables.pojos.Group;
import com.databasir.dao.tables.pojos.OperationLog;
import com.databasir.dao.tables.pojos.Project;
import com.databasir.dao.tables.pojos.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperationLogService {

    private final OperationLogDao operationLogDao;

    private final UserDao userDao;

    private final GroupDao groupDao;

    private final ProjectDao projectDao;

    private final OperationLogRequestConverter operationLogRequestConverter;

    private final OperationLogConverter operationLogConverter;

    public Long save(OperationLogRequest request) {
        OperationLog pojo = operationLogRequestConverter.toPojo(request);
        return operationLogDao.insertAndReturnId(pojo);
    }

    public void saveLoginFailedLog(String username, String msg) {
        saveLoginFailedLog(username, "登录", msg);
    }

    public void saveLoginFailedLog(String username, String name, String msg) {
        try {
            JsonData result = JsonData.error("-1", Objects.requireNonNullElse(msg, "登录失败"));
            OperationLogRequest log = OperationLogRequest.builder()
                    .isSuccess(false)
                    .operationCode("login")
                    .operationModule(AuditLog.Modules.LOGIN)
                    .operationName(name)
                    .operatorNickname(username)
                    .operatorUsername(username)
                    .operatorUserId(-1)
                    .operationResponse(result)
                    .build();
            OperationLog pojo = operationLogRequestConverter.toPojo(log);
            operationLogDao.insertAndReturnId(pojo);
        } catch (Exception e) {
            log.error("保存登录日志失败", e);
        }
    }

    public void saveLoginLog(User user, Boolean success, String msg) {
        this.saveLoginLog(user, success, "登录", msg);
    }

    public void saveLoginLog(User user, Boolean success, String name, String msg) {
        try {
            JsonData result;
            if (success) {
                result = JsonData.ok();
            } else {
                result = JsonData.error("-1", Objects.requireNonNullElse(msg, "登录失败"));
            }
            OperationLogRequest log = OperationLogRequest.builder()
                    .isSuccess(success)
                    .involvedUserId(user.getId())
                    .operationCode("login")
                    .operationModule(AuditLog.Modules.LOGIN)
                    .operationName(name)
                    .operatorNickname(user.getNickname())
                    .operatorUsername(user.getUsername())
                    .operatorUserId(user.getId())
                    .operationResponse(result)
                    .build();
            OperationLog pojo = operationLogRequestConverter.toPojo(log);
            operationLogDao.insertAndReturnId(pojo);
        } catch (Exception e) {
            log.error("保存登录日志失败", e);
        }
    }

    public Page<OperationLogPageResponse> list(Pageable page,
                                               OperationLogPageCondition condition) {
        Page<OperationLog> pojoData = operationLogDao.selectByPage(page, condition.toCondition());
        List<Integer> groupIds = pojoData.filter(p -> p.getInvolvedGroupId() != null)
                .map(OperationLog::getInvolvedGroupId)
                .toList();
        Map<Integer, Group> groupMapById = groupDao.selectAllInIds(groupIds)
                .stream()
                .collect(Collectors.toMap(Group::getId, Function.identity()));

        List<Integer> userIds = pojoData.filter(p -> p.getInvolvedUserId() != null)
                .map(OperationLog::getInvolvedUserId)
                .toList();
        Map<Integer, User> userMapById = userDao.selectInIds(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        List<Integer> projectIds = pojoData.filter(p -> p.getInvolvedProjectId() != null)
                .map(OperationLog::getInvolvedProjectId)
                .toList();
        Map<Integer, Project> projectMapById = projectDao.selectInIds(projectIds)
                .stream()
                .collect(Collectors.toMap(Project::getId, Function.identity()));
        return pojoData.map(pojo -> operationLogConverter.to(pojo, groupMapById, userMapById, projectMapById));
    }
}

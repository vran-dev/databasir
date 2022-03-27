package com.databasir.core.domain.log.service;

import com.databasir.common.JsonData;
import com.databasir.core.BaseTest;
import com.databasir.core.domain.log.data.OperationLogRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class OperationLogServiceTest extends BaseTest {

    @Autowired
    private OperationLogService operationLogService;

    @Test
    void save() {
        var request = OperationLogRequest.builder()
                .operatorUserId(-1000)
                .operatorUsername("ut")
                .operatorNickname("ut")
                .operationModule("system")
                .operationCode("updateEmail")
                .operationName("更新邮箱")
                .operationResponse(JsonData.ok())
                .isSuccess(true)
                .involvedProjectId(null)
                .involvedGroupId(null)
                .involvedUserId(null)
                .build();
        Long id = operationLogService.save(request);
        Assertions.assertNotNull(id);
    }
}
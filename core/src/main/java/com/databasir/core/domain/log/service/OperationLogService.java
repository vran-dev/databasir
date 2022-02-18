package com.databasir.core.domain.log.service;

import com.databasir.core.domain.log.converter.OperationLogPojoConverter;
import com.databasir.core.domain.log.converter.OperationLogRequestConverter;
import com.databasir.core.domain.log.data.OperationLogPageCondition;
import com.databasir.core.domain.log.data.OperationLogPageResponse;
import com.databasir.core.domain.log.data.OperationLogRequest;
import com.databasir.dao.impl.OperationLogDao;
import com.databasir.dao.tables.pojos.OperationLogPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationLogService {

    private final OperationLogDao operationLogDao;

    private final OperationLogRequestConverter operationLogRequestConverter;

    private final OperationLogPojoConverter operationLogPojoConverter;

    public void save(OperationLogRequest request) {
        OperationLogPojo pojo = operationLogRequestConverter.toPojo(request);
        operationLogDao.insertAndReturnId(pojo);
    }

    public Page<OperationLogPageResponse> list(Pageable page,
                                               OperationLogPageCondition condition) {
        Page<OperationLogPojo> pojoData = operationLogDao.selectByPage(page, condition.toCondition());
        return pojoData.map(operationLogPojoConverter::to);
    }
}

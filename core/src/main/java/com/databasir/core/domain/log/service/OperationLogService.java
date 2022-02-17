package com.databasir.core.domain.log.service;

import com.databasir.core.domain.log.converter.OperationLogRequestConverter;
import com.databasir.core.domain.log.data.OperationLogRequest;
import com.databasir.dao.impl.OperationLogDao;
import com.databasir.dao.tables.pojos.OperationLogPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationLogService {

    private final OperationLogDao operationLogDao;

    private final OperationLogRequestConverter operationLogRequestConverter;

    public void save(OperationLogRequest request) {
        OperationLogPojo pojo = operationLogRequestConverter.toPojo(request);
        operationLogDao.insertAndReturnId(pojo);
    }
}

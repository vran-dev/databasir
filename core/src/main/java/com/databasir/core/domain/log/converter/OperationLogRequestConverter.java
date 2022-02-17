package com.databasir.core.domain.log.converter;

import com.databasir.core.domain.log.data.OperationLogRequest;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.OperationLogPojo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface OperationLogRequestConverter {

    OperationLogPojo toPojo(OperationLogRequest request);

}

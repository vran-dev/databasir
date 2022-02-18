package com.databasir.core.domain.log.converter;

import com.databasir.core.domain.log.data.OperationLogPageResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.OperationLogPojo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface OperationLogPojoConverter {

    OperationLogPageResponse to(OperationLogPojo pojo);

}

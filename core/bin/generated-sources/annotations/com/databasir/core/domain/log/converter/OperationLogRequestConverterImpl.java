package com.databasir.core.domain.log.converter;

import com.databasir.core.domain.log.data.OperationLogRequest;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.OperationLog;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class OperationLogRequestConverterImpl implements OperationLogRequestConverter {

    @Autowired
    private JsonConverter jsonConverter;

    @Override
    public OperationLog toPojo(OperationLogRequest request) {
        if ( request == null ) {
            return null;
        }

        OperationLog operationLog = new OperationLog();

        operationLog.setOperatorUserId( request.getOperatorUserId() );
        operationLog.setOperatorUsername( request.getOperatorUsername() );
        operationLog.setOperatorNickname( request.getOperatorNickname() );
        operationLog.setOperationModule( request.getOperationModule() );
        operationLog.setOperationCode( request.getOperationCode() );
        operationLog.setOperationName( request.getOperationName() );
        operationLog.setOperationResponse( jsonConverter.objToJsonData( request.getOperationResponse() ) );
        operationLog.setIsSuccess( request.getIsSuccess() );
        operationLog.setInvolvedProjectId( request.getInvolvedProjectId() );
        operationLog.setInvolvedGroupId( request.getInvolvedGroupId() );
        operationLog.setInvolvedUserId( request.getInvolvedUserId() );

        return operationLog;
    }
}

package com.databasir.core.domain.log.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationLogRequest {

    private Integer operatorUserId;

    private String operatorUsername;

    private String operatorNickname;

    private String operationModule;

    private String operationCode;

    private String operationName;

    private Object operationResponse;

    private Boolean isSuccess;

    private Integer involvedProjectId;

    private Integer involvedGroupId;

    private Integer involvedUserId;

}

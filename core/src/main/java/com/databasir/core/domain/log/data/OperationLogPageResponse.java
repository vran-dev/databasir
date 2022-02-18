package com.databasir.core.domain.log.data;

import com.databasir.common.JsonData;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OperationLogPageResponse {

    private Integer id;

    private Integer operatorUserId;

    private String operatorUsername;

    private String operatorNickname;

    private String operationModule;

    private String operationCode;

    private String operationName;

    private JsonData<Object> operationResponse;

    private Boolean isSuccess;

    private Integer involvedProjectId;

    private Integer involvedGroupId;

    private Integer involvedUserId;

    private LocalDateTime createAt;

}

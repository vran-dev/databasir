package com.databasir.core.domain.log.data;

import com.databasir.common.JsonData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private InvolvedProjectData involvedProject;

    private InvolvedGroupData involvedGroup;

    private InvolvedUserData involvedUser;

    private LocalDateTime createAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvolvedProjectData {

        private Integer id;

        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvolvedGroupData {

        private Integer id;

        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvolvedUserData {

        private Integer id;

        private String email;

        private String nickname;
    }
}

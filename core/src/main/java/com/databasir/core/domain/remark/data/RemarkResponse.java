package com.databasir.core.domain.remark.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RemarkResponse {

    private Integer id;

    private Integer projectId;

    private String remark;

    private RemarkUser remarkBy;

    private LocalDateTime createAt;

    @Data
    public static class RemarkUser {

        private Integer userId;

        private String nickname;

        private String email;
    }

}

package com.databasir.core.domain.remark.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DiscussionResponse {

    private Integer id;

    private Integer projectId;

    private String content;

    private DiscussByUser discussBy;

    private LocalDateTime createAt;

    @Data
    public static class DiscussByUser {

        private Integer userId;

        private String nickname;

        private String email;
    }

}

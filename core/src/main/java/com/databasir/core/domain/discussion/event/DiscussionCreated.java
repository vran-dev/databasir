package com.databasir.core.domain.discussion.event;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class DiscussionCreated {

    private Integer discussionId;

    private Integer createByUserId;

    private String content;

    private Integer projectId;

    private String tableName;

    private String columnName;

    private LocalDateTime createAt;

    public Optional<String> getColumnName() {
        return Optional.of(columnName);
    }
}

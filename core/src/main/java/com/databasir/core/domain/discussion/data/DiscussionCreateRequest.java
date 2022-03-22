package com.databasir.core.domain.discussion.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DiscussionCreateRequest {

    @NotBlank
    private String content;

    @NotNull
    private String tableName;

    private String columnName;
}

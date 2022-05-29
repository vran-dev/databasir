package com.databasir.core.domain.description.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DescriptionUpdated {

    private Integer groupId;

    private Integer projectId;

    private Integer userId;

    private String tableName;

    private String columnName;

    private String description;
}

package com.databasir.core.domain.project.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectSaved {

    private Integer groupId;

    private Integer projectId;

    private String projectName;

    private String projectDescription;

    private String databaseType;

    private String databaseName;

    private String schemaName;

}

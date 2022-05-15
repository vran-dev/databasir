package com.databasir.dao.value;

import lombok.Data;

@Data
public class ProjectQueryPojo {

    private Integer projectId;

    private Integer groupId;

    private String groupName;

    private String projectName;

    private String projectDescription;

    private String databaseName;

    private String schemaName;
}

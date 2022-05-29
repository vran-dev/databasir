package com.databasir.dao.value;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FullTextProjectInfoUpdatePojo {

    private Integer projectId;

    private String projectName;

    private String projectDescription;

    private String databaseType;

    private String databaseName;

    private String schemaName;
}

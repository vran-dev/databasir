package com.databasir.core.domain.project.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectSimpleResponse {

    private Integer id;

    private String name;

    private String description;

    private String databaseName;

    private String databaseType;

    private Boolean isAutoSync;

    private String autoSyncCron;

    private LocalDateTime createAt;

}

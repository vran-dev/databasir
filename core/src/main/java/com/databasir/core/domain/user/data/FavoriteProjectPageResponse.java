package com.databasir.core.domain.user.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoriteProjectPageResponse {

    private Integer projectId;

    private String projectName;

    private String projectDescription;

    private Boolean isAutoSync;

    private String autoSyncCron;

    private Integer groupId;

    private String groupName;

    private String databaseType;

    private String databaseName;

    private LocalDateTime createAt;
}

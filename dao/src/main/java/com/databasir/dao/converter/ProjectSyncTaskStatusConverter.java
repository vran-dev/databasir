package com.databasir.dao.converter;

import com.databasir.dao.enums.ProjectSyncTaskStatus;
import org.jooq.impl.EnumConverter;

public class ProjectSyncTaskStatusConverter extends EnumConverter<String, ProjectSyncTaskStatus> {

    public ProjectSyncTaskStatusConverter() {
        super(String.class, ProjectSyncTaskStatus.class);
    }

}

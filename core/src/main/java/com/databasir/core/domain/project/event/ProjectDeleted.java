package com.databasir.core.domain.project.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDeleted {

    private Integer projectId;

}

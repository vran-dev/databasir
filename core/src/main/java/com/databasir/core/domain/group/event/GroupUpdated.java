package com.databasir.core.domain.group.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupUpdated {

    private Integer groupId;

    private String groupName;

    private String groupDescription;
}

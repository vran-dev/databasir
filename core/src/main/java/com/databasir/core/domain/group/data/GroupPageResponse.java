package com.databasir.core.domain.group.data;

import lombok.Data;

import java.util.List;

@Data
public class GroupPageResponse {

    private Integer id;

    private String name;

    private String description;

    private List<String> groupOwnerNames;

    private Integer projectCount;

}

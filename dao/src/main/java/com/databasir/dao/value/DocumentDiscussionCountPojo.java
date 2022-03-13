package com.databasir.dao.value;

import lombok.Data;

@Data
public class DocumentDiscussionCountPojo {

    private Integer projectId;

    private String tableName;

    private String columnName;

    private Integer count;
}

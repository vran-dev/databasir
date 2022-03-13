package com.databasir.core.domain.description.data;

import lombok.Data;

@Data
public class DocumentDescriptionSaveRequest {

    private String tableName;

    private String columnName;

    private String content;

}

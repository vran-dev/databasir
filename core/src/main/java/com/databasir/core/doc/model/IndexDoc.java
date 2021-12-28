package com.databasir.core.doc.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class IndexDoc {

    private String indexName;

    @Builder.Default
    private List<String> columnNames = Collections.emptyList();

    private Boolean isPrimaryKey;

    private Boolean isUniqueKey;
}

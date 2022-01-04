package com.databasir.core.meta.data;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class IndexMeta {

    private String name;

    @Builder.Default
    private List<String> columnNames = Collections.emptyList();

    private Boolean isPrimaryKey;

    private Boolean isUniqueKey;
}

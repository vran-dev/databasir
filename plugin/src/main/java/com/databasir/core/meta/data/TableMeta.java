package com.databasir.core.meta.data;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class TableMeta {

    private String name;

    private String type;

    private String comment;

    @Builder.Default
    private List<ColumnMeta> columns = Collections.emptyList();

    @Builder.Default
    private List<TriggerMeta> triggers = Collections.emptyList();

    @Builder.Default
    private List<IndexMeta> indexes = Collections.emptyList();

    private String remark;
}

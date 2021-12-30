package com.databasir.core.meta.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class TableMeta {

    private String tableName;

    private String tableType;

    private String tableComment;

    @Builder.Default
    private List<ColumnMeta> columns = Collections.emptyList();

    @Builder.Default
    private List<TriggerMeta> triggers = Collections.emptyList();

    @Builder.Default
    private List<IndexMeta> indexes = Collections.emptyList();

    private String remark;
}

package com.databasir.core.meta.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder.Default
    private List<ForeignKeyMeta> foreignKeys = Collections.emptyList();
}

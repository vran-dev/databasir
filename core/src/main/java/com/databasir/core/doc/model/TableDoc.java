package com.databasir.core.doc.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class TableDoc {

    private String tableName;

    private String tableType;

    private String tableComment;

    @Builder.Default
    private List<ColumnDoc> columns = Collections.emptyList();

    @Builder.Default
    private List<TriggerDoc> triggers = Collections.emptyList();

    @Builder.Default
    private List<IndexDoc> indexes = Collections.emptyList();

    private String remark;
}

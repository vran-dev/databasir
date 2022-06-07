package com.databasir.core.domain.document.data.diff;

import com.databasir.core.diff.data.DiffType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public class TableDocDiff implements DiffAble<TableDocDiff> {

    private Integer id;

    private String name;

    private String type;

    private String comment;

    @Builder.Default
    private List<ColumnDocDiff> columns = Collections.emptyList();

    @Builder.Default
    private List<IndexDocDiff> indexes = Collections.emptyList();

    @Builder.Default
    private List<TriggerDocDiff> triggers = Collections.emptyList();

    @Builder.Default
    private List<ForeignKeyDocDiff> foreignKeys = Collections.emptyList();

    private LocalDateTime createAt;

    private DiffType diffType;

    private TableDocDiff original;

}

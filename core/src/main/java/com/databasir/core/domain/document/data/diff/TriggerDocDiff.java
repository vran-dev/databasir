package com.databasir.core.domain.document.data.diff;

import com.databasir.core.diff.data.DiffType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TriggerDocDiff implements DiffAble<TriggerDocDiff> {

    private Integer id;

    private String name;

    private Integer tableDocumentId;

    private Integer databaseDocumentId;

    private String timing;

    private String manipulation;

    private String statement;

    private String triggerCreateAt;

    private LocalDateTime createAt;

    private DiffType diffType;

    private TriggerDocDiff original;
}

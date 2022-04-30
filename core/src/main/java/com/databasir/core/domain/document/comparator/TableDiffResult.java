package com.databasir.core.domain.document.comparator;

import com.databasir.core.diff.data.DiffType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@Data
@RequiredArgsConstructor
public class TableDiffResult {

    private final String id;

    private final DiffType diffType;

    private Collection<DiffResult> columnDiffResults = Collections.emptyList();

    private Collection<DiffResult> indexDiffResults = Collections.emptyList();

    private Collection<DiffResult> triggerDiffResults = Collections.emptyList();

    private Collection<DiffResult> foreignKeyDiffResults = Collections.emptyList();
}

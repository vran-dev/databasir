package com.databasir.core.domain.document.comparator;

import com.databasir.core.diff.data.DiffType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DiffResult {

    private final String id;

    private final DiffType diffType;

}

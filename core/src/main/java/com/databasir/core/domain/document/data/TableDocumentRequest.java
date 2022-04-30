package com.databasir.core.domain.document.data;

import lombok.Data;

import java.util.Collection;
import java.util.Collections;

@Data
public class TableDocumentRequest {

    private Collection<Integer> tableIds = Collections.emptyList();

    private Long originalVersion;

    private Long currentVersion;
}

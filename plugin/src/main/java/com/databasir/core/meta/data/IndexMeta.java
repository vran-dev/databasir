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
public class IndexMeta {

    private String name;

    @Builder.Default
    private List<String> columnNames = Collections.emptyList();

    private Boolean isUniqueKey;
}

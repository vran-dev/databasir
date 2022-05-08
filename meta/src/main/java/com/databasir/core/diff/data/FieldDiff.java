package com.databasir.core.diff.data;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class FieldDiff implements Diff {

    private String fieldName;

    private DiffType diffType;

    private Object original;

    private Object current;

    @Builder.Default
    private List<FieldDiff> fields = new ArrayList<>();

}

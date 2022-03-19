package com.databasir.core.diff.processor;

import com.databasir.core.diff.data.FieldDiff;
import com.databasir.core.meta.data.ColumnMeta;

import java.util.List;

public class ColumnDiffProcessor implements DiffProcessor<ColumnMeta> {

    @Override
    public FieldDiff process(String fieldName, List<ColumnMeta> original, List<ColumnMeta> current) {
        return diffTableField(original, current, fieldName, ColumnMeta::getName);
    }
}

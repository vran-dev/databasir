package com.databasir.core.diff.processor;

import com.databasir.core.diff.data.FieldDiff;
import com.databasir.core.meta.data.IndexMeta;

import java.util.List;

public class IndexDiffProcessor implements DiffProcessor<IndexMeta> {

    @Override
    public FieldDiff process(String fieldName, List<IndexMeta> original, List<IndexMeta> current) {
        return diffTableField(original, current, fieldName, IndexMeta::getName);
    }
}

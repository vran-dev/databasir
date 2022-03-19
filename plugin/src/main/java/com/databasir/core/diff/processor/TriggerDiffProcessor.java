package com.databasir.core.diff.processor;

import com.databasir.core.diff.data.FieldDiff;
import com.databasir.core.meta.data.TriggerMeta;

import java.util.List;

public class TriggerDiffProcessor implements DiffProcessor<TriggerMeta> {

    @Override
    public FieldDiff process(String fieldName, List<TriggerMeta> original, List<TriggerMeta> current) {
        return diffTableField(original, current, fieldName, TriggerMeta::getName);
    }
}

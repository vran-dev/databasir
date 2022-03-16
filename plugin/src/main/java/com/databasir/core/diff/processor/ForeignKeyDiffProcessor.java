package com.databasir.core.diff.processor;

import com.databasir.core.diff.data.FieldDiff;
import com.databasir.core.meta.data.ForeignKeyMeta;

import java.util.List;

public class ForeignKeyDiffProcessor implements DiffProcessor<ForeignKeyMeta> {

    @Override
    public FieldDiff process(String fieldName, List<ForeignKeyMeta> original, List<ForeignKeyMeta> current) {
        return diffTableField(
                original,
                current,
                "foreignKeys",
                fk -> {
                    if (fk.getFkName() == null) {
                        return fk.getFkTableName() + "." + fk.getFkColumnName() + "." + fk.getKeySql();
                    } else {
                        return fk.getFkName();
                    }
                });
    }
}

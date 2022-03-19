package com.databasir.core.diff.processor;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.FieldDiff;
import com.databasir.core.diff.data.RootDiff;
import com.databasir.core.meta.data.DatabaseMeta;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public class DatabaseDiffProcessor {

    private final TableDiffProcessor tableDiffProcessor = new TableDiffProcessor();

    private static final DatabaseMeta EMPTY = DatabaseMeta.builder().build();

    public RootDiff process(DatabaseMeta original, DatabaseMeta current) {
        DiffType diffType = null;
        if (original == null && current != null) {
            diffType = DiffType.ADDED;
        }
        if (original != null && current == null) {
            diffType = DiffType.REMOVED;
        }
        List<FieldDiff> fields = diffDatabaseFields(
                Objects.requireNonNullElse(original, EMPTY),
                Objects.requireNonNullElse(current, EMPTY)
        );
        boolean isModified = fields.stream().anyMatch(f -> DiffType.isModified(f.getDiffType()));
        if (diffType == null) {
            diffType = isModified ? DiffType.MODIFIED : DiffType.NONE;
        }
        RootDiff diff = new RootDiff();
        diff.setFields(fields);
        diff.setDiffType(diffType);
        return diff;
    }

    private List<FieldDiff> diffDatabaseFields(DatabaseMeta original, DatabaseMeta current) {
        Class<DatabaseMeta> clazz = DatabaseMeta.class;
        Field[] fields = clazz.getDeclaredFields();
        List<FieldDiff> diffs = new ArrayList<>(32);
        // ignore tables diff
        Arrays.stream(fields)
                .filter(field -> !Objects.equals(field.getName(), "tables"))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        Object originalValue = original == null ? null : field.get(original);
                        Object currentValue = current == null ? null : field.get(current);
                        if (!Objects.equals(originalValue, currentValue)) {
                            DiffType diffType;
                            if (originalValue == null) {
                                diffType = DiffType.ADDED;
                            } else if (currentValue == null) {
                                diffType = DiffType.REMOVED;
                            } else {
                                diffType = DiffType.MODIFIED;
                            }
                            diffs.add(FieldDiff.builder()
                                    .diffType(diffType)
                                    .fieldName(field.getName())
                                    .original(originalValue)
                                    .current(currentValue)
                                    .build());
                        }
                    } catch (IllegalAccessException e) {
                        log.error("diff field failed", e);
                    }
                });

        FieldDiff tablesField = tableDiffProcessor.process("tables", original.getTables(), current.getTables());
        diffs.add(tablesField);
        return diffs;
    }
}

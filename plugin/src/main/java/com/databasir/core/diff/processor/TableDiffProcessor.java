package com.databasir.core.diff.processor;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.FieldDiff;
import com.databasir.core.meta.data.TableMeta;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TableDiffProcessor implements DiffProcessor<TableMeta> {

    private final IndexDiffProcessor indexDiffProcessor = new IndexDiffProcessor();

    private final ColumnDiffProcessor columnDiffProcessor = new ColumnDiffProcessor();

    private final TriggerDiffProcessor triggerDiffProcessor = new TriggerDiffProcessor();

    private final ForeignKeyDiffProcessor foreignKeyDiffProcessor = new ForeignKeyDiffProcessor();

    @Override
    public FieldDiff process(String fieldName, List<TableMeta> original, List<TableMeta> current) {
        // diff tables field
        Map<String, TableMeta> originalMap = toMap(original, TableMeta::getName);
        Map<String, TableMeta> currentMap = toMap(current, TableMeta::getName);
        List<FieldDiff> tables = new ArrayList<>(32);
        // removed
        List<FieldDiff> removedFields = originalRemovedField(originalMap, currentMap);
        tables.addAll(removedFields);
        // added
        List<FieldDiff> addedFields = currentAddedField(originalMap, currentMap);
        tables.addAll(addedFields);
        // modified
        List<FieldDiff> modified = originalMap.entrySet()
                .stream()
                .filter(entry -> currentMap.containsKey(entry.getKey()))
                .filter(entry -> !Objects.equals(entry.getValue(), currentMap.get(entry.getKey())))
                .map(entry -> {
                    TableMeta originalValue = entry.getValue();
                    TableMeta currentValue = currentMap.get(entry.getKey());
                    return diffTableField(originalValue, currentValue);
                })
                .collect(Collectors.toList());
        tables.addAll(modified);
        DiffType tablesDiffType;
        if (!modified.isEmpty()) {
            tablesDiffType = DiffType.MODIFIED;
        } else if (!addedFields.isEmpty()) {
            tablesDiffType = DiffType.ADDED;
        } else if (!removedFields.isEmpty()) {
            tablesDiffType = DiffType.REMOVED;
        } else {
            tablesDiffType = DiffType.NONE;
        }
        FieldDiff tablesField = FieldDiff.builder()
                .diffType(tablesDiffType)
                .fieldName(fieldName)
                .fields(tables)
                .build();
        return tablesField;
    }

    private FieldDiff diffTableField(TableMeta original, TableMeta current) {
        FieldDiff columns =
                columnDiffProcessor.process("columns", original.getColumns(), current.getColumns());
        FieldDiff indexes =
                indexDiffProcessor.process("indexes", original.getIndexes(), current.getIndexes());
        FieldDiff triggers =
                triggerDiffProcessor.process("triggers", original.getTriggers(), current.getTriggers());
        FieldDiff foreignKeys =
                foreignKeyDiffProcessor.process("foreignKeys", original.getForeignKeys(), current.getForeignKeys());
        List<FieldDiff> otherFields = fields(original, current);

        List<FieldDiff> fields = new ArrayList<>();
        fields.add(columns);
        fields.add(indexes);
        fields.add(foreignKeys);
        fields.add(triggers);
        fields.addAll(otherFields);
        return FieldDiff.builder()
                .diffType(DiffType.MODIFIED)
                .fieldName(original.getName())
                .fields(fields)
                .build();
    }

    private List<FieldDiff> fields(TableMeta original, TableMeta current) {
        List<FieldDiff> fields = new ArrayList<>();
        // ignore tables diff
        Class<TableMeta> clazz = TableMeta.class;
        List<String> ignoredFields = List.of("columns", "indexes", "triggers", "foreignKeys");
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !ignoredFields.contains(field.getName()))
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
                            fields.add(FieldDiff.builder()
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
        return fields;
    }
}

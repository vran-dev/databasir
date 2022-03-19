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

    private static final TableMeta EMPTY = new TableMeta();

    @Override
    public FieldDiff process(String fieldName, List<TableMeta> original, List<TableMeta> current) {
        // diff tables field
        Map<String, TableMeta> originalMap = toMap(original, TableMeta::getName);
        Map<String, TableMeta> currentMap = toMap(current, TableMeta::getName);
        List<FieldDiff> tables = new ArrayList<>();
        List<TableMeta> added = added(originalMap, currentMap);
        List<TableMeta> removed = removed(originalMap, currentMap);
        // added
        List<FieldDiff> addedFields = added.stream()
                .map(table -> diffTableField(EMPTY, table))
                .collect(Collectors.toList());
        tables.addAll(addedFields);
        // removed
        List<FieldDiff> removedFields = removed.stream()
                .map(table -> diffTableField(table, EMPTY))
                .collect(Collectors.toList());
        tables.addAll(removedFields);
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

    private List<TableMeta> added(Map<String, TableMeta> originalMap,
                                  Map<String, TableMeta> currentMap) {
        return currentMap.entrySet()
                .stream()
                .filter(entry -> !originalMap.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private List<TableMeta> removed(Map<String, TableMeta> originalMap,
                                    Map<String, TableMeta> currentMap) {
        return originalMap.entrySet()
                .stream()
                .filter(entry -> !currentMap.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
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
        DiffType diffType;
        if (original == EMPTY) {
            diffType = DiffType.ADDED;
        } else if (current == EMPTY) {
            diffType = DiffType.REMOVED;
        } else {
            diffType = DiffType.MODIFIED;
        }
        return FieldDiff.builder()
                .diffType(diffType)
                .fieldName(original == EMPTY ? current.getName() : original.getName())
                .original(current == EMPTY ? original : null)
                .current(original == EMPTY ? current : null)
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

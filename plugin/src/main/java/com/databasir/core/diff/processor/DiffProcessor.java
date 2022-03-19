package com.databasir.core.diff.processor;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.diff.data.FieldDiff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface DiffProcessor<T> {

    FieldDiff process(String fieldName, List<T> original, List<T> current);

    default FieldDiff diffTableField(List<T> original,
                                     List<T> current,
                                     String fieldName,
                                     Function<T, String> identity) {
        Map<String, T> originalMap = toMap(original, identity);
        Map<String, T> currentMap = toMap(current, identity);
        List<FieldDiff> columnFieldDiffs = new ArrayList<>(32);
        // removed
        List<FieldDiff> removedFields = originalRemovedField(originalMap, currentMap);
        columnFieldDiffs.addAll(removedFields);
        // added
        List<FieldDiff> addedFields = currentAddedField(originalMap, currentMap);
        columnFieldDiffs.addAll(addedFields);
        // modified
        List<FieldDiff> modifiedFields = modifiedField(originalMap, currentMap);
        columnFieldDiffs.addAll(modifiedFields);
        return FieldDiff.builder()
                .fieldName(fieldName)
                .diffType(columnFieldDiffs.isEmpty() ? DiffType.NONE : DiffType.MODIFIED)
                .fields(columnFieldDiffs)
                .build();

    }

    default Map<String, T> toMap(List<T> content, Function<T, String> idMapping) {
        return content
                .stream()
                .collect(Collectors.toMap(idMapping, Function.identity()));
    }

    default List<FieldDiff> originalRemovedField(Map<String, T> originalMapById,
                                                 Map<String, T> currentMapById) {
        return originalMapById.entrySet()
                .stream()
                .filter(entry -> !currentMapById.containsKey(entry.getKey()))
                .map(entry -> FieldDiff.builder()
                        .fieldName(entry.getKey())
                        .original(entry.getValue())
                        .diffType(DiffType.REMOVED)
                        .build())
                .collect(Collectors.toList());
    }

    default List<FieldDiff> currentAddedField(Map<String, T> originalMapById,
                                              Map<String, T> currentMapById) {
        return currentMapById.entrySet()
                .stream()
                .filter(entry -> !originalMapById.containsKey(entry.getKey()))
                .map(entry -> FieldDiff.builder()
                        .fieldName(entry.getKey())
                        .current(entry.getValue())
                        .diffType(DiffType.ADDED)
                        .build())
                .collect(Collectors.toList());
    }

    default List<FieldDiff> modifiedField(Map<String, T> original,
                                          Map<String, T> current) {
        List<FieldDiff> diff = new ArrayList<>();
        original.entrySet()
                .stream()
                .filter(entry -> current.containsKey(entry.getKey()))
                .forEach(entry -> {
                    T originalValue = entry.getValue();
                    T currentValue = current.get(entry.getKey());
                    if (!Objects.equals(originalValue, currentValue)) {
                        FieldDiff fieldDiff = FieldDiff.builder()
                                .fieldName(entry.getKey())
                                .original(originalValue)
                                .current(currentValue)
                                .diffType(DiffType.MODIFIED)
                                .build();
                        diff.add(fieldDiff);
                    }
                });
        return diff;
    }
}

package com.databasir.core.domain.document.diff;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.domain.document.data.diff.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DocumentDiffChecker {

    private static final List<String> IGNORE_FIELDS = List.of(
            "id",
            "class",
            "tableDocumentId",
            "databaseDocumentId",
            "createAt",
            "updateAt",
            "diffType",
            "original"
    );

    public List<TableDocDiff> diff(List<TableDocDiff> original, List<TableDocDiff> current) {
        Map<String, TableDocDiff> originalTablesMap = toMap(original, TableDocDiff::getName);
        Map<String, TableDocDiff> currentTablesMap = toMap(current, TableDocDiff::getName);
        // added items
        var added = added(originalTablesMap, currentTablesMap)
                .stream()
                .map(curr -> {
                    List<ColumnDocDiff> columnDiffs =
                            doDiff(Collections.emptyList(), curr.getColumns(), ColumnDocDiff::getName);
                    columnDiffs.sort(Comparator.comparingInt(c -> c.getId()));
                    List<IndexDocDiff> indexDiffs =
                            doDiff(Collections.emptyList(), curr.getIndexes(), IndexDocDiff::getName);
                    List<TriggerDocDiff> triggerDiffs =
                            doDiff(Collections.emptyList(), curr.getTriggers(), TriggerDocDiff::getName);
                    List<ForeignKeyDocDiff> fkDiffs =
                            doDiff(Collections.emptyList(),
                                    curr.getForeignKeys(),
                                    fk -> fk.getFkTableName() + "." + fk.getFkColumnName() + "." + fk.getKeySeq());
                    return TableDocDiff.builder()
                            .id(curr.getId())
                            .diffType(DiffType.ADDED)
                            .name(curr.getName())
                            .comment(curr.getComment())
                            .type(curr.getType())
                            .createAt(curr.getCreateAt())
                            .columns(columnDiffs)
                            .indexes(indexDiffs)
                            .triggers(triggerDiffs)
                            .foreignKeys(fkDiffs)
                            .build();
                })
                .collect(Collectors.toList());
        // removed items
        var removed = removed(originalTablesMap, currentTablesMap)
                .stream()
                .map(old -> {
                    List<ColumnDocDiff> columnDiffs =
                            doDiff(old.getColumns(), Collections.emptyList(), ColumnDocDiff::getName);
                    columnDiffs.sort(Comparator.comparingInt(c -> c.getId()));
                    List<IndexDocDiff> indexDiffs =
                            doDiff(old.getIndexes(), Collections.emptyList(), IndexDocDiff::getName);
                    List<TriggerDocDiff> triggerDiffs =
                            doDiff(old.getTriggers(), Collections.emptyList(), TriggerDocDiff::getName);
                    List<ForeignKeyDocDiff> fkDiffs =
                            doDiff(old.getForeignKeys(),
                                    Collections.emptyList(),
                                    fk -> fk.getFkTableName() + "." + fk.getFkColumnName() + "." + fk.getKeySeq());
                    return TableDocDiff.builder()
                            .id(old.getId())
                            .diffType(DiffType.REMOVED)
                            .name(old.getName())
                            .comment(old.getComment())
                            .type(old.getType())
                            .createAt(old.getCreateAt())
                            .columns(columnDiffs)
                            .indexes(indexDiffs)
                            .triggers(triggerDiffs)
                            .foreignKeys(fkDiffs)
                            .build();
                })
                .collect(Collectors.toList());
        // unchanged or modified items
        List<TableDocDiff> sameOrModified = currentTablesMap.entrySet()
                .stream()
                .filter(entry -> originalTablesMap.containsKey(entry.getKey()))
                .map(entry -> {
                    String tableName = entry.getKey();
                    TableDocDiff currentTable = entry.getValue();
                    TableDocDiff originalTable = originalTablesMap.get(tableName);

                    List<ColumnDocDiff> columnDiffs =
                            doDiff(originalTable.getColumns(), currentTable.getColumns(), ColumnDocDiff::getName);
                    columnDiffs.sort(Comparator.comparingInt(c -> c.getId()));
                    List<IndexDocDiff> indexDiffs =
                            doDiff(originalTable.getIndexes(), currentTable.getIndexes(), IndexDocDiff::getName);
                    List<TriggerDocDiff> triggerDiffs =
                            doDiff(originalTable.getTriggers(), currentTable.getTriggers(), TriggerDocDiff::getName);
                    List<ForeignKeyDocDiff> fkDiffs =
                            doDiff(originalTable.getForeignKeys(),
                                    currentTable.getForeignKeys(),
                                    fk -> fk.getFkTableName() + "." + fk.getFkColumnName() + "." + fk.getKeySeq());

                    BaseTypeFieldEqualFunction eq = new BaseTypeFieldEqualFunction(IGNORE_FIELDS);
                    DiffType diffType = eq.apply(currentTable, originalTable) ? DiffType.NONE : DiffType.MODIFIED;
                    // workaround for diffType = NONE
                    if (diffType == DiffType.NONE) {
                        originalTable = null;
                    }
                    return TableDocDiff.builder()
                            .id(currentTable.getId())
                            .diffType(diffType)
                            .original(originalTable)
                            .name(currentTable.getName())
                            .comment(currentTable.getComment())
                            .type(currentTable.getType())
                            .createAt(currentTable.getCreateAt())
                            .columns(columnDiffs)
                            .indexes(indexDiffs)
                            .triggers(triggerDiffs)
                            .foreignKeys(fkDiffs)
                            .build();
                })
                .collect(Collectors.toList());

        List<TableDocDiff> all = new ArrayList<>(16);
        all.addAll(sameOrModified);
        all.addAll(added);
        all.addAll(removed);
        return all;
    }

    private <T extends DiffAble<T>> List<T> doDiff(List<T> original, List<T> current, Function<T, String> idMapping) {
        Map<String, T> originalMap = toMap(original, idMapping);
        Map<String, T> currentMap = toMap(current, idMapping);
        List<T> added = added(originalMap, currentMap);
        List<T> removed = removed(originalMap, currentMap);
        List<T> modified = modified(originalMap, currentMap);
        List<T> same = same(originalMap, currentMap);
        List<T> results = new ArrayList<>();
        results.addAll(same);
        results.addAll(added);
        results.addAll(modified);
        results.addAll(removed);
        return results;
    }

    private <T> Map<String, T> toMap(List<T> content, Function<T, String> idMapping) {
        return content
                .stream()
                .collect(Collectors.toMap(idMapping, Function.identity(), (a, b) -> {
                    log.warn("Duplicate key, origin = {}, current = {}", a, b);
                    return a;
                }));
    }

    private <T extends DiffAble<T>> List<T> added(Map<String, T> originalMapById,
                                                  Map<String, T> currentMapById) {
        return currentMapById.entrySet()
                .stream()
                .filter(entry -> !originalMapById.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .map(value -> {
                    value.setDiffType(DiffType.ADDED);
                    return value;
                })
                .collect(Collectors.toList());
    }

    private <T extends DiffAble<T>> List<T> removed(Map<String, T> originalMapById,
                                                    Map<String, T> currentMapById) {
        return originalMapById.entrySet()
                .stream()
                .filter(entry -> !currentMapById.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .map(value -> {
                    value.setDiffType(DiffType.REMOVED);
                    return value;
                })
                .collect(Collectors.toList());
    }

    private <T extends DiffAble<T>> List<T> modified(Map<String, T> originalMapById,
                                                     Map<String, T> currentMapById) {
        BaseTypeFieldEqualFunction eq = new BaseTypeFieldEqualFunction(IGNORE_FIELDS);
        return modified(originalMapById, currentMapById, eq);
    }

    private <T extends DiffAble<T>> List<T> modified(Map<String, T> originalMapById,
                                                     Map<String, T> currentMapById,
                                                     BiFunction<Object, Object, Boolean> sameFunction) {
        return currentMapById.entrySet()
                .stream()
                .filter(entry -> originalMapById.containsKey(entry.getKey()))
                .filter(entry -> !sameFunction.apply(entry.getValue(), originalMapById.get(entry.getKey())))
                .map(entry -> {
                    T value = entry.getValue();
                    value.setDiffType(DiffType.MODIFIED);
                    value.setOriginal(originalMapById.get(entry.getKey()));
                    return value;
                })
                .collect(Collectors.toList());
    }

    private <T extends DiffAble<T>> List<T> same(Map<String, T> originalMapById,
                                                 Map<String, T> currentMapById) {
        BaseTypeFieldEqualFunction eq = new BaseTypeFieldEqualFunction(IGNORE_FIELDS);
        return same(originalMapById, currentMapById, eq);
    }

    private <T extends DiffAble<T>> List<T> same(Map<String, T> originalMapById,
                                                 Map<String, T> currentMapById,
                                                 BiFunction<Object, Object, Boolean> sameFunction) {
        return currentMapById.entrySet()
                .stream()
                .filter(entry -> originalMapById.containsKey(entry.getKey()))
                .filter(entry -> sameFunction.apply(entry.getValue(), originalMapById.get(entry.getKey())))
                .map(entry -> {
                    T value = entry.getValue();
                    value.setDiffType(DiffType.NONE);
                    return value;
                })
                .collect(Collectors.toList());
    }
}

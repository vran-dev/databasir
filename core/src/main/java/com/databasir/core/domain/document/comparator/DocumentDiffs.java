package com.databasir.core.domain.document.comparator;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse.TableData;
import com.databasir.core.meta.data.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class DocumentDiffs {

    public static List<TableDiffResult> tableDiff(DatabaseMeta original, DatabaseMeta current) {
        return tableDiff(original.getTables(), current.getTables());
    }

    public static List<TableDiffResult> tableDiff(List<TableMeta> original, List<TableMeta> current) {
        Map<String, TableMeta> originalTablesMap = toMap(original, TableMeta::getName);
        Map<String, TableMeta> currentTablesMap = toMap(current, TableMeta::getName);
        var added = added(originalTablesMap, currentTablesMap)
                .stream()
                .map(curr -> {
                    TableDiffResult result = new TableDiffResult(curr.getName(), DiffType.ADDED);
                    List<DiffResult> columnDiffs =
                            doDiff(Collections.emptyList(), curr.getColumns(), ColumnMeta::getName);
                    List<DiffResult> indexDiffs =
                            doDiff(Collections.emptyList(), curr.getIndexes(), IndexMeta::getName);
                    List<DiffResult> triggerDiffs =
                            doDiff(Collections.emptyList(), curr.getTriggers(), TriggerMeta::getName);
                    List<DiffResult> fkDiffs =
                            doDiff(Collections.emptyList(),
                                    curr.getForeignKeys(),
                                    fk -> fk.getFkTableName() + "." + fk.getFkColumnName() + "." + fk.getKeySeq());
                    result.setColumnDiffResults(columnDiffs);
                    result.setIndexDiffResults(indexDiffs);
                    result.setTriggerDiffResults(triggerDiffs);
                    result.setForeignKeyDiffResults(fkDiffs);
                    return result;
                })
                .collect(Collectors.toList());
        var removed = removed(originalTablesMap, currentTablesMap)
                .stream()
                .map(old -> {
                    TableDiffResult result = new TableDiffResult(old.getName(), DiffType.REMOVED);
                    List<DiffResult> columnDiffs =
                            doDiff(old.getColumns(), Collections.emptyList(), ColumnMeta::getName);
                    List<DiffResult> indexDiffs =
                            doDiff(old.getIndexes(), Collections.emptyList(), IndexMeta::getName);
                    List<DiffResult> triggerDiffs =
                            doDiff(old.getTriggers(), Collections.emptyList(), TriggerMeta::getName);
                    List<DiffResult> fkDiffs =
                            doDiff(old.getForeignKeys(),
                                    Collections.emptyList(),
                                    fk -> fk.getFkTableName() + "." + fk.getFkColumnName() + "." + fk.getKeySeq());
                    result.setColumnDiffResults(columnDiffs);
                    result.setIndexDiffResults(indexDiffs);
                    result.setTriggerDiffResults(triggerDiffs);
                    result.setForeignKeyDiffResults(fkDiffs);
                    return result;
                })
                .collect(Collectors.toList());
        List<TableDiffResult> sameOrModified = currentTablesMap.entrySet()
                .stream()
                .filter(entry -> originalTablesMap.containsKey(entry.getKey()))
                .map(entry -> {
                    String tableName = entry.getKey();
                    TableMeta currentTable = entry.getValue();
                    TableMeta originalTable = originalTablesMap.get(tableName);

                    List<DiffResult> columnDiffs =
                            doDiff(originalTable.getColumns(), currentTable.getColumns(), ColumnMeta::getName);
                    List<DiffResult> indexDiffs =
                            doDiff(originalTable.getIndexes(), currentTable.getIndexes(), IndexMeta::getName);
                    List<DiffResult> triggerDiffs =
                            doDiff(originalTable.getTriggers(), currentTable.getTriggers(), TriggerMeta::getName);
                    List<DiffResult> fkDiffs =
                            doDiff(originalTable.getForeignKeys(),
                                    currentTable.getForeignKeys(),
                                    fk -> fk.getFkTableName() + "." + fk.getFkColumnName() + "." + fk.getKeySeq());

                    DiffType diffType = Objects.equals(currentTable, originalTable) ? DiffType.NONE : DiffType.MODIFIED;
                    TableDiffResult diffResult = new TableDiffResult(tableName, diffType);
                    diffResult.setColumnDiffResults(columnDiffs);
                    diffResult.setIndexDiffResults(indexDiffs);
                    diffResult.setTriggerDiffResults(triggerDiffs);
                    diffResult.setForeignKeyDiffResults(fkDiffs);
                    return diffResult;
                })
                .collect(Collectors.toList());

        List<TableDiffResult> all = new ArrayList<>(16);
        all.addAll(sameOrModified);
        all.addAll(added);
        all.addAll(removed);
        return all;
    }

    private static <T> List<DiffResult> doDiff(List<T> original, List<T> current, Function<T, String> idMapping) {
        Map<String, T> originalColumnMap = toMap(original, idMapping);
        Map<String, T> currentColumnMap = toMap(current, idMapping);
        BiFunction<T, DiffType, DiffResult> mapping = (meta, diffType) -> {
            return new DiffResult(idMapping.apply(meta), diffType);
        };
        List<DiffResult> added = added(originalColumnMap, currentColumnMap)
                .stream()
                .map(col -> mapping.apply(col, DiffType.ADDED))
                .collect(Collectors.toList());
        List<DiffResult> removed = removed(originalColumnMap, currentColumnMap)
                .stream()
                .map(col -> mapping.apply(col, DiffType.REMOVED))
                .collect(Collectors.toList());
        List<DiffResult> modified = modified(originalColumnMap, currentColumnMap)
                .stream()
                .map(col -> mapping.apply(col, DiffType.MODIFIED))
                .collect(Collectors.toList());
        List<DiffResult> same = same(originalColumnMap, currentColumnMap)
                .stream()
                .map(col -> mapping.apply(col, DiffType.NONE))
                .collect(Collectors.toList());
        List<DiffResult> columnResults = new ArrayList<>();
        columnResults.addAll(same);
        columnResults.addAll(added);
        columnResults.addAll(modified);
        columnResults.addAll(removed);
        return columnResults;
    }

    private static <T> Map<String, T> toMap(List<T> content, Function<T, String> idMapping) {
        return content
                .stream()
                .collect(Collectors.toMap(idMapping, Function.identity(), (a, b) -> {
                    log.warn("Duplicate key, origin = {}, current = {}", a, b);
                    return a;
                }));
    }

    private static <T> List<T> added(Map<String, T> originalMapById,
                                     Map<String, T> currentMapById) {
        return currentMapById.entrySet()
                .stream()
                .filter(entry -> !originalMapById.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private static <T> List<T> removed(Map<String, T> originalMapById,
                                       Map<String, T> currentMapById) {
        return originalMapById.entrySet()
                .stream()
                .filter(entry -> !currentMapById.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private static <T> List<T> modified(Map<String, T> originalMapById,
                                        Map<String, T> currentMapById) {
        return modified(originalMapById, currentMapById, Objects::equals);
    }

    private static <T> List<T> modified(Map<String, T> originalMapById,
                                        Map<String, T> currentMapById,
                                        BiFunction<T, T, Boolean> sameFunction) {
        return currentMapById.entrySet()
                .stream()
                .filter(entry -> originalMapById.containsKey(entry.getKey()))
                .filter(entry -> !sameFunction.apply(entry.getValue(), originalMapById.get(entry.getKey())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    private static <T> List<T> same(Map<String, T> originalMapById,
                                    Map<String, T> currentMapById) {
        return same(originalMapById, currentMapById, Objects::equals);
    }

    private static <T> List<T> same(Map<String, T> originalMapById,
                                    Map<String, T> currentMapById,
                                    BiFunction<T, T, Boolean> sameFunction) {
        return currentMapById.entrySet()
                .stream()
                .filter(entry -> originalMapById.containsKey(entry.getKey()))
                .filter(entry -> sameFunction.apply(entry.getValue(), originalMapById.get(entry.getKey())))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}

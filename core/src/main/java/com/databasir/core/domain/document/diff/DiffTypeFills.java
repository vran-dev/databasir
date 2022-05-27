package com.databasir.core.domain.document.diff;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.domain.document.comparator.DiffResult;
import com.databasir.core.domain.document.comparator.TableDiffResult;
import com.databasir.core.domain.document.data.DiffAble;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse.ForeignKeyDocumentResponse;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class DiffTypeFills {

    public static TableDocumentResponse fillRemoved(TableDocumentResponse data) {
        data.setDiffType(DiffType.REMOVED);
        data.getColumns().forEach(col -> col.setDiffType(DiffType.REMOVED));
        data.getForeignKeys().forEach(col -> col.setDiffType(DiffType.REMOVED));
        data.getIndexes().forEach(col -> col.setDiffType(DiffType.REMOVED));
        data.getTriggers().forEach(col -> col.setDiffType(DiffType.REMOVED));
        return data;
    }

    public static TableDocumentResponse fillAdded(TableDocumentResponse data, TableDiffResult diff) {
        data.setDiffType(DiffType.ADDED);
        var cols =
                diff(diff.getColumnDiffResults(), emptyList(), data.getColumns(), i -> i.getName());
        data.setColumns(cols);
        var indexes =
                diff(diff.getIndexDiffResults(), emptyList(), data.getIndexes(), i -> i.getName());
        data.setIndexes(indexes);
        var foreignKeys = foreignKeyDiff(diff.getForeignKeyDiffResults(),
                emptyList(), data.getForeignKeys());
        data.setForeignKeys(foreignKeys);
        var triggers =
                diff(diff.getTriggerDiffResults(), emptyList(), data.getTriggers(), t -> t.getName());
        data.setTriggers(triggers);
        return data;
    }

    public static TableDocumentResponse fillModified(TableDocumentResponse current,
                                                     TableDocumentResponse original,
                                                     TableDiffResult diff) {
        current.setDiffType(DiffType.MODIFIED);
        current.setOriginal(original);
        var cols =
                diff(diff.getColumnDiffResults(), original.getColumns(), current.getColumns(),
                        col -> col.getName());
        current.setColumns(cols);
        var indexes =
                diff(diff.getIndexDiffResults(), original.getIndexes(), current.getIndexes(), i -> i.getName());
        current.setIndexes(indexes);
        var foreignKeys = foreignKeyDiff(diff.getForeignKeyDiffResults(),
                original.getForeignKeys(), current.getForeignKeys());
        current.setForeignKeys(foreignKeys);
        var triggers =
                diff(diff.getTriggerDiffResults(), original.getTriggers(), current.getTriggers(),
                        t -> t.getName());
        current.setTriggers(triggers);
        return current;
    }

    public static <T extends DiffAble> List<T> diff(Collection<DiffResult> diffs,
                                                    Collection<T> original,
                                                    Collection<T> current,
                                                    Function<T, String> idMapping) {
        var currentMapByName = current.stream()
                .collect(Collectors.toMap(idMapping, Function.identity(), (a, b) -> a));
        var originalMapByName = original.stream()
                .collect(Collectors.toMap(idMapping, Function.identity(), (a, b) -> a));
        return diffs.stream().map(diff -> {
                    if (diff.getDiffType() == DiffType.ADDED) {
                        var t = currentMapByName.get(diff.getId());
                        t.setDiffType(DiffType.ADDED);
                        return t;
                    }
                    if (diff.getDiffType() == DiffType.REMOVED) {
                        var t = originalMapByName.get(diff.getId());
                        t.setDiffType(DiffType.REMOVED);
                        return t;
                    }
                    if (diff.getDiffType() == DiffType.MODIFIED) {
                        var c = currentMapByName.get(diff.getId());
                        var o = originalMapByName.get(diff.getId());
                        c.setDiffType(DiffType.MODIFIED);
                        c.setOriginal(o);
                        return c;
                    }
                    var t = currentMapByName.get(diff.getId());
                    t.setDiffType(DiffType.NONE);
                    return t;
                })
                .collect(Collectors.toList());
    }

    public static List<ForeignKeyDocumentResponse> foreignKeyDiff(Collection<DiffResult> diffs,
                                                                  Collection<ForeignKeyDocumentResponse> original,
                                                                  Collection<ForeignKeyDocumentResponse> current) {
        Function<ForeignKeyDocumentResponse, String> idMapping = fk -> {
            return fk.getFkTableName() + "." + fk.getFkColumnName() + "." + fk.getKeySeq();
        };
        return diff(diffs, original, current, idMapping);
    }
}

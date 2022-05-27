package com.databasir.core.domain.document.diff;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse;

import java.util.List;

public class DiffTypePredictor {

    public static DiffType predict(List<DatabaseDocumentSimpleResponse.TableData> result) {
        long changedItemSize = result.stream()
                .filter(item -> !item.getDiffType().isNone())
                .count();
        long addedItemSize = result.stream()
                .filter(item -> !item.getDiffType().isNone())
                .filter(item -> item.getDiffType().isAdded())
                .count();
        long removedItemSize = result.stream()
                .filter(item -> !item.getDiffType().isNone())
                .filter(item -> item.getDiffType().isRemoved())
                .count();
        if (changedItemSize > 0 && addedItemSize == changedItemSize) {
            return DiffType.ADDED;
        } else if (changedItemSize > 0 && removedItemSize == changedItemSize) {
            return DiffType.REMOVED;
        } else {
            return result.stream()
                    .anyMatch(t -> t.getDiffType() != DiffType.NONE) ? DiffType.MODIFIED : DiffType.NONE;
        }
    }
}

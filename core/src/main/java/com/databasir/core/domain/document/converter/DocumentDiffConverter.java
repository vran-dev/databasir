package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.diff.*;
import com.databasir.dao.tables.pojos.*;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DocumentDiffConverter {

    default DatabaseDocDiff of(DatabaseDocument databaseDocument,
                               List<TableDocument> tables,
                               List<TableColumnDocument> columns,
                               List<TableIndexDocument> indexes,
                               List<TableTriggerDocument> triggers,
                               List<TableForeignKeyDocument> foreignKeys) {
        List<TableDocDiff> tableDocDiffList = of(tables, columns, indexes, triggers, foreignKeys);
        return of(databaseDocument, tableDocDiffList);
    }

    default List<TableDocDiff> of(List<TableDocument> tables,
                                  List<TableColumnDocument> columns,
                                  List<TableIndexDocument> indexes,
                                  List<TableTriggerDocument> triggers,
                                  List<TableForeignKeyDocument> foreignKeys) {
        var columnGroupByTableId = groupBy(columns, TableColumnDocument::getTableDocumentId);
        var indexGroupByTableId = groupBy(indexes, TableIndexDocument::getTableDocumentId);
        var triggerGroupByTableId = groupBy(triggers, TableTriggerDocument::getTableDocumentId);
        var fkGroupByTableId = groupBy(foreignKeys, TableForeignKeyDocument::getTableDocumentId);
        return tables.stream()
                .map(table -> {
                    Integer id = table.getId();
                    var tableCols = columnGroupByTableId.getOrDefault(id, Collections.emptyList());
                    var tableIndexes = indexGroupByTableId.getOrDefault(id, Collections.emptyList());
                    var tableTriggers = triggerGroupByTableId.getOrDefault(id, Collections.emptyList());
                    var tableForeignKeys = fkGroupByTableId.getOrDefault(id, Collections.emptyList());
                    return of(table, tableCols, tableIndexes, tableTriggers, tableForeignKeys);
                })
                .collect(Collectors.toList());
    }

    DatabaseDocDiff of(DatabaseDocument databaseDocument, List<TableDocDiff> tables);

    TableDocDiff of(TableDocument table,
                    List<TableColumnDocument> columns,
                    List<TableIndexDocument> indexes,
                    List<TableTriggerDocument> triggers,
                    List<TableForeignKeyDocument> foreignKeys);

    ColumnDocDiff of(TableColumnDocument pojo);

    IndexDocDiff of(TableIndexDocument pojo);

    TriggerDocDiff of(TableTriggerDocument pojo);

    ForeignKeyDocDiff of(TableForeignKeyDocument pojo);

    default <R> Map<Integer, List<R>> groupBy(List<R> content, Function<R, Integer> idMapping) {
        return content.stream()
                .collect(Collectors.groupingBy(idMapping));
    }
}

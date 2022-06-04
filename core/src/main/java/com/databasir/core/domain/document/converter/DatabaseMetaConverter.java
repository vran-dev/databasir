package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.meta.data.*;
import com.databasir.dao.tables.pojos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = JsonConverter.class)
public interface DatabaseMetaConverter {

    default DatabaseMeta of(DatabaseDocument database,
                            List<TableDocument> tables,
                            List<TableColumnDocument> columns,
                            List<TableIndexDocument> indexes,
                            List<TableTriggerDocument> triggers,
                            List<TableForeignKeyDocument> foreignKeys) {
        var columnMap = groupBy(columns, TableColumnDocument::getTableDocumentId);
        var indexMap = groupBy(indexes, TableIndexDocument::getTableDocumentId);
        var triggerMap = groupBy(triggers, TableTriggerDocument::getTableDocumentId);
        var fkMap = groupBy(foreignKeys, TableForeignKeyDocument::getTableDocumentId);
        return of(database, tables, columnMap, indexMap, triggerMap, fkMap);
    }

    default DatabaseMeta of(DatabaseDocument database,
                            List<TableDocument> tables,
                            Map<Integer, List<TableColumnDocument>> columnGroupByTableId,
                            Map<Integer, List<TableIndexDocument>> indexGroupByTableId,
                            Map<Integer, List<TableTriggerDocument>> triggerGroupByTableId,
                            Map<Integer, List<TableForeignKeyDocument>> fkGroupByTableId) {
        List<TableMeta> tableMetas = tables.stream()
                .map(table -> {
                    Integer id = table.getId();
                    var columns = columnGroupByTableId.getOrDefault(id, Collections.emptyList());
                    var indexes = indexGroupByTableId.getOrDefault(id, Collections.emptyList());
                    var triggers = triggerGroupByTableId.getOrDefault(id, Collections.emptyList());
                    var foreignKeys = fkGroupByTableId.getOrDefault(id, Collections.emptyList());
                    return of(table, columns, indexes, triggers, foreignKeys);
                })
                .collect(Collectors.toList());
        return of(database, tableMetas);
    }

    DatabaseMeta of(DatabaseDocument database, List<TableMeta> tables);

    TableMeta of(TableDocument table,
                 List<TableColumnDocument> columns,
                 List<TableIndexDocument> indexes,
                 List<TableTriggerDocument> triggers,
                 List<TableForeignKeyDocument> foreignKeys);

    ColumnMeta of(TableColumnDocument pojo);

    @Mapping(target = "isUniqueKey", source = "pojo.isUnique")
    @Mapping(target = "columnNames", source = "pojo.columnNameArray")
    IndexMeta of(TableIndexDocument pojo);

    @Mapping(target = "createAt", source = "pojo.triggerCreateAt")
    TriggerMeta of(TableTriggerDocument pojo);

    List<TableMeta> of(List<TableDocumentResponse> table);

    TableMeta of(TableDocumentResponse table);

    default <R> Map<Integer, List<R>> groupBy(List<R> content, Function<R, Integer> idMapping) {
        return content.stream()
                .collect(Collectors.groupingBy(idMapping));
    }
}

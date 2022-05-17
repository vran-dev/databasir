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

    default DatabaseMeta of(DatabaseDocumentPojo database,
                            List<TableDocumentPojo> tables,
                            List<TableColumnDocumentPojo> columns,
                            List<TableIndexDocumentPojo> indexes,
                            List<TableTriggerDocumentPojo> triggers,
                            List<TableForeignKeyDocumentPojo> foreignKeys) {
        var columnMap = groupBy(columns, TableColumnDocumentPojo::getTableDocumentId);
        var indexMap = groupBy(indexes, TableIndexDocumentPojo::getTableDocumentId);
        var triggerMap = groupBy(triggers, TableTriggerDocumentPojo::getTableDocumentId);
        var fkMap = groupBy(foreignKeys, TableForeignKeyDocumentPojo::getTableDocumentId);
        return of(database, tables, columnMap, indexMap, triggerMap, fkMap);
    }

    default DatabaseMeta of(DatabaseDocumentPojo database,
                            List<TableDocumentPojo> tables,
                            Map<Integer, List<TableColumnDocumentPojo>> columnGroupByTableId,
                            Map<Integer, List<TableIndexDocumentPojo>> indexGroupByTableId,
                            Map<Integer, List<TableTriggerDocumentPojo>> triggerGroupByTableId,
                            Map<Integer, List<TableForeignKeyDocumentPojo>> fkGroupByTableId) {
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

    DatabaseMeta of(DatabaseDocumentPojo database, List<TableMeta> tables);

    TableMeta of(TableDocumentPojo table,
                 List<TableColumnDocumentPojo> columns,
                 List<TableIndexDocumentPojo> indexes,
                 List<TableTriggerDocumentPojo> triggers,
                 List<TableForeignKeyDocumentPojo> foreignKeys);

    ColumnMeta of(TableColumnDocumentPojo pojo);

    @Mapping(target = "isUniqueKey", source = "pojo.isUnique")
    @Mapping(target = "columnNames", source = "pojo.columnNameArray")
    IndexMeta of(TableIndexDocumentPojo pojo);

    @Mapping(target = "createAt", source = "pojo.triggerCreateAt")
    TriggerMeta of(TableTriggerDocumentPojo pojo);

    List<TableMeta> of(List<TableDocumentResponse> table);

    TableMeta of(TableDocumentResponse table);

    default <R> Map<Integer, List<R>> groupBy(List<R> content, Function<R, Integer> idMapping) {
        return content.stream()
                .collect(Collectors.groupingBy(idMapping));
    }
}

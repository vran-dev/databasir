package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse;
import com.databasir.core.domain.document.data.diff.ColumnDocDiff;
import com.databasir.core.domain.document.data.diff.TableDocDiff;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = JsonConverter.class, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DocumentResponseConverter {

    @Mapping(target = "columns", source = "columns")
    @Mapping(target = "indexes", source = "indexes")
    @Mapping(target = "foreignKeys", source = "foreignKeys")
    @Mapping(target = "triggers", source = "triggers")
    TableDocumentResponse of(TableDocument tableDocument,
                             List<TableColumnDocument> columns,
                             List<TableIndexDocument> indexes,
                             List<TableForeignKeyDocument> foreignKeys,
                             List<TableTriggerDocument> triggers);

    @Mapping(target = "columns", source = "columns")
    @Mapping(target = "indexes", source = "indexes")
    @Mapping(target = "foreignKeys", source = "foreignKeys")
    @Mapping(target = "triggers", source = "triggers")
    @SuppressWarnings("checkstyle:all")
    TableDocumentResponse of(TableDocument tableDocument,
                             Integer discussionCount,
                             String description,
                             List<TableDocumentResponse.ColumnDocumentResponse> columns,
                             List<TableIndexDocument> indexes,
                             List<TableForeignKeyDocument> foreignKeys,
                             List<TableTriggerDocument> triggers);

    TableDocumentResponse.ColumnDocumentResponse of(TableColumnDocument pojo,
                                                    String description);

    default List<TableDocumentResponse.ColumnDocumentResponse> of(
            List<TableColumnDocument> columns,
            String tableName,
            Map<String, String> descriptionMapByJoinName) {
        return columns.stream()
                .map(column -> {
                    String description = descriptionMapByJoinName.get(tableName + "." + column.getName());
                    return of(column, description);
                })
                .collect(Collectors.toList());
    }

    @Mapping(target = "columnNames", source = "columnNameArray")
    TableDocumentResponse.IndexDocumentResponse of(TableIndexDocument indexDocument);

    @Mapping(target = "id", source = "databaseDocument.id")
    @Mapping(target = "createAt", source = "databaseDocument.createAt")
    @Mapping(target = "documentVersion", source = "databaseDocument.version")
    DatabaseDocumentResponse of(DatabaseDocument databaseDocument,
                                List<TableDocumentResponse> tables);

    default TableDocumentResponse ofDiff(TableDocDiff table,
                                         Map<String, Integer> discussionCountMap,
                                         Map<String, String> descriptionMap) {
        List<TableDocumentResponse.ColumnDocumentResponse> cols = toColumns(table, descriptionMap);
        return ofDiff(table, cols, discussionCountMap, descriptionMap);
    }

    @Mapping(target = "description", expression = "java(descriptionMap.get(table.getName()))")
    @Mapping(target = "discussionCount", expression = "java(discussionCountMap.get(table.getName()))")
    @Mapping(target = "columns", source = "cols")
    TableDocumentResponse ofDiff(TableDocDiff table,
                                 List<TableDocumentResponse.ColumnDocumentResponse> cols,
                                 Map<String, Integer> discussionCountMap,
                                 Map<String, String> descriptionMap);

    default List<TableDocumentResponse.ColumnDocumentResponse> toColumns(TableDocDiff table,
                                                                         Map<String, String> descriptionMap) {
        return table.getColumns()
                .stream()
                .map(col -> toColumn(table.getName(), col, descriptionMap))
                .collect(Collectors.toList());
    }

    @Mapping(target = "description", expression = "java(descriptionMap.get(tableName+\".\"+diff.getName()))")
    @Mapping(target = "original",
            expression = "java(toOriginalColumn(tableName, diff.getOriginal(), descriptionMap))")
    TableDocumentResponse.ColumnDocumentResponse toColumn(String tableName,
                                                          ColumnDocDiff diff,
                                                          Map<String, String> descriptionMap);

    default TableDocumentResponse.ColumnDocumentResponse toOriginalColumn(String tableName,
                                                                          ColumnDocDiff diff,
                                                                          Map<String, String> descriptionMap) {
        if (diff == null) {
            return null;
        }
        return toColumn(tableName, diff, descriptionMap);
    }
}

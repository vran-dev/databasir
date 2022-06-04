package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.domain.document.data.TableDocumentResponse;
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
                                                    Integer discussionCount,
                                                    String description);

    default List<TableDocumentResponse.ColumnDocumentResponse> of(
            List<TableColumnDocument> columns,
            String tableName,
            Map<String, Integer> discussionCountMapByJoinName,
            Map<String, String> descriptionMapByJoinName) {
        return columns.stream()
                .map(column -> {
                    Integer count = discussionCountMapByJoinName.get(tableName + "." + column.getName());
                    String description = descriptionMapByJoinName.get(tableName + "." + column.getName());
                    return of(column, count, description);
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
}

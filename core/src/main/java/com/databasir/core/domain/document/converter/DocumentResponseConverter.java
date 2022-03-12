package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
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
    @Mapping(target = "triggers", source = "triggers")
    DatabaseDocumentResponse.TableDocumentResponse of(TableDocumentPojo tableDocument,
                                                      List<TableColumnDocumentPojo> columns,
                                                      List<TableIndexDocumentPojo> indexes,
                                                      List<TableTriggerDocumentPojo> triggers);

    @Mapping(target = "columns", source = "columns")
    @Mapping(target = "indexes", source = "indexes")
    @Mapping(target = "triggers", source = "triggers")
    @SuppressWarnings("checkstyle:all")
    DatabaseDocumentResponse.TableDocumentResponse of(TableDocumentPojo tableDocument,
                                                      Integer discussionCount,
                                                      List<DatabaseDocumentResponse.TableDocumentResponse.ColumnDocumentResponse> columns,
                                                      List<TableIndexDocumentPojo> indexes,
                                                      List<TableTriggerDocumentPojo> triggers);

    DatabaseDocumentResponse.TableDocumentResponse.ColumnDocumentResponse of(TableColumnDocumentPojo pojo,
                                                                             Integer discussionCount);

    default List<DatabaseDocumentResponse.TableDocumentResponse.ColumnDocumentResponse> of(
            List<TableColumnDocumentPojo> columns,
            String tableName,
            Map<String, Integer> discussionCountMapByJoinName) {
        return columns.stream()
                .map(column -> {
                    Integer count = discussionCountMapByJoinName.get(tableName + "." + column.getName());
                    return of(column, count);
                })
                .collect(Collectors.toList());
    }


    @Mapping(target = "columnNames", source = "columnNameArray")
    DatabaseDocumentResponse.TableDocumentResponse.IndexDocumentResponse of(TableIndexDocumentPojo indexDocument);

    @Mapping(target = "id", source = "databaseDocument.id")
    @Mapping(target = "createAt", source = "databaseDocument.createAt")
    @Mapping(target = "documentVersion", source = "databaseDocument.version")
    DatabaseDocumentResponse of(DatabaseDocumentPojo databaseDocument,
                                List<DatabaseDocumentResponse.TableDocumentResponse> tables);
}

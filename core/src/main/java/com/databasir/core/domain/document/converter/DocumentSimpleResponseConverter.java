package com.databasir.core.domain.document.converter;

import com.databasir.core.diff.data.DiffType;
import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse;
import com.databasir.core.domain.document.data.diff.TableDocDiff;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.DatabaseDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = JsonConverter.class, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DocumentSimpleResponseConverter {

    @Mapping(target = "id", source = "database.id")
    @Mapping(target = "createAt", source = "database.createAt")
    @Mapping(target = "documentVersion", source = "database.version")
    DatabaseDocumentSimpleResponse of(DatabaseDocument database, List<TableDocDiff> tables, String projectName);

    @Mapping(target = "id", source = "databaseDocument.id")
    @Mapping(target = "createAt", source = "databaseDocument.createAt")
    @Mapping(target = "documentVersion", source = "databaseDocument.version")
    DatabaseDocumentSimpleResponse of(DatabaseDocument databaseDocument,
                                      List<DatabaseDocumentSimpleResponse.TableData> tables,
                                      DiffType diffType,
                                      String projectName);

    DatabaseDocumentSimpleResponse.TableData of(TableDocument tables,
                                                Integer discussionCount,
                                                String description);

    default List<DatabaseDocumentSimpleResponse.TableData> of(List<TableDocument> tables,
                                                              Map<String, Integer> discussionCountMapByTableName,
                                                              Map<String, String> descriptionMapByTableName) {
        return tables.stream()
                .map(table -> {
                    Integer count = discussionCountMapByTableName.get(table.getName());
                    String description = descriptionMapByTableName.get(table.getName());
                    return of(table, count, description);
                })
                .collect(Collectors.toList());
    }

    default List<DatabaseDocumentSimpleResponse.TableData> ofDiff(List<TableDocDiff> tables,
                                                                  Map<String, Integer> discussionCountMapByTableName,
                                                                  Map<String, String> descriptionMapByTableName) {
        return tables.stream()
                .map(tableDocDiff -> {
                    String tableName = tableDocDiff.getName();
                    Integer count = discussionCountMapByTableName.get(tableName);
                    String description = descriptionMapByTableName.get(tableName);
                    return ofDiff(tableDocDiff, count, description);
                })
                .collect(Collectors.toList());
    }

    DatabaseDocumentSimpleResponse.TableData ofDiff(TableDocDiff table,
                                                    Integer discussionCount,
                                                    String description);

}

package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.DatabaseDocumentPojo;
import com.databasir.dao.tables.pojos.TableDocumentPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = JsonConverter.class, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DocumentSimpleResponseConverter {

    @Mapping(target = "id", source = "databaseDocument.id")
    @Mapping(target = "createAt", source = "databaseDocument.createAt")
    @Mapping(target = "documentVersion", source = "databaseDocument.version")
    DatabaseDocumentSimpleResponse of(DatabaseDocumentPojo databaseDocument,
                                      List<DatabaseDocumentSimpleResponse.TableData> tables);

    DatabaseDocumentSimpleResponse.TableData of(TableDocumentPojo tables,
                                                Integer discussionCount,
                                                String description);

    default List<DatabaseDocumentSimpleResponse.TableData> of(List<TableDocumentPojo> tables,
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
}

package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = JsonConverter.class, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DocumentResponseConverter {

    @Mapping(target = "columns", source = "columns")
    @Mapping(target = "indexes", source = "indexes")
    @Mapping(target = "triggers", source = "triggers")
    DatabaseDocumentResponse.TableDocumentResponse of(TableDocumentPojo tableDocument,
                                                      List<TableColumnDocumentPojo> columns,
                                                      List<TableIndexDocumentPojo> indexes,
                                                      List<TableTriggerDocumentPojo> triggers);

    @Mapping(target = "columnNames", source = "columnNameArray")
    DatabaseDocumentResponse.TableDocumentResponse.IndexDocumentResponse of(TableIndexDocumentPojo indexDocument);

    @Mapping(target = "id", source = "databaseDocument.id")
    @Mapping(target = "createAt", source = "databaseDocument.createAt")
    @Mapping(target = "documentVersion", source = "databaseDocument.version")
    DatabaseDocumentResponse of(DatabaseDocumentPojo databaseDocument,
                                List<DatabaseDocumentResponse.TableDocumentResponse> tables);
}

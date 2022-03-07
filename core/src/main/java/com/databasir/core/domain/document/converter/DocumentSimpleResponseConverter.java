package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DatabaseDocumentSimpleResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.DatabaseDocumentPojo;
import com.databasir.dao.tables.pojos.TableDocumentPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = JsonConverter.class, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DocumentSimpleResponseConverter {

    @Mapping(target = "id", source = "databaseDocument.id")
    @Mapping(target = "createAt", source = "databaseDocument.createAt")
    @Mapping(target = "documentVersion", source = "databaseDocument.version")
    DatabaseDocumentSimpleResponse of(DatabaseDocumentPojo databaseDocument,
                                      List<TableDocumentPojo> tables);
}

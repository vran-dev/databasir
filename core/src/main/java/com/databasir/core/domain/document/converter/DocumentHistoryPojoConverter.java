package com.databasir.core.domain.document.converter;

import com.databasir.core.domain.document.data.DatabaseDocumentResponse;
import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.dao.tables.pojos.DatabaseDocumentHistoryPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = JsonConverter.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentHistoryPojoConverter {

    @Mapping(target = "databaseDocumentObject", source = "databaseMetaObject")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    DatabaseDocumentHistoryPojo of(DatabaseDocumentResponse databaseMetaObject,
                                   Integer projectId,
                                   Integer databaseDocumentId,
                                   Long version);
}

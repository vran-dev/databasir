package com.databasir.core.domain.document.converter;

import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.meta.data.ColumnMeta;
import com.databasir.core.meta.data.IndexMeta;
import com.databasir.core.meta.data.TriggerMeta;
import com.databasir.dao.tables.pojos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = JsonConverter.class, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DocumentPojoConverter extends BaseConverter {

    @Mapping(target = "databaseName", source = "meta.databaseName")
    @Mapping(target = "schemaName", source = "meta.schemaName")
    @Mapping(target = "isArchive", constant = "false")
    DatabaseDocumentPojo toDatabasePojo(Integer projectId,
                                        com.databasir.core.meta.data.DatabaseMeta meta,
                                        Long version);

    @Mapping(target = "comment", qualifiedBy = NullToEmpty.class)
    TableDocumentPojo toTablePojo(Integer databaseDocumentId,
                                  com.databasir.core.meta.data.TableMeta meta);

    default List<TableColumnDocumentPojo> toColumnPojo(Integer databaseDocumentId,
                                                       Integer tableDocumentId,
                                                       List<ColumnMeta> metaList) {
        return metaList.stream()
                .map(meta -> toColumnPojo(databaseDocumentId, tableDocumentId, meta))
                .collect(Collectors.toList());
    }

    @Mapping(target = "comment", qualifiedBy = NullToEmpty.class)
    TableColumnDocumentPojo toColumnPojo(Integer databaseDocumentId,
                                         Integer tableDocumentId,
                                         ColumnMeta meta);

    default List<TableIndexDocumentPojo> toIndexPojo(Integer databaseDocumentId,
                                                     Integer tableDocumentId,
                                                     List<IndexMeta> metaList) {
        return metaList.stream()
                .map(meta -> toIndexPojo(databaseDocumentId, tableDocumentId, meta))
                .collect(Collectors.toList());
    }

    @Mapping(target = "isUnique", source = "meta.isUniqueKey")
    @Mapping(target = "columnNameArray", source = "meta.columnNames")
    TableIndexDocumentPojo toIndexPojo(Integer databaseDocumentId,
                                       Integer tableDocumentId,
                                       IndexMeta meta);

    default List<TableTriggerDocumentPojo> toTriggerPojo(Integer databaseDocumentId,
                                                         Integer tableDocumentId,
                                                         List<TriggerMeta> metaList) {
        return metaList.stream()
                .map(meta -> toTriggerPojo(databaseDocumentId, tableDocumentId, meta))
                .collect(Collectors.toList());
    }

    @Mapping(target = "triggerCreateAt", source = "meta.createAt")
    TableTriggerDocumentPojo toTriggerPojo(Integer databaseDocumentId,
                                           Integer tableDocumentId,
                                           TriggerMeta meta);

}

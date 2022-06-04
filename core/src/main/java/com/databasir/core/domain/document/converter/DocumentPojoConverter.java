package com.databasir.core.domain.document.converter;

import com.databasir.core.infrastructure.converter.JsonConverter;
import com.databasir.core.meta.data.*;
import com.databasir.dao.tables.pojos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = JsonConverter.class, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DocumentPojoConverter {

    @Mapping(target = "databaseName", source = "meta.databaseName")
    @Mapping(target = "schemaName", source = "meta.schemaName")
    @Mapping(target = "isArchive", constant = "false")
    DatabaseDocument toDatabasePojo(Integer projectId,
                                        DatabaseMeta meta,
                                        Long version);

    TableDocument toTablePojo(Integer databaseDocumentId,
                                  com.databasir.core.meta.data.TableMeta meta);

    default List<TableColumnDocument> toColumnPojo(Integer databaseDocumentId,
                                                       Integer tableDocumentId,
                                                       List<ColumnMeta> metaList) {
        return metaList.stream()
                .map(meta -> toColumnPojo(databaseDocumentId, tableDocumentId, meta))
                .collect(Collectors.toList());
    }

    TableColumnDocument toColumnPojo(Integer databaseDocumentId,
                                         Integer tableDocumentId,
                                         ColumnMeta meta);

    default List<TableIndexDocument> toIndexPojo(Integer databaseDocumentId,
                                                     Integer tableDocumentId,
                                                     List<IndexMeta> metaList) {
        return metaList.stream()
                .map(meta -> toIndexPojo(databaseDocumentId, tableDocumentId, meta))
                .collect(Collectors.toList());
    }

    @Mapping(target = "isUnique", source = "meta.isUniqueKey")
    @Mapping(target = "columnNameArray", source = "meta.columnNames")
    TableIndexDocument toIndexPojo(Integer databaseDocumentId,
                                       Integer tableDocumentId,
                                       IndexMeta meta);

    default List<TableTriggerDocument> toTriggerPojo(Integer databaseDocumentId,
                                                         Integer tableDocumentId,
                                                         List<TriggerMeta> metaList) {
        return metaList.stream()
                .map(meta -> toTriggerPojo(databaseDocumentId, tableDocumentId, meta))
                .collect(Collectors.toList());
    }

    @Mapping(target = "triggerCreateAt", source = "meta.createAt")
    @Mapping(target = "createAt", ignore = true)
    TableTriggerDocument toTriggerPojo(Integer databaseDocumentId,
                                           Integer tableDocumentId,
                                           TriggerMeta meta);

    default List<TableForeignKeyDocument> toForeignKeyPojo(Integer docId,
                                                               Integer tableMetaId,
                                                               List<ForeignKeyMeta> foreignKeys) {
        return foreignKeys.stream()
                .map(key -> toForeignKeyPojo(docId, tableMetaId, key))
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    TableForeignKeyDocument toForeignKeyPojo(Integer databaseDocumentId,
                                                 Integer tableDocumentId,
                                                 ForeignKeyMeta foreignKey);

}

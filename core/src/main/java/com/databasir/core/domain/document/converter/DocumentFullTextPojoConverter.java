package com.databasir.core.domain.document.converter;

import com.databasir.dao.tables.pojos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentFullTextPojoConverter {

    /**
     * groupName、groupDescription, projectName, projectDescription 等信息需要动态获取，所以不保存
     */
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "projectId", source = "db.projectId")
    @Mapping(target = "databaseDocumentId", source = "db.id")
    @Mapping(target = "databaseDocumentVersion", source = "db.version")
    @Mapping(target = "databaseProductName", source = "db.productName")
    @Mapping(target = "tableDocumentId", source = "table.id")
    @Mapping(target = "tableColumnDocumentId", source = "column.id")
    @Mapping(target = "tableName", source = "table.name")
    @Mapping(target = "tableComment", source = "table.comment")
    @Mapping(target = "colName", source = "column.name")
    @Mapping(target = "colComment", source = "column.comment")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "groupName", ignore = true)
    @Mapping(target = "groupDescription", ignore = true)
    @Mapping(target = "projectName", ignore = true)
    @Mapping(target = "projectDescription", ignore = true)
    DocumentFullTextPojo toPojo(GroupPojo group,
                                ProjectPojo project,
                                DatabaseDocumentPojo db,
                                TableDocumentPojo table,
                                TableColumnDocumentPojo column,
                                String tableDescription,
                                String columnDescription);
}

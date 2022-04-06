package com.databasir.core.domain.mock.converter;

import com.databasir.core.domain.mock.data.MockDataRuleResponse;
import com.databasir.dao.enums.MockDataType;
import com.databasir.dao.tables.pojos.MockDataRulePojo;
import com.databasir.dao.tables.pojos.TableColumnDocumentPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MockDataRuleResponseConverter {

    @Mapping(target = "columnType", source = "column.type")
    MockDataRuleResponse from(MockDataRulePojo pojo, TableColumnDocumentPojo column);

    @Mapping(target = "columnName", source = "pojo.name")
    @Mapping(target = "columnType", source = "pojo.type")
    MockDataRuleResponse from(String tableName, MockDataType mockDataType, TableColumnDocumentPojo pojo);
}

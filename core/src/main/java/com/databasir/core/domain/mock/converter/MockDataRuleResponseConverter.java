package com.databasir.core.domain.mock.converter;

import com.databasir.core.domain.mock.data.MockDataRuleResponse;
import com.databasir.dao.enums.MockDataType;
import com.databasir.dao.tables.pojos.MockDataRule;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MockDataRuleResponseConverter {

    @Mapping(target = "columnType", source = "column.type")
    MockDataRuleResponse from(MockDataRule pojo, TableColumnDocument column);

    @Mapping(target = "columnName", source = "pojo.name")
    @Mapping(target = "columnType", source = "pojo.type")
    MockDataRuleResponse from(String tableName, MockDataType mockDataType, TableColumnDocument pojo);
}

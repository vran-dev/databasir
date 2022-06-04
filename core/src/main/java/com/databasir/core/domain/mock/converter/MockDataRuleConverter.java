package com.databasir.core.domain.mock.converter;

import com.databasir.core.domain.mock.data.ColumnMockRuleSaveRequest;
import com.databasir.dao.tables.pojos.MockDataRule;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MockDataRuleConverter {

    MockDataRule from(Integer projectId, ColumnMockRuleSaveRequest request);

    default List<MockDataRule> from(Integer projectId, List<ColumnMockRuleSaveRequest> request) {
        return request.stream()
                .map(rule -> from(projectId, rule))
                .collect(Collectors.toList());
    }
}

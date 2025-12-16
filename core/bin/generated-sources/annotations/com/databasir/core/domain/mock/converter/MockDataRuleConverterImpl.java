package com.databasir.core.domain.mock.converter;

import com.databasir.core.domain.mock.data.ColumnMockRuleSaveRequest;
import com.databasir.dao.tables.pojos.MockDataRule;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class MockDataRuleConverterImpl implements MockDataRuleConverter {

    @Override
    public MockDataRule from(Integer projectId, ColumnMockRuleSaveRequest request) {
        if ( projectId == null && request == null ) {
            return null;
        }

        MockDataRule mockDataRule = new MockDataRule();

        if ( projectId != null ) {
            mockDataRule.setProjectId( projectId );
        }
        if ( request != null ) {
            mockDataRule.setTableName( request.getTableName() );
            mockDataRule.setColumnName( request.getColumnName() );
            mockDataRule.setDependentTableName( request.getDependentTableName() );
            mockDataRule.setDependentColumnName( request.getDependentColumnName() );
            mockDataRule.setMockDataType( request.getMockDataType() );
            mockDataRule.setMockDataScript( request.getMockDataScript() );
        }

        return mockDataRule;
    }
}

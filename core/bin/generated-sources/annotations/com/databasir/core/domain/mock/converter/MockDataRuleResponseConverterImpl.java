package com.databasir.core.domain.mock.converter;

import com.databasir.core.domain.mock.data.MockDataRuleResponse;
import com.databasir.dao.enums.MockDataType;
import com.databasir.dao.tables.pojos.MockDataRule;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T11:24:05+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class MockDataRuleResponseConverterImpl implements MockDataRuleResponseConverter {

    @Override
    public MockDataRuleResponse from(MockDataRule pojo, TableColumnDocument column) {
        if ( pojo == null && column == null ) {
            return null;
        }

        MockDataRuleResponse mockDataRuleResponse = new MockDataRuleResponse();

        if ( pojo != null ) {
            mockDataRuleResponse.setColumnName( pojo.getColumnName() );
            mockDataRuleResponse.setDependentColumnName( pojo.getDependentColumnName() );
            mockDataRuleResponse.setDependentTableName( pojo.getDependentTableName() );
            mockDataRuleResponse.setMockDataScript( pojo.getMockDataScript() );
            mockDataRuleResponse.setMockDataType( pojo.getMockDataType() );
            mockDataRuleResponse.setTableName( pojo.getTableName() );
        }
        if ( column != null ) {
            mockDataRuleResponse.setColumnType( column.getType() );
        }

        return mockDataRuleResponse;
    }

    @Override
    public MockDataRuleResponse from(String tableName, MockDataType mockDataType, TableColumnDocument pojo) {
        if ( tableName == null && mockDataType == null && pojo == null ) {
            return null;
        }

        MockDataRuleResponse mockDataRuleResponse = new MockDataRuleResponse();

        if ( tableName != null ) {
            mockDataRuleResponse.setTableName( tableName );
        }
        if ( mockDataType != null ) {
            mockDataRuleResponse.setMockDataType( mockDataType );
        }
        if ( pojo != null ) {
            mockDataRuleResponse.setColumnName( pojo.getName() );
            mockDataRuleResponse.setColumnType( pojo.getType() );
        }

        return mockDataRuleResponse;
    }
}

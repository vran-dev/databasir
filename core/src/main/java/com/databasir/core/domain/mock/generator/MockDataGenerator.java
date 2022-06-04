package com.databasir.core.domain.mock.generator;

import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.mock.factory.MockColumnRule;
import com.databasir.core.domain.mock.factory.MockDataFactory;
import com.databasir.dao.enums.MockDataType;
import com.databasir.dao.impl.MockDataRuleDao;
import com.databasir.dao.impl.ProjectDao;
import com.databasir.dao.impl.TableColumnDocumentDao;
import com.databasir.dao.impl.TableDocumentDao;
import com.databasir.dao.tables.pojos.MockDataRule;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MockDataGenerator {

    private final List<MockDataFactory> mockDataFactories;

    private final MockDataRuleDao mockDataRuleDao;

    private final TableDocumentDao tableDocumentDao;

    private final TableColumnDocumentDao tableColumnDocumentDao;

    private final ProjectDao projectDao;

    public String createInsertSql(Integer projectId,
                                  Integer databaseDocId,
                                  String tableName) {
        if (!projectDao.existsById(projectId)) {
            throw DomainErrors.PROJECT_NOT_FOUND.exception();
        }
        MockDataContext context = MockDataContext.builder()
                .databaseDocumentId(databaseDocId)
                .projectId(projectId)
                .build();
        create(context, tableName);
        return context.toInsertSql();
    }

    private void create(MockDataContext context, String tableName) {
        if (!context.containsTable(tableName)) {
            var tableOption =
                    tableDocumentDao.selectByDatabaseDocumentIdAndTableName(context.getDatabaseDocumentId(), tableName);
            if (tableOption.isEmpty()) {
                log.warn("can not find table => " + tableName);
                return;
            }
            var table = tableOption.get();
            var columns = tableColumnDocumentDao.selectByTableDocumentId(table.getId());
            context.addTableColumns(tableName, columns);
        }
        if (!context.containMockRule(tableName)) {
            var columnRules = mockDataRuleDao.selectByProjectIdAndTableName(context.getProjectId(), tableName);
            context.addTableMockRules(tableName, columnRules);
        }

        for (TableColumnDocument column : context.getTableColumnMap().get(tableName).values()) {
            if (context.containsColumnMockData(tableName, column.getName())
                    || context.isMockInProgress(tableName, column.getName())) {
                continue;
            }
            create(context, tableName, column.getName());
        }
    }

    private void create(MockDataContext context, String tableName, String columnName) {
        if (context.containsColumnMockData(tableName, columnName)) {
            return;
        }
        TableColumnDocument column = context.getTableColumn(tableName, columnName);
        Optional<MockDataRule> ruleOption = context.getMockRule(tableName, columnName);
        String rawData;
        if (ruleOption.isPresent()) {
            MockDataRule rule = ruleOption.get();
            if (rule.getMockDataType() == MockDataType.REF) {
                context.addMockInProgress(tableName, columnName);
                context.saveReference(
                        rule.getTableName(), rule.getColumnName(),
                        rule.getDependentTableName(), rule.getDependentColumnName()
                );
                if (context.containsTable(rule.getDependentTableName())) {
                    create(context, rule.getDependentTableName(), rule.getDependentColumnName());
                } else {
                    create(context, rule.getDependentTableName());
                }
                context.removeMockInProgress(tableName, columnName);
                rawData = context.getRawColumnMockData(rule.getDependentTableName(), rule.getDependentColumnName());
            } else {
                rawData = createByFactory(column, rule);
            }
        } else {
            rawData = createByFactory(column, null);
        }
        context.addColumnMockData(tableName, toData(rawData, column));
    }

    private String createByFactory(TableColumnDocument column, MockDataRule rule) {
        MockDataType mockType = rule == null ? MockDataType.AUTO : rule.getMockDataType();
        MockColumnRule colRule = MockColumnRule.builder()
                .dataType(column.getDataType())
                .mockDataType(mockType)
                .mockDataScript(null == rule ? null : rule.getMockDataScript())
                .columnName(column.getName())
                .columnType(column.getType())
                .build();
        return mockDataFactories.stream()
                .filter(factory -> factory.accept(colRule))
                .findFirst()
                .map(factory -> factory.create(colRule))
                .orElseThrow();
    }

    private ColumnMockData toData(String data, TableColumnDocument column) {
        return ColumnMockData.builder()
                .columnName(column.getName())
                .columnType(column.getType())
                .mockData(data)
                .build();
    }
}

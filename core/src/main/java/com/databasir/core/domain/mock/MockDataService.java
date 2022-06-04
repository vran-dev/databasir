package com.databasir.core.domain.mock;

import com.databasir.core.domain.mock.converter.MockDataRuleConverter;
import com.databasir.core.domain.mock.converter.MockDataRuleResponseConverter;
import com.databasir.core.domain.mock.data.ColumnMockRuleSaveRequest;
import com.databasir.core.domain.mock.data.MockDataGenerateCondition;
import com.databasir.core.domain.mock.data.MockDataRuleListCondition;
import com.databasir.core.domain.mock.data.MockDataRuleResponse;
import com.databasir.core.domain.mock.generator.MockDataGenerator;
import com.databasir.core.domain.mock.validator.MockDataSaveValidator;
import com.databasir.core.domain.mock.validator.MockDataValidator;
import com.databasir.dao.enums.MockDataType;
import com.databasir.dao.impl.MockDataRuleDao;
import com.databasir.dao.impl.TableColumnDocumentDao;
import com.databasir.dao.tables.pojos.DatabaseDocument;
import com.databasir.dao.tables.pojos.MockDataRule;
import com.databasir.dao.tables.pojos.TableColumnDocument;
import com.databasir.dao.tables.pojos.TableDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MockDataService {

    private final MockDataGenerator mockDataGenerator;

    private final MockDataRuleDao mockDataRuleDao;

    private final TableColumnDocumentDao tableColumnDocumentDao;

    private final MockDataRuleConverter mockDataRuleConverter;

    private final MockDataRuleResponseConverter mockDataRuleResponseConverter;

    private final MockDataSaveValidator mockDataSaveValidator;

    private final MockDataValidator mockDataValidator;

    public String generateMockInsertSql(Integer projectId, MockDataGenerateCondition condition) {
        mockDataValidator.validProject(projectId);
        DatabaseDocument databaseDoc =
                mockDataValidator.validAndGetDatabaseDocument(projectId, condition.getVersion());
        TableDocument tableDoc =
                mockDataValidator.validAndGetTableDocument(databaseDoc.getId(), condition.getTableId());
        return mockDataGenerator.createInsertSql(projectId, databaseDoc.getId(), tableDoc.getName());
    }

    public void saveMockRules(Integer projectId,
                              Integer tableId,
                              List<ColumnMockRuleSaveRequest> rules) {
        mockDataValidator.validProject(projectId);
        DatabaseDocument doc =
                mockDataValidator.validAndGetDatabaseDocument(projectId, null);
        TableDocument tableDoc =
                mockDataValidator.validAndGetTableDocument(doc.getId(), tableId);
        List<String> columnNames = rules.stream()
                .map(ColumnMockRuleSaveRequest::getColumnName)
                .collect(Collectors.toList());
        mockDataSaveValidator.validTableColumn(tableDoc.getId(), columnNames);
        mockDataSaveValidator.validScriptMockType(rules);
        mockDataSaveValidator.validRefMockType(doc.getId(), rules);
        // verify
        mockDataGenerator.createInsertSql(projectId, doc.getId(), tableDoc.getName());

        List<MockDataRule> pojo = mockDataRuleConverter.from(projectId, rules);
        mockDataRuleDao.batchSave(pojo);
    }

    public List<MockDataRuleResponse> listRules(Integer projectId, MockDataRuleListCondition condition) {
        mockDataValidator.validProject(projectId);
        DatabaseDocument databaseDoc =
                mockDataValidator.validAndGetDatabaseDocument(projectId, condition.getVersion());
        TableDocument tableDoc =
                mockDataValidator.validAndGetTableDocument(databaseDoc.getId(), condition.getTableId());
        List<TableColumnDocument> columns =
                tableColumnDocumentDao.selectByTableDocumentId(condition.getTableId());
        var ruleMapByColumnName = mockDataRuleDao.selectByProjectIdAndTableName(projectId, tableDoc.getName())
                .stream()
                .collect(Collectors.toMap(MockDataRule::getColumnName, Function.identity()));
        return columns.stream()
                .map(col -> {
                    if (ruleMapByColumnName.containsKey(col.getName())) {
                        return mockDataRuleResponseConverter.from(ruleMapByColumnName.get(col.getName()), col);
                    } else {
                        return mockDataRuleResponseConverter.from(tableDoc.getName(), MockDataType.AUTO, col);
                    }
                })
                .collect(Collectors.toList());
    }

}

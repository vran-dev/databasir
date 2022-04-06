package com.databasir.core.domain.mock.validator;

import com.alibaba.excel.util.StringUtils;
import com.databasir.core.domain.DomainErrors;
import com.databasir.core.domain.mock.data.ColumnMockRuleSaveRequest;
import com.databasir.core.domain.mock.script.MockScriptEvaluator;
import com.databasir.core.domain.mock.script.SpelScriptEvaluator;
import com.databasir.dao.enums.MockDataType;
import com.databasir.dao.impl.TableColumnDocumentDao;
import com.databasir.dao.impl.TableDocumentDao;
import com.databasir.dao.tables.pojos.TableColumnDocumentPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MockDataSaveValidator {

    private final TableDocumentDao tableDocumentDao;

    private final TableColumnDocumentDao tableColumnDocumentDao;

    private final SpelScriptEvaluator spelScriptEvaluator;

    public void validScriptMockType(List<ColumnMockRuleSaveRequest> rules) {
        for (ColumnMockRuleSaveRequest request : rules) {
            if (request.getMockDataType() != MockDataType.SCRIPT) {
                continue;
            }
            if (StringUtils.isBlank(request.getMockDataScript())) {
                throw DomainErrors.MOCK_DATA_SCRIPT_MUST_NOT_BE_BLANK.exception();
            }
            try {
                spelScriptEvaluator.evaluate(request.getMockDataScript(), new MockScriptEvaluator.ScriptContext());
            } catch (Exception e) {
                throw DomainErrors.INVALID_MOCK_DATA_SCRIPT.exception(e.getMessage());
            }
        }
    }

    public void validTableColumn(Integer tableDocId, List<String> requestColumnNames) {
        var existsColumnNames = tableColumnDocumentDao.selectByTableDocumentId(tableDocId)
                .stream()
                .map(TableColumnDocumentPojo::getName)
                .collect(Collectors.toSet());
        for (String colName : requestColumnNames) {
            if (!existsColumnNames.contains(colName)) {
                throw DomainErrors.TABLE_META_NOT_FOUND.exception("column "
                        + colName
                        + " not exists in "
                        + tableDocId);
            }
        }
    }

    public void validRefMockType(Integer databaseDocId,
                                 List<ColumnMockRuleSaveRequest> rules) {
        Map<String, Set<String>> fromTableAndColumn = new HashMap<>();
        Map<String, Set<String>> toTableAndColumn = new HashMap<>();
        for (ColumnMockRuleSaveRequest request : rules) {
            if (request.getMockDataType() != MockDataType.REF) {
                continue;
            }
            forbiddenIfMissRequireParams(request);
            forbiddenIfSelfReference(request);
            forbiddenIfCircleReference(request, fromTableAndColumn, toTableAndColumn);
            forbiddenIfIsInvalidTableOrColumn(databaseDocId, request);
        }
    }

    private void forbiddenIfSelfReference(ColumnMockRuleSaveRequest request) {
        if (!Objects.equals(request.getTableName(), request.getDependentTableName())) {
            return;
        }
        if (!Objects.equals(request.getColumnName(), request.getDependentColumnName())) {
            return;
        }
        throw DomainErrors.MUST_NOT_REF_SELF.exception();
    }

    private void forbiddenIfMissRequireParams(ColumnMockRuleSaveRequest request) {
        if (StringUtils.isBlank(request.getDependentTableName())) {
            throw DomainErrors.DEPENDENT_COLUMN_NAME_MUST_NOT_BE_BLANK.exception();
        }
        if (StringUtils.isBlank(request.getDependentColumnName())) {
            throw DomainErrors.DEPENDENT_COLUMN_NAME_MUST_NOT_BE_BLANK.exception();
        }
    }

    private void forbiddenIfIsInvalidTableOrColumn(Integer docId, ColumnMockRuleSaveRequest request) {
        String dependentTableName = request.getDependentTableName();
        var dependentTable = tableDocumentDao.selectByDatabaseDocumentIdAndTableName(docId, dependentTableName)
                .orElseThrow(DomainErrors.TABLE_META_NOT_FOUND::exception);
        if (!tableColumnDocumentDao.exists(dependentTable.getId(), request.getDependentColumnName())) {
            throw DomainErrors.TABLE_META_NOT_FOUND.exception("列字段 "
                    + request.getDependentColumnName()
                    + "不存在");
        }
    }

    private void forbiddenIfCircleReference(ColumnMockRuleSaveRequest request,
                                            Map<String, Set<String>> fromTableAndColumn,
                                            Map<String, Set<String>> toTableAndColumn) {
        if (toTableAndColumn.containsKey(request.getTableName())
                && toTableAndColumn.get(request.getTableName()).contains(request.getColumnName())) {
            if (fromTableAndColumn.containsKey(request.getDependentTableName())
                    && fromTableAndColumn.get(request.getDependentTableName())
                    .contains(request.getDependentColumnName())) {
                String format = "%s 和 %s 出现了循环引用";
                String from = request.getTableName() + "." + request.getColumnName();
                String to = request.getDependentTableName() + "." + request.getDependentColumnName();
                String message = String.format(format, from, to);
                throw DomainErrors.CIRCLE_REFERENCE.exception(message);
            }
        }

        Set<String> fromColumns =
                fromTableAndColumn.computeIfAbsent(request.getTableName(), key -> new HashSet<String>());
        fromColumns.add(request.getColumnName());
        Set<String> toColumns =
                toTableAndColumn.computeIfAbsent(request.getDependentTableName(), key -> new HashSet<String>());
        toColumns.add(request.getDependentColumnName());
    }
}

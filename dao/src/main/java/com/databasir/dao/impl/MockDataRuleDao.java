package com.databasir.dao.impl;

import com.databasir.dao.tables.pojos.MockDataRule;
import com.databasir.dao.tables.records.MockDataRuleRecord;
import lombok.Getter;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.databasir.dao.Tables.MOCK_DATA_RULE;

@Repository
public class MockDataRuleDao extends BaseDao<MockDataRule> {

    @Autowired
    @Getter
    private DSLContext dslContext;

    public MockDataRuleDao() {
        super(MOCK_DATA_RULE, MockDataRule.class);
    }

    public List<MockDataRule> selectByProjectId(Integer projectId) {
        return this.getDslContext()
                .selectFrom(MOCK_DATA_RULE)
                .where(MOCK_DATA_RULE.PROJECT_ID.eq(projectId))
                .fetchInto(MockDataRule.class);
    }

    public List<MockDataRule> selectByProjectIdAndTableName(Integer projectId, String tableName) {
        return this.getDslContext()
                .selectFrom(MOCK_DATA_RULE)
                .where(MOCK_DATA_RULE.PROJECT_ID.eq(projectId)
                        .and(MOCK_DATA_RULE.TABLE_NAME.eq(tableName)))
                .fetchInto(MockDataRule.class);
    }

    public void batchSave(Collection<MockDataRule> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        List<InsertReturningStep<MockDataRuleRecord>> query = data.stream()
                .map(pojo -> getDslContext()
                        .insertInto(MOCK_DATA_RULE)
                        .set(getDslContext().newRecord(MOCK_DATA_RULE, pojo))
                        .onDuplicateKeyUpdate()
                        .set(MOCK_DATA_RULE.MOCK_DATA_TYPE, pojo.getMockDataType())
                        .set(MOCK_DATA_RULE.MOCK_DATA_SCRIPT, pojo.getMockDataScript())
                        .set(MOCK_DATA_RULE.DEPENDENT_COLUMN_NAME, pojo.getDependentColumnName())
                        .set(MOCK_DATA_RULE.DEPENDENT_TABLE_NAME, pojo.getDependentTableName())
                )
                .collect(Collectors.toList());
        getDslContext().batch(query).execute();
    }

}
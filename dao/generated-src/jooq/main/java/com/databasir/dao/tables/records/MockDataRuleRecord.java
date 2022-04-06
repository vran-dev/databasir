/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables.records;


import com.databasir.dao.enums.MockDataType;
import com.databasir.dao.tables.MockDataRule;
import com.databasir.dao.tables.pojos.MockDataRulePojo;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MockDataRuleRecord extends UpdatableRecordImpl<MockDataRuleRecord> implements Record10<Integer, Integer, String, String, String, String, MockDataType, String, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>databasir.mock_data_rule.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>databasir.mock_data_rule.project_id</code>.
     */
    public void setProjectId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.project_id</code>.
     */
    public Integer getProjectId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>databasir.mock_data_rule.table_name</code>.
     */
    public void setTableName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.table_name</code>.
     */
    public String getTableName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>databasir.mock_data_rule.column_name</code>.
     */
    public void setColumnName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.column_name</code>.
     */
    public String getColumnName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>databasir.mock_data_rule.dependent_table_name</code>.
     */
    public void setDependentTableName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.dependent_table_name</code>.
     */
    public String getDependentTableName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>databasir.mock_data_rule.dependent_column_name</code>.
     */
    public void setDependentColumnName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.dependent_column_name</code>.
     */
    public String getDependentColumnName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>databasir.mock_data_rule.mock_data_type</code>.
     */
    public void setMockDataType(MockDataType value) {
        set(6, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.mock_data_type</code>.
     */
    public MockDataType getMockDataType() {
        return (MockDataType) get(6);
    }

    /**
     * Setter for <code>databasir.mock_data_rule.mock_data_script</code>.
     */
    public void setMockDataScript(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.mock_data_script</code>.
     */
    public String getMockDataScript() {
        return (String) get(7);
    }

    /**
     * Setter for <code>databasir.mock_data_rule.update_at</code>.
     */
    public void setUpdateAt(LocalDateTime value) {
        set(8, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.update_at</code>.
     */
    public LocalDateTime getUpdateAt() {
        return (LocalDateTime) get(8);
    }

    /**
     * Setter for <code>databasir.mock_data_rule.create_at</code>.
     */
    public void setCreateAt(LocalDateTime value) {
        set(9, value);
    }

    /**
     * Getter for <code>databasir.mock_data_rule.create_at</code>.
     */
    public LocalDateTime getCreateAt() {
        return (LocalDateTime) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, Integer, String, String, String, String, MockDataType, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<Integer, Integer, String, String, String, String, MockDataType, String, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return MockDataRule.MOCK_DATA_RULE.ID;
    }

    @Override
    public Field<Integer> field2() {
        return MockDataRule.MOCK_DATA_RULE.PROJECT_ID;
    }

    @Override
    public Field<String> field3() {
        return MockDataRule.MOCK_DATA_RULE.TABLE_NAME;
    }

    @Override
    public Field<String> field4() {
        return MockDataRule.MOCK_DATA_RULE.COLUMN_NAME;
    }

    @Override
    public Field<String> field5() {
        return MockDataRule.MOCK_DATA_RULE.DEPENDENT_TABLE_NAME;
    }

    @Override
    public Field<String> field6() {
        return MockDataRule.MOCK_DATA_RULE.DEPENDENT_COLUMN_NAME;
    }

    @Override
    public Field<MockDataType> field7() {
        return MockDataRule.MOCK_DATA_RULE.MOCK_DATA_TYPE;
    }

    @Override
    public Field<String> field8() {
        return MockDataRule.MOCK_DATA_RULE.MOCK_DATA_SCRIPT;
    }

    @Override
    public Field<LocalDateTime> field9() {
        return MockDataRule.MOCK_DATA_RULE.UPDATE_AT;
    }

    @Override
    public Field<LocalDateTime> field10() {
        return MockDataRule.MOCK_DATA_RULE.CREATE_AT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getProjectId();
    }

    @Override
    public String component3() {
        return getTableName();
    }

    @Override
    public String component4() {
        return getColumnName();
    }

    @Override
    public String component5() {
        return getDependentTableName();
    }

    @Override
    public String component6() {
        return getDependentColumnName();
    }

    @Override
    public MockDataType component7() {
        return getMockDataType();
    }

    @Override
    public String component8() {
        return getMockDataScript();
    }

    @Override
    public LocalDateTime component9() {
        return getUpdateAt();
    }

    @Override
    public LocalDateTime component10() {
        return getCreateAt();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getProjectId();
    }

    @Override
    public String value3() {
        return getTableName();
    }

    @Override
    public String value4() {
        return getColumnName();
    }

    @Override
    public String value5() {
        return getDependentTableName();
    }

    @Override
    public String value6() {
        return getDependentColumnName();
    }

    @Override
    public MockDataType value7() {
        return getMockDataType();
    }

    @Override
    public String value8() {
        return getMockDataScript();
    }

    @Override
    public LocalDateTime value9() {
        return getUpdateAt();
    }

    @Override
    public LocalDateTime value10() {
        return getCreateAt();
    }

    @Override
    public MockDataRuleRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public MockDataRuleRecord value2(Integer value) {
        setProjectId(value);
        return this;
    }

    @Override
    public MockDataRuleRecord value3(String value) {
        setTableName(value);
        return this;
    }

    @Override
    public MockDataRuleRecord value4(String value) {
        setColumnName(value);
        return this;
    }

    @Override
    public MockDataRuleRecord value5(String value) {
        setDependentTableName(value);
        return this;
    }

    @Override
    public MockDataRuleRecord value6(String value) {
        setDependentColumnName(value);
        return this;
    }

    @Override
    public MockDataRuleRecord value7(MockDataType value) {
        setMockDataType(value);
        return this;
    }

    @Override
    public MockDataRuleRecord value8(String value) {
        setMockDataScript(value);
        return this;
    }

    @Override
    public MockDataRuleRecord value9(LocalDateTime value) {
        setUpdateAt(value);
        return this;
    }

    @Override
    public MockDataRuleRecord value10(LocalDateTime value) {
        setCreateAt(value);
        return this;
    }

    @Override
    public MockDataRuleRecord values(Integer value1, Integer value2, String value3, String value4, String value5, String value6, MockDataType value7, String value8, LocalDateTime value9, LocalDateTime value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MockDataRuleRecord
     */
    public MockDataRuleRecord() {
        super(MockDataRule.MOCK_DATA_RULE);
    }

    /**
     * Create a detached, initialised MockDataRuleRecord
     */
    public MockDataRuleRecord(Integer id, Integer projectId, String tableName, String columnName, String dependentTableName, String dependentColumnName, MockDataType mockDataType, String mockDataScript, LocalDateTime updateAt, LocalDateTime createAt) {
        super(MockDataRule.MOCK_DATA_RULE);

        setId(id);
        setProjectId(projectId);
        setTableName(tableName);
        setColumnName(columnName);
        setDependentTableName(dependentTableName);
        setDependentColumnName(dependentColumnName);
        setMockDataType(mockDataType);
        setMockDataScript(mockDataScript);
        setUpdateAt(updateAt);
        setCreateAt(createAt);
    }

    /**
     * Create a detached, initialised MockDataRuleRecord
     */
    public MockDataRuleRecord(MockDataRulePojo value) {
        super(MockDataRule.MOCK_DATA_RULE);

        if (value != null) {
            setId(value.getId());
            setProjectId(value.getProjectId());
            setTableName(value.getTableName());
            setColumnName(value.getColumnName());
            setDependentTableName(value.getDependentTableName());
            setDependentColumnName(value.getDependentColumnName());
            setMockDataType(value.getMockDataType());
            setMockDataScript(value.getMockDataScript());
            setUpdateAt(value.getUpdateAt());
            setCreateAt(value.getCreateAt());
        }
    }
}

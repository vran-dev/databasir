/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables.records;


import com.databasir.dao.tables.TableForeignKeyDocumentTable;
import com.databasir.dao.tables.pojos.TableForeignKeyDocument;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TableForeignKeyDocumentRecord extends UpdatableRecordImpl<TableForeignKeyDocumentRecord> implements Record13<Integer, Integer, Integer, Integer, String, String, String, String, String, String, String, String, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>databasir.table_foreign_key_document.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>databasir.table_foreign_key_document.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for
     * <code>databasir.table_foreign_key_document.table_document_id</code>.
     */
    public void setTableDocumentId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for
     * <code>databasir.table_foreign_key_document.table_document_id</code>.
     */
    public Integer getTableDocumentId() {
        return (Integer) get(1);
    }

    /**
     * Setter for
     * <code>databasir.table_foreign_key_document.database_document_id</code>.
     */
    public void setDatabaseDocumentId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>databasir.table_foreign_key_document.database_document_id</code>.
     */
    public Integer getDatabaseDocumentId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>databasir.table_foreign_key_document.key_seq</code>.
     */
    public void setKeySeq(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>databasir.table_foreign_key_document.key_seq</code>.
     */
    public Integer getKeySeq() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>databasir.table_foreign_key_document.fk_name</code>.
     */
    public void setFkName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>databasir.table_foreign_key_document.fk_name</code>.
     */
    public String getFkName() {
        return (String) get(4);
    }

    /**
     * Setter for
     * <code>databasir.table_foreign_key_document.fk_table_name</code>.
     */
    public void setFkTableName(String value) {
        set(5, value);
    }

    /**
     * Getter for
     * <code>databasir.table_foreign_key_document.fk_table_name</code>.
     */
    public String getFkTableName() {
        return (String) get(5);
    }

    /**
     * Setter for
     * <code>databasir.table_foreign_key_document.fk_column_name</code>.
     */
    public void setFkColumnName(String value) {
        set(6, value);
    }

    /**
     * Getter for
     * <code>databasir.table_foreign_key_document.fk_column_name</code>.
     */
    public String getFkColumnName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>databasir.table_foreign_key_document.pk_name</code>.
     */
    public void setPkName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>databasir.table_foreign_key_document.pk_name</code>.
     */
    public String getPkName() {
        return (String) get(7);
    }

    /**
     * Setter for
     * <code>databasir.table_foreign_key_document.pk_table_name</code>.
     */
    public void setPkTableName(String value) {
        set(8, value);
    }

    /**
     * Getter for
     * <code>databasir.table_foreign_key_document.pk_table_name</code>.
     */
    public String getPkTableName() {
        return (String) get(8);
    }

    /**
     * Setter for
     * <code>databasir.table_foreign_key_document.pk_column_name</code>.
     */
    public void setPkColumnName(String value) {
        set(9, value);
    }

    /**
     * Getter for
     * <code>databasir.table_foreign_key_document.pk_column_name</code>.
     */
    public String getPkColumnName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>databasir.table_foreign_key_document.update_rule</code>.
     * NO_ACTION, CASCADE, SET_NULL, SET_DEFAULT
     */
    public void setUpdateRule(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>databasir.table_foreign_key_document.update_rule</code>.
     * NO_ACTION, CASCADE, SET_NULL, SET_DEFAULT
     */
    public String getUpdateRule() {
        return (String) get(10);
    }

    /**
     * Setter for <code>databasir.table_foreign_key_document.delete_rule</code>.
     * NO_ACTION, CASCADE, SET_NULL, SET_DEFAULT
     */
    public void setDeleteRule(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>databasir.table_foreign_key_document.delete_rule</code>.
     * NO_ACTION, CASCADE, SET_NULL, SET_DEFAULT
     */
    public String getDeleteRule() {
        return (String) get(11);
    }

    /**
     * Setter for <code>databasir.table_foreign_key_document.create_at</code>.
     */
    public void setCreateAt(LocalDateTime value) {
        set(12, value);
    }

    /**
     * Getter for <code>databasir.table_foreign_key_document.create_at</code>.
     */
    public LocalDateTime getCreateAt() {
        return (LocalDateTime) get(12);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row13<Integer, Integer, Integer, Integer, String, String, String, String, String, String, String, String, LocalDateTime> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    @Override
    public Row13<Integer, Integer, Integer, Integer, String, String, String, String, String, String, String, String, LocalDateTime> valuesRow() {
        return (Row13) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.ID;
    }

    @Override
    public Field<Integer> field2() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.TABLE_DOCUMENT_ID;
    }

    @Override
    public Field<Integer> field3() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.DATABASE_DOCUMENT_ID;
    }

    @Override
    public Field<Integer> field4() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.KEY_SEQ;
    }

    @Override
    public Field<String> field5() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.FK_NAME;
    }

    @Override
    public Field<String> field6() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.FK_TABLE_NAME;
    }

    @Override
    public Field<String> field7() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.FK_COLUMN_NAME;
    }

    @Override
    public Field<String> field8() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.PK_NAME;
    }

    @Override
    public Field<String> field9() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.PK_TABLE_NAME;
    }

    @Override
    public Field<String> field10() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.PK_COLUMN_NAME;
    }

    @Override
    public Field<String> field11() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.UPDATE_RULE;
    }

    @Override
    public Field<String> field12() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.DELETE_RULE;
    }

    @Override
    public Field<LocalDateTime> field13() {
        return TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT.CREATE_AT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getTableDocumentId();
    }

    @Override
    public Integer component3() {
        return getDatabaseDocumentId();
    }

    @Override
    public Integer component4() {
        return getKeySeq();
    }

    @Override
    public String component5() {
        return getFkName();
    }

    @Override
    public String component6() {
        return getFkTableName();
    }

    @Override
    public String component7() {
        return getFkColumnName();
    }

    @Override
    public String component8() {
        return getPkName();
    }

    @Override
    public String component9() {
        return getPkTableName();
    }

    @Override
    public String component10() {
        return getPkColumnName();
    }

    @Override
    public String component11() {
        return getUpdateRule();
    }

    @Override
    public String component12() {
        return getDeleteRule();
    }

    @Override
    public LocalDateTime component13() {
        return getCreateAt();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getTableDocumentId();
    }

    @Override
    public Integer value3() {
        return getDatabaseDocumentId();
    }

    @Override
    public Integer value4() {
        return getKeySeq();
    }

    @Override
    public String value5() {
        return getFkName();
    }

    @Override
    public String value6() {
        return getFkTableName();
    }

    @Override
    public String value7() {
        return getFkColumnName();
    }

    @Override
    public String value8() {
        return getPkName();
    }

    @Override
    public String value9() {
        return getPkTableName();
    }

    @Override
    public String value10() {
        return getPkColumnName();
    }

    @Override
    public String value11() {
        return getUpdateRule();
    }

    @Override
    public String value12() {
        return getDeleteRule();
    }

    @Override
    public LocalDateTime value13() {
        return getCreateAt();
    }

    @Override
    public TableForeignKeyDocumentRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value2(Integer value) {
        setTableDocumentId(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value3(Integer value) {
        setDatabaseDocumentId(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value4(Integer value) {
        setKeySeq(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value5(String value) {
        setFkName(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value6(String value) {
        setFkTableName(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value7(String value) {
        setFkColumnName(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value8(String value) {
        setPkName(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value9(String value) {
        setPkTableName(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value10(String value) {
        setPkColumnName(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value11(String value) {
        setUpdateRule(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value12(String value) {
        setDeleteRule(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord value13(LocalDateTime value) {
        setCreateAt(value);
        return this;
    }

    @Override
    public TableForeignKeyDocumentRecord values(Integer value1, Integer value2, Integer value3, Integer value4, String value5, String value6, String value7, String value8, String value9, String value10, String value11, String value12, LocalDateTime value13) {
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
        value11(value11);
        value12(value12);
        value13(value13);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TableForeignKeyDocumentRecord
     */
    public TableForeignKeyDocumentRecord() {
        super(TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT);
    }

    /**
     * Create a detached, initialised TableForeignKeyDocumentRecord
     */
    public TableForeignKeyDocumentRecord(Integer id, Integer tableDocumentId, Integer databaseDocumentId, Integer keySeq, String fkName, String fkTableName, String fkColumnName, String pkName, String pkTableName, String pkColumnName, String updateRule, String deleteRule, LocalDateTime createAt) {
        super(TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT);

        setId(id);
        setTableDocumentId(tableDocumentId);
        setDatabaseDocumentId(databaseDocumentId);
        setKeySeq(keySeq);
        setFkName(fkName);
        setFkTableName(fkTableName);
        setFkColumnName(fkColumnName);
        setPkName(pkName);
        setPkTableName(pkTableName);
        setPkColumnName(pkColumnName);
        setUpdateRule(updateRule);
        setDeleteRule(deleteRule);
        setCreateAt(createAt);
    }

    /**
     * Create a detached, initialised TableForeignKeyDocumentRecord
     */
    public TableForeignKeyDocumentRecord(TableForeignKeyDocument value) {
        super(TableForeignKeyDocumentTable.TABLE_FOREIGN_KEY_DOCUMENT);

        if (value != null) {
            setId(value.getId());
            setTableDocumentId(value.getTableDocumentId());
            setDatabaseDocumentId(value.getDatabaseDocumentId());
            setKeySeq(value.getKeySeq());
            setFkName(value.getFkName());
            setFkTableName(value.getFkTableName());
            setFkColumnName(value.getFkColumnName());
            setPkName(value.getPkName());
            setPkTableName(value.getPkTableName());
            setPkColumnName(value.getPkColumnName());
            setUpdateRule(value.getUpdateRule());
            setDeleteRule(value.getDeleteRule());
            setCreateAt(value.getCreateAt());
        }
    }
}

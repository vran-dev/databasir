/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables.records;


import com.databasir.dao.tables.TableTriggerDocument;
import com.databasir.dao.tables.pojos.TableTriggerDocumentPojo;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TableTriggerDocumentRecord extends UpdatableRecordImpl<TableTriggerDocumentRecord> implements Record8<Integer, Integer, Integer, String, String, String, String, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>databasir.table_trigger_document.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>databasir.table_trigger_document.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for
     * <code>databasir.table_trigger_document.table_document_id</code>.
     */
    public void setTableDocumentId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for
     * <code>databasir.table_trigger_document.table_document_id</code>.
     */
    public Integer getTableDocumentId() {
        return (Integer) get(1);
    }

    /**
     * Setter for
     * <code>databasir.table_trigger_document.database_document_id</code>.
     */
    public void setDatabaseDocumentId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>databasir.table_trigger_document.database_document_id</code>.
     */
    public Integer getDatabaseDocumentId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>databasir.table_trigger_document.timing</code>.
     */
    public void setTiming(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>databasir.table_trigger_document.timing</code>.
     */
    public String getTiming() {
        return (String) get(3);
    }

    /**
     * Setter for <code>databasir.table_trigger_document.manipulation</code>.
     */
    public void setManipulation(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>databasir.table_trigger_document.manipulation</code>.
     */
    public String getManipulation() {
        return (String) get(4);
    }

    /**
     * Setter for <code>databasir.table_trigger_document.statement</code>.
     */
    public void setStatement(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>databasir.table_trigger_document.statement</code>.
     */
    public String getStatement() {
        return (String) get(5);
    }

    /**
     * Setter for
     * <code>databasir.table_trigger_document.trigger_create_at</code>.
     */
    public void setTriggerCreateAt(String value) {
        set(6, value);
    }

    /**
     * Getter for
     * <code>databasir.table_trigger_document.trigger_create_at</code>.
     */
    public String getTriggerCreateAt() {
        return (String) get(6);
    }

    /**
     * Setter for <code>databasir.table_trigger_document.create_at</code>.
     */
    public void setCreateAt(LocalDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>databasir.table_trigger_document.create_at</code>.
     */
    public LocalDateTime getCreateAt() {
        return (LocalDateTime) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, Integer, Integer, String, String, String, String, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<Integer, Integer, Integer, String, String, String, String, LocalDateTime> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.ID;
    }

    @Override
    public Field<Integer> field2() {
        return TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.TABLE_DOCUMENT_ID;
    }

    @Override
    public Field<Integer> field3() {
        return TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.DATABASE_DOCUMENT_ID;
    }

    @Override
    public Field<String> field4() {
        return TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.TIMING;
    }

    @Override
    public Field<String> field5() {
        return TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.MANIPULATION;
    }

    @Override
    public Field<String> field6() {
        return TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.STATEMENT;
    }

    @Override
    public Field<String> field7() {
        return TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.TRIGGER_CREATE_AT;
    }

    @Override
    public Field<LocalDateTime> field8() {
        return TableTriggerDocument.TABLE_TRIGGER_DOCUMENT.CREATE_AT;
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
    public String component4() {
        return getTiming();
    }

    @Override
    public String component5() {
        return getManipulation();
    }

    @Override
    public String component6() {
        return getStatement();
    }

    @Override
    public String component7() {
        return getTriggerCreateAt();
    }

    @Override
    public LocalDateTime component8() {
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
    public String value4() {
        return getTiming();
    }

    @Override
    public String value5() {
        return getManipulation();
    }

    @Override
    public String value6() {
        return getStatement();
    }

    @Override
    public String value7() {
        return getTriggerCreateAt();
    }

    @Override
    public LocalDateTime value8() {
        return getCreateAt();
    }

    @Override
    public TableTriggerDocumentRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public TableTriggerDocumentRecord value2(Integer value) {
        setTableDocumentId(value);
        return this;
    }

    @Override
    public TableTriggerDocumentRecord value3(Integer value) {
        setDatabaseDocumentId(value);
        return this;
    }

    @Override
    public TableTriggerDocumentRecord value4(String value) {
        setTiming(value);
        return this;
    }

    @Override
    public TableTriggerDocumentRecord value5(String value) {
        setManipulation(value);
        return this;
    }

    @Override
    public TableTriggerDocumentRecord value6(String value) {
        setStatement(value);
        return this;
    }

    @Override
    public TableTriggerDocumentRecord value7(String value) {
        setTriggerCreateAt(value);
        return this;
    }

    @Override
    public TableTriggerDocumentRecord value8(LocalDateTime value) {
        setCreateAt(value);
        return this;
    }

    @Override
    public TableTriggerDocumentRecord values(Integer value1, Integer value2, Integer value3, String value4, String value5, String value6, String value7, LocalDateTime value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TableTriggerDocumentRecord
     */
    public TableTriggerDocumentRecord() {
        super(TableTriggerDocument.TABLE_TRIGGER_DOCUMENT);
    }

    /**
     * Create a detached, initialised TableTriggerDocumentRecord
     */
    public TableTriggerDocumentRecord(Integer id, Integer tableDocumentId, Integer databaseDocumentId, String timing, String manipulation, String statement, String triggerCreateAt, LocalDateTime createAt) {
        super(TableTriggerDocument.TABLE_TRIGGER_DOCUMENT);

        setId(id);
        setTableDocumentId(tableDocumentId);
        setDatabaseDocumentId(databaseDocumentId);
        setTiming(timing);
        setManipulation(manipulation);
        setStatement(statement);
        setTriggerCreateAt(triggerCreateAt);
        setCreateAt(createAt);
    }

    /**
     * Create a detached, initialised TableTriggerDocumentRecord
     */
    public TableTriggerDocumentRecord(TableTriggerDocumentPojo value) {
        super(TableTriggerDocument.TABLE_TRIGGER_DOCUMENT);

        if (value != null) {
            setId(value.getId());
            setTableDocumentId(value.getTableDocumentId());
            setDatabaseDocumentId(value.getDatabaseDocumentId());
            setTiming(value.getTiming());
            setManipulation(value.getManipulation());
            setStatement(value.getStatement());
            setTriggerCreateAt(value.getTriggerCreateAt());
            setCreateAt(value.getCreateAt());
        }
    }
}

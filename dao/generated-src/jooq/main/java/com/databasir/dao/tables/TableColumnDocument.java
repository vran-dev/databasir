/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables;


import com.databasir.dao.Databasir;
import com.databasir.dao.Indexes;
import com.databasir.dao.Keys;
import com.databasir.dao.tables.records.TableColumnDocumentRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row14;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TableColumnDocument extends TableImpl<TableColumnDocumentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>databasir.table_column_document</code>
     */
    public static final TableColumnDocument TABLE_COLUMN_DOCUMENT = new TableColumnDocument();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TableColumnDocumentRecord> getRecordType() {
        return TableColumnDocumentRecord.class;
    }

    /**
     * The column <code>databasir.table_column_document.id</code>.
     */
    public final TableField<TableColumnDocumentRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column
     * <code>databasir.table_column_document.table_document_id</code>.
     */
    public final TableField<TableColumnDocumentRecord, Integer> TABLE_DOCUMENT_ID = createField(DSL.name("table_document_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column
     * <code>databasir.table_column_document.database_document_id</code>.
     */
    public final TableField<TableColumnDocumentRecord, Integer> DATABASE_DOCUMENT_ID = createField(DSL.name("database_document_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>databasir.table_column_document.name</code>.
     */
    public final TableField<TableColumnDocumentRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>databasir.table_column_document.type</code>.
     */
    public final TableField<TableColumnDocumentRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>databasir.table_column_document.data_type</code>.
     */
    public final TableField<TableColumnDocumentRecord, Integer> DATA_TYPE = createField(DSL.name("data_type"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("99999", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>databasir.table_column_document.comment</code>.
     */
    public final TableField<TableColumnDocumentRecord, String> COMMENT = createField(DSL.name("comment"), SQLDataType.VARCHAR(512), this, "");

    /**
     * The column <code>databasir.table_column_document.default_value</code>.
     */
    public final TableField<TableColumnDocumentRecord, String> DEFAULT_VALUE = createField(DSL.name("default_value"), SQLDataType.VARCHAR(512), this, "");

    /**
     * The column <code>databasir.table_column_document.size</code>.
     */
    public final TableField<TableColumnDocumentRecord, Integer> SIZE = createField(DSL.name("size"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>databasir.table_column_document.decimal_digits</code>.
     */
    public final TableField<TableColumnDocumentRecord, Integer> DECIMAL_DIGITS = createField(DSL.name("decimal_digits"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>databasir.table_column_document.is_primary_key</code>.
     */
    public final TableField<TableColumnDocumentRecord, Boolean> IS_PRIMARY_KEY = createField(DSL.name("is_primary_key"), SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>databasir.table_column_document.nullable</code>. YES,
     * NO, UNKNOWN
     */
    public final TableField<TableColumnDocumentRecord, String> NULLABLE = createField(DSL.name("nullable"), SQLDataType.VARCHAR(64).nullable(false), this, "YES, NO, UNKNOWN");

    /**
     * The column <code>databasir.table_column_document.auto_increment</code>.
     * YES, NO, UNKNOWN
     */
    public final TableField<TableColumnDocumentRecord, String> AUTO_INCREMENT = createField(DSL.name("auto_increment"), SQLDataType.VARCHAR(64).nullable(false), this, "YES, NO, UNKNOWN");

    /**
     * The column <code>databasir.table_column_document.create_at</code>.
     */
    public final TableField<TableColumnDocumentRecord, LocalDateTime> CREATE_AT = createField(DSL.name("create_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private TableColumnDocument(Name alias, Table<TableColumnDocumentRecord> aliased) {
        this(alias, aliased, null);
    }

    private TableColumnDocument(Name alias, Table<TableColumnDocumentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>databasir.table_column_document</code> table
     * reference
     */
    public TableColumnDocument(String alias) {
        this(DSL.name(alias), TABLE_COLUMN_DOCUMENT);
    }

    /**
     * Create an aliased <code>databasir.table_column_document</code> table
     * reference
     */
    public TableColumnDocument(Name alias) {
        this(alias, TABLE_COLUMN_DOCUMENT);
    }

    /**
     * Create a <code>databasir.table_column_document</code> table reference
     */
    public TableColumnDocument() {
        this(DSL.name("table_column_document"), null);
    }

    public <O extends Record> TableColumnDocument(Table<O> child, ForeignKey<O, TableColumnDocumentRecord> key) {
        super(child, key, TABLE_COLUMN_DOCUMENT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Databasir.DATABASIR;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.TABLE_COLUMN_DOCUMENT_IDX_DATABASE_DOCUMENT_ID, Indexes.TABLE_COLUMN_DOCUMENT_IDX_TABLE_DOCUMENT_ID);
    }

    @Override
    public Identity<TableColumnDocumentRecord, Integer> getIdentity() {
        return (Identity<TableColumnDocumentRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<TableColumnDocumentRecord> getPrimaryKey() {
        return Keys.KEY_TABLE_COLUMN_DOCUMENT_PRIMARY;
    }

    @Override
    public TableColumnDocument as(String alias) {
        return new TableColumnDocument(DSL.name(alias), this);
    }

    @Override
    public TableColumnDocument as(Name alias) {
        return new TableColumnDocument(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TableColumnDocument rename(String name) {
        return new TableColumnDocument(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TableColumnDocument rename(Name name) {
        return new TableColumnDocument(name, null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<Integer, Integer, Integer, String, String, Integer, String, String, Integer, Integer, Boolean, String, String, LocalDateTime> fieldsRow() {
        return (Row14) super.fieldsRow();
    }
}

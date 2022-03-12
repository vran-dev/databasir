/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables;


import com.databasir.dao.Databasir;
import com.databasir.dao.Indexes;
import com.databasir.dao.Keys;
import com.databasir.dao.tables.records.DatabaseDocumentRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DatabaseDocument extends TableImpl<DatabaseDocumentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>databasir.database_document</code>
     */
    public static final DatabaseDocument DATABASE_DOCUMENT = new DatabaseDocument();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DatabaseDocumentRecord> getRecordType() {
        return DatabaseDocumentRecord.class;
    }

    /**
     * The column <code>databasir.database_document.id</code>.
     */
    public final TableField<DatabaseDocumentRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>databasir.database_document.project_id</code>.
     */
    public final TableField<DatabaseDocumentRecord, Integer> PROJECT_ID = createField(DSL.name("project_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>databasir.database_document.database_name</code>.
     */
    public final TableField<DatabaseDocumentRecord, String> DATABASE_NAME = createField(DSL.name("database_name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>databasir.database_document.schema_name</code>.
     */
    public final TableField<DatabaseDocumentRecord, String> SCHEMA_NAME = createField(DSL.name("schema_name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>databasir.database_document.product_name</code>.
     */
    public final TableField<DatabaseDocumentRecord, String> PRODUCT_NAME = createField(DSL.name("product_name"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>databasir.database_document.product_version</code>.
     */
    public final TableField<DatabaseDocumentRecord, String> PRODUCT_VERSION = createField(DSL.name("product_version"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>databasir.database_document.version</code>.
     */
    public final TableField<DatabaseDocumentRecord, Long> VERSION = createField(DSL.name("version"), SQLDataType.BIGINT.nullable(false).defaultValue(DSL.inline("1", SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>databasir.database_document.is_archive</code>.
     */
    public final TableField<DatabaseDocumentRecord, Boolean> IS_ARCHIVE = createField(DSL.name("is_archive"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.inline("0", SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>databasir.database_document.update_at</code>.
     */
    public final TableField<DatabaseDocumentRecord, LocalDateTime> UPDATE_AT = createField(DSL.name("update_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>databasir.database_document.create_at</code>.
     */
    public final TableField<DatabaseDocumentRecord, LocalDateTime> CREATE_AT = createField(DSL.name("create_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private DatabaseDocument(Name alias, Table<DatabaseDocumentRecord> aliased) {
        this(alias, aliased, null);
    }

    private DatabaseDocument(Name alias, Table<DatabaseDocumentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>databasir.database_document</code> table
     * reference
     */
    public DatabaseDocument(String alias) {
        this(DSL.name(alias), DATABASE_DOCUMENT);
    }

    /**
     * Create an aliased <code>databasir.database_document</code> table
     * reference
     */
    public DatabaseDocument(Name alias) {
        this(alias, DATABASE_DOCUMENT);
    }

    /**
     * Create a <code>databasir.database_document</code> table reference
     */
    public DatabaseDocument() {
        this(DSL.name("database_document"), null);
    }

    public <O extends Record> DatabaseDocument(Table<O> child, ForeignKey<O, DatabaseDocumentRecord> key) {
        super(child, key, DATABASE_DOCUMENT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Databasir.DATABASIR;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.DATABASE_DOCUMENT_IDX_PROJECT_ID);
    }

    @Override
    public Identity<DatabaseDocumentRecord, Integer> getIdentity() {
        return (Identity<DatabaseDocumentRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<DatabaseDocumentRecord> getPrimaryKey() {
        return Keys.KEY_DATABASE_DOCUMENT_PRIMARY;
    }

    @Override
    public DatabaseDocument as(String alias) {
        return new DatabaseDocument(DSL.name(alias), this);
    }

    @Override
    public DatabaseDocument as(Name alias) {
        return new DatabaseDocument(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DatabaseDocument rename(String name) {
        return new DatabaseDocument(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DatabaseDocument rename(Name name) {
        return new DatabaseDocument(name, null);
    }

    // -------------------------------------------------------------------------
    // Row10 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, Integer, String, String, String, String, Long, Boolean, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row10) super.fieldsRow();
    }
}

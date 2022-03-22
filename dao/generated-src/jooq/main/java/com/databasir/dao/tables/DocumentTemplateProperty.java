/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables;


import com.databasir.dao.Databasir;
import com.databasir.dao.Keys;
import com.databasir.dao.tables.records.DocumentTemplatePropertyRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


/**
 * template property
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DocumentTemplateProperty extends TableImpl<DocumentTemplatePropertyRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of
     * <code>databasir.document_template_property</code>
     */
    public static final DocumentTemplateProperty DOCUMENT_TEMPLATE_PROPERTY = new DocumentTemplateProperty();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DocumentTemplatePropertyRecord> getRecordType() {
        return DocumentTemplatePropertyRecord.class;
    }

    /**
     * The column <code>databasir.document_template_property.id</code>.
     */
    public final TableField<DocumentTemplatePropertyRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>databasir.document_template_property.key</code>.
     */
    public final TableField<DocumentTemplatePropertyRecord, String> KEY = createField(DSL.name("key"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>databasir.document_template_property.value</code>.
     */
    public final TableField<DocumentTemplatePropertyRecord, String> VALUE = createField(DSL.name("value"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column
     * <code>databasir.document_template_property.default_value</code>.
     */
    public final TableField<DocumentTemplatePropertyRecord, String> DEFAULT_VALUE = createField(DSL.name("default_value"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>databasir.document_template_property.type</code>.
     */
    public final TableField<DocumentTemplatePropertyRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>databasir.document_template_property.create_at</code>.
     */
    public final TableField<DocumentTemplatePropertyRecord, LocalDateTime> CREATE_AT = createField(DSL.name("create_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private DocumentTemplateProperty(Name alias, Table<DocumentTemplatePropertyRecord> aliased) {
        this(alias, aliased, null);
    }

    private DocumentTemplateProperty(Name alias, Table<DocumentTemplatePropertyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("template property"), TableOptions.table());
    }

    /**
     * Create an aliased <code>databasir.document_template_property</code> table
     * reference
     */
    public DocumentTemplateProperty(String alias) {
        this(DSL.name(alias), DOCUMENT_TEMPLATE_PROPERTY);
    }

    /**
     * Create an aliased <code>databasir.document_template_property</code> table
     * reference
     */
    public DocumentTemplateProperty(Name alias) {
        this(alias, DOCUMENT_TEMPLATE_PROPERTY);
    }

    /**
     * Create a <code>databasir.document_template_property</code> table
     * reference
     */
    public DocumentTemplateProperty() {
        this(DSL.name("document_template_property"), null);
    }

    public <O extends Record> DocumentTemplateProperty(Table<O> child, ForeignKey<O, DocumentTemplatePropertyRecord> key) {
        super(child, key, DOCUMENT_TEMPLATE_PROPERTY);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Databasir.DATABASIR;
    }

    @Override
    public Identity<DocumentTemplatePropertyRecord, Integer> getIdentity() {
        return (Identity<DocumentTemplatePropertyRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<DocumentTemplatePropertyRecord> getPrimaryKey() {
        return Keys.KEY_DOCUMENT_TEMPLATE_PROPERTY_PRIMARY;
    }

    @Override
    public List<UniqueKey<DocumentTemplatePropertyRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_DOCUMENT_TEMPLATE_PROPERTY_UK_TYPE_KEY);
    }

    @Override
    public DocumentTemplateProperty as(String alias) {
        return new DocumentTemplateProperty(DSL.name(alias), this);
    }

    @Override
    public DocumentTemplateProperty as(Name alias) {
        return new DocumentTemplateProperty(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DocumentTemplateProperty rename(String name) {
        return new DocumentTemplateProperty(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public DocumentTemplateProperty rename(Name name) {
        return new DocumentTemplateProperty(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, String, String, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}

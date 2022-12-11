/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables;


import com.databasir.dao.Databasir;
import com.databasir.dao.Keys;
import com.databasir.dao.converter.OAuthAppTypeConverter;
import com.databasir.dao.enums.OAuthAppType;
import com.databasir.dao.tables.records.OauthAppRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * oauth app info
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OauthAppTable extends TableImpl<OauthAppRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>databasir.oauth_app</code>
     */
    public static final OauthAppTable OAUTH_APP = new OauthAppTable();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OauthAppRecord> getRecordType() {
        return OauthAppRecord.class;
    }

    /**
     * The column <code>databasir.oauth_app.id</code>.
     */
    public final TableField<OauthAppRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>databasir.oauth_app.registration_id</code>.
     */
    public final TableField<OauthAppRecord, String> REGISTRATION_ID = createField(DSL.name("registration_id"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>databasir.oauth_app.app_name</code>.
     */
    public final TableField<OauthAppRecord, String> APP_NAME = createField(DSL.name("app_name"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>databasir.oauth_app.app_icon</code>.
     */
    public final TableField<OauthAppRecord, String> APP_ICON = createField(DSL.name("app_icon"), SQLDataType.VARCHAR(256).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>databasir.oauth_app.app_type</code>. github, gitlab
     */
    public final TableField<OauthAppRecord, OAuthAppType> APP_TYPE = createField(DSL.name("app_type"), SQLDataType.VARCHAR(64).nullable(false), this, "github, gitlab", new OAuthAppTypeConverter());

    /**
     * The column <code>databasir.oauth_app.update_at</code>.
     */
    public final TableField<OauthAppRecord, LocalDateTime> UPDATE_AT = createField(DSL.name("update_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>databasir.oauth_app.create_at</code>.
     */
    public final TableField<OauthAppRecord, LocalDateTime> CREATE_AT = createField(DSL.name("create_at"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    private OauthAppTable(Name alias, Table<OauthAppRecord> aliased) {
        this(alias, aliased, null);
    }

    private OauthAppTable(Name alias, Table<OauthAppRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("oauth app info"), TableOptions.table());
    }

    /**
     * Create an aliased <code>databasir.oauth_app</code> table reference
     */
    public OauthAppTable(String alias) {
        this(DSL.name(alias), OAUTH_APP);
    }

    /**
     * Create an aliased <code>databasir.oauth_app</code> table reference
     */
    public OauthAppTable(Name alias) {
        this(alias, OAUTH_APP);
    }

    /**
     * Create a <code>databasir.oauth_app</code> table reference
     */
    public OauthAppTable() {
        this(DSL.name("oauth_app"), null);
    }

    public <O extends Record> OauthAppTable(Table<O> child, ForeignKey<O, OauthAppRecord> key) {
        super(child, key, OAUTH_APP);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Databasir.DATABASIR;
    }

    @Override
    public Identity<OauthAppRecord, Integer> getIdentity() {
        return (Identity<OauthAppRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<OauthAppRecord> getPrimaryKey() {
        return Keys.KEY_OAUTH_APP_PRIMARY;
    }

    @Override
    public List<UniqueKey<OauthAppRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_OAUTH_APP_UK_REGISTRATION_ID);
    }

    @Override
    public OauthAppTable as(String alias) {
        return new OauthAppTable(DSL.name(alias), this);
    }

    @Override
    public OauthAppTable as(Name alias) {
        return new OauthAppTable(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public OauthAppTable rename(String name) {
        return new OauthAppTable(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OauthAppTable rename(Name name) {
        return new OauthAppTable(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, String, String, String, OAuthAppType, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}

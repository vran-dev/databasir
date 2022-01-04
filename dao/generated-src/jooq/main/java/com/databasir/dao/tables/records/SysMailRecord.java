/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables.records;


import com.databasir.dao.tables.SysMail;
import com.databasir.dao.tables.pojos.SysMailPojo;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SysMailRecord extends UpdatableRecordImpl<SysMailRecord> implements Record7<Integer, String, String, String, Integer, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>databasir.sys_mail.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>databasir.sys_mail.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>databasir.sys_mail.username</code>.
     */
    public void setUsername(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>databasir.sys_mail.username</code>.
     */
    public String getUsername() {
        return (String) get(1);
    }

    /**
     * Setter for <code>databasir.sys_mail.password</code>.
     */
    public void setPassword(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>databasir.sys_mail.password</code>.
     */
    public String getPassword() {
        return (String) get(2);
    }

    /**
     * Setter for <code>databasir.sys_mail.smtp_host</code>.
     */
    public void setSmtpHost(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>databasir.sys_mail.smtp_host</code>.
     */
    public String getSmtpHost() {
        return (String) get(3);
    }

    /**
     * Setter for <code>databasir.sys_mail.smtp_port</code>.
     */
    public void setSmtpPort(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>databasir.sys_mail.smtp_port</code>.
     */
    public Integer getSmtpPort() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>databasir.sys_mail.update_at</code>.
     */
    public void setUpdateAt(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>databasir.sys_mail.update_at</code>.
     */
    public LocalDateTime getUpdateAt() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>databasir.sys_mail.create_at</code>.
     */
    public void setCreateAt(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>databasir.sys_mail.create_at</code>.
     */
    public LocalDateTime getCreateAt() {
        return (LocalDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, String, String, String, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Integer, String, String, String, Integer, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return SysMail.SYS_MAIL.ID;
    }

    @Override
    public Field<String> field2() {
        return SysMail.SYS_MAIL.USERNAME;
    }

    @Override
    public Field<String> field3() {
        return SysMail.SYS_MAIL.PASSWORD;
    }

    @Override
    public Field<String> field4() {
        return SysMail.SYS_MAIL.SMTP_HOST;
    }

    @Override
    public Field<Integer> field5() {
        return SysMail.SYS_MAIL.SMTP_PORT;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return SysMail.SYS_MAIL.UPDATE_AT;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return SysMail.SYS_MAIL.CREATE_AT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getUsername();
    }

    @Override
    public String component3() {
        return getPassword();
    }

    @Override
    public String component4() {
        return getSmtpHost();
    }

    @Override
    public Integer component5() {
        return getSmtpPort();
    }

    @Override
    public LocalDateTime component6() {
        return getUpdateAt();
    }

    @Override
    public LocalDateTime component7() {
        return getCreateAt();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getUsername();
    }

    @Override
    public String value3() {
        return getPassword();
    }

    @Override
    public String value4() {
        return getSmtpHost();
    }

    @Override
    public Integer value5() {
        return getSmtpPort();
    }

    @Override
    public LocalDateTime value6() {
        return getUpdateAt();
    }

    @Override
    public LocalDateTime value7() {
        return getCreateAt();
    }

    @Override
    public SysMailRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public SysMailRecord value2(String value) {
        setUsername(value);
        return this;
    }

    @Override
    public SysMailRecord value3(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public SysMailRecord value4(String value) {
        setSmtpHost(value);
        return this;
    }

    @Override
    public SysMailRecord value5(Integer value) {
        setSmtpPort(value);
        return this;
    }

    @Override
    public SysMailRecord value6(LocalDateTime value) {
        setUpdateAt(value);
        return this;
    }

    @Override
    public SysMailRecord value7(LocalDateTime value) {
        setCreateAt(value);
        return this;
    }

    @Override
    public SysMailRecord values(Integer value1, String value2, String value3, String value4, Integer value5, LocalDateTime value6, LocalDateTime value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SysMailRecord
     */
    public SysMailRecord() {
        super(SysMail.SYS_MAIL);
    }

    /**
     * Create a detached, initialised SysMailRecord
     */
    public SysMailRecord(Integer id, String username, String password, String smtpHost, Integer smtpPort, LocalDateTime updateAt, LocalDateTime createAt) {
        super(SysMail.SYS_MAIL);

        setId(id);
        setUsername(username);
        setPassword(password);
        setSmtpHost(smtpHost);
        setSmtpPort(smtpPort);
        setUpdateAt(updateAt);
        setCreateAt(createAt);
    }

    /**
     * Create a detached, initialised SysMailRecord
     */
    public SysMailRecord(SysMailPojo value) {
        super(SysMail.SYS_MAIL);

        if (value != null) {
            setId(value.getId());
            setUsername(value.getUsername());
            setPassword(value.getPassword());
            setSmtpHost(value.getSmtpHost());
            setSmtpPort(value.getSmtpPort());
            setUpdateAt(value.getUpdateAt());
            setCreateAt(value.getCreateAt());
        }
    }
}

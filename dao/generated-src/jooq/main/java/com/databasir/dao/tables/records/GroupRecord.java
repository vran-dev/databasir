/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables.records;


import com.databasir.dao.tables.Group;
import com.databasir.dao.tables.pojos.GroupPojo;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GroupRecord extends UpdatableRecordImpl<GroupRecord> implements Record6<Integer, String, String, Boolean, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>databasir.group.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>databasir.group.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>databasir.group.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>databasir.group.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>databasir.group.description</code>.
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>databasir.group.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>databasir.group.deleted</code>.
     */
    public void setDeleted(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>databasir.group.deleted</code>.
     */
    public Boolean getDeleted() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>databasir.group.update_at</code>.
     */
    public void setUpdateAt(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>databasir.group.update_at</code>.
     */
    public LocalDateTime getUpdateAt() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>databasir.group.create_at</code>.
     */
    public void setCreateAt(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>databasir.group.create_at</code>.
     */
    public LocalDateTime getCreateAt() {
        return (LocalDateTime) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, String, String, Boolean, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, String, String, Boolean, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Group.GROUP.ID;
    }

    @Override
    public Field<String> field2() {
        return Group.GROUP.NAME;
    }

    @Override
    public Field<String> field3() {
        return Group.GROUP.DESCRIPTION;
    }

    @Override
    public Field<Boolean> field4() {
        return Group.GROUP.DELETED;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return Group.GROUP.UPDATE_AT;
    }

    @Override
    public Field<LocalDateTime> field6() {
        return Group.GROUP.CREATE_AT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getDescription();
    }

    @Override
    public Boolean component4() {
        return getDeleted();
    }

    @Override
    public LocalDateTime component5() {
        return getUpdateAt();
    }

    @Override
    public LocalDateTime component6() {
        return getCreateAt();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getDescription();
    }

    @Override
    public Boolean value4() {
        return getDeleted();
    }

    @Override
    public LocalDateTime value5() {
        return getUpdateAt();
    }

    @Override
    public LocalDateTime value6() {
        return getCreateAt();
    }

    @Override
    public GroupRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public GroupRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public GroupRecord value3(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public GroupRecord value4(Boolean value) {
        setDeleted(value);
        return this;
    }

    @Override
    public GroupRecord value5(LocalDateTime value) {
        setUpdateAt(value);
        return this;
    }

    @Override
    public GroupRecord value6(LocalDateTime value) {
        setCreateAt(value);
        return this;
    }

    @Override
    public GroupRecord values(Integer value1, String value2, String value3, Boolean value4, LocalDateTime value5, LocalDateTime value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GroupRecord
     */
    public GroupRecord() {
        super(Group.GROUP);
    }

    /**
     * Create a detached, initialised GroupRecord
     */
    public GroupRecord(Integer id, String name, String description, Boolean deleted, LocalDateTime updateAt, LocalDateTime createAt) {
        super(Group.GROUP);

        setId(id);
        setName(name);
        setDescription(description);
        setDeleted(deleted);
        setUpdateAt(updateAt);
        setCreateAt(createAt);
    }

    /**
     * Create a detached, initialised GroupRecord
     */
    public GroupRecord(GroupPojo value) {
        super(Group.GROUP);

        if (value != null) {
            setId(value.getId());
            setName(value.getName());
            setDescription(value.getDescription());
            setDeleted(value.getDeleted());
            setUpdateAt(value.getUpdateAt());
            setCreateAt(value.getCreateAt());
        }
    }
}
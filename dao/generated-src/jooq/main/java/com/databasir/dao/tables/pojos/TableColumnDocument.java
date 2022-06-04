/*
 * This file is generated by jOOQ.
 */
package com.databasir.dao.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TableColumnDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer       id;
    private Integer       tableDocumentId;
    private Integer       databaseDocumentId;
    private String        name;
    private String        type;
    private Integer       dataType;
    private String        comment;
    private String        defaultValue;
    private Integer       size;
    private Integer       decimalDigits;
    private Boolean       isPrimaryKey;
    private String        nullable;
    private String        autoIncrement;
    private LocalDateTime createAt;

    public TableColumnDocument() {}

    public TableColumnDocument(TableColumnDocument value) {
        this.id = value.id;
        this.tableDocumentId = value.tableDocumentId;
        this.databaseDocumentId = value.databaseDocumentId;
        this.name = value.name;
        this.type = value.type;
        this.dataType = value.dataType;
        this.comment = value.comment;
        this.defaultValue = value.defaultValue;
        this.size = value.size;
        this.decimalDigits = value.decimalDigits;
        this.isPrimaryKey = value.isPrimaryKey;
        this.nullable = value.nullable;
        this.autoIncrement = value.autoIncrement;
        this.createAt = value.createAt;
    }

    public TableColumnDocument(
        Integer       id,
        Integer       tableDocumentId,
        Integer       databaseDocumentId,
        String        name,
        String        type,
        Integer       dataType,
        String        comment,
        String        defaultValue,
        Integer       size,
        Integer       decimalDigits,
        Boolean       isPrimaryKey,
        String        nullable,
        String        autoIncrement,
        LocalDateTime createAt
    ) {
        this.id = id;
        this.tableDocumentId = tableDocumentId;
        this.databaseDocumentId = databaseDocumentId;
        this.name = name;
        this.type = type;
        this.dataType = dataType;
        this.comment = comment;
        this.defaultValue = defaultValue;
        this.size = size;
        this.decimalDigits = decimalDigits;
        this.isPrimaryKey = isPrimaryKey;
        this.nullable = nullable;
        this.autoIncrement = autoIncrement;
        this.createAt = createAt;
    }

    /**
     * Getter for <code>databasir.table_column_document.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>databasir.table_column_document.id</code>.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for
     * <code>databasir.table_column_document.table_document_id</code>.
     */
    public Integer getTableDocumentId() {
        return this.tableDocumentId;
    }

    /**
     * Setter for
     * <code>databasir.table_column_document.table_document_id</code>.
     */
    public void setTableDocumentId(Integer tableDocumentId) {
        this.tableDocumentId = tableDocumentId;
    }

    /**
     * Getter for
     * <code>databasir.table_column_document.database_document_id</code>.
     */
    public Integer getDatabaseDocumentId() {
        return this.databaseDocumentId;
    }

    /**
     * Setter for
     * <code>databasir.table_column_document.database_document_id</code>.
     */
    public void setDatabaseDocumentId(Integer databaseDocumentId) {
        this.databaseDocumentId = databaseDocumentId;
    }

    /**
     * Getter for <code>databasir.table_column_document.name</code>.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>databasir.table_column_document.name</code>.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>databasir.table_column_document.type</code>.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Setter for <code>databasir.table_column_document.type</code>.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for <code>databasir.table_column_document.data_type</code>.
     */
    public Integer getDataType() {
        return this.dataType;
    }

    /**
     * Setter for <code>databasir.table_column_document.data_type</code>.
     */
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    /**
     * Getter for <code>databasir.table_column_document.comment</code>.
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Setter for <code>databasir.table_column_document.comment</code>.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Getter for <code>databasir.table_column_document.default_value</code>.
     */
    public String getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * Setter for <code>databasir.table_column_document.default_value</code>.
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Getter for <code>databasir.table_column_document.size</code>.
     */
    public Integer getSize() {
        return this.size;
    }

    /**
     * Setter for <code>databasir.table_column_document.size</code>.
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Getter for <code>databasir.table_column_document.decimal_digits</code>.
     */
    public Integer getDecimalDigits() {
        return this.decimalDigits;
    }

    /**
     * Setter for <code>databasir.table_column_document.decimal_digits</code>.
     */
    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    /**
     * Getter for <code>databasir.table_column_document.is_primary_key</code>.
     */
    public Boolean getIsPrimaryKey() {
        return this.isPrimaryKey;
    }

    /**
     * Setter for <code>databasir.table_column_document.is_primary_key</code>.
     */
    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    /**
     * Getter for <code>databasir.table_column_document.nullable</code>. YES,
     * NO, UNKNOWN
     */
    public String getNullable() {
        return this.nullable;
    }

    /**
     * Setter for <code>databasir.table_column_document.nullable</code>. YES,
     * NO, UNKNOWN
     */
    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    /**
     * Getter for <code>databasir.table_column_document.auto_increment</code>.
     * YES, NO, UNKNOWN
     */
    public String getAutoIncrement() {
        return this.autoIncrement;
    }

    /**
     * Setter for <code>databasir.table_column_document.auto_increment</code>.
     * YES, NO, UNKNOWN
     */
    public void setAutoIncrement(String autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    /**
     * Getter for <code>databasir.table_column_document.create_at</code>.
     */
    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    /**
     * Setter for <code>databasir.table_column_document.create_at</code>.
     */
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TableColumnDocument (");

        sb.append(id);
        sb.append(", ").append(tableDocumentId);
        sb.append(", ").append(databaseDocumentId);
        sb.append(", ").append(name);
        sb.append(", ").append(type);
        sb.append(", ").append(dataType);
        sb.append(", ").append(comment);
        sb.append(", ").append(defaultValue);
        sb.append(", ").append(size);
        sb.append(", ").append(decimalDigits);
        sb.append(", ").append(isPrimaryKey);
        sb.append(", ").append(nullable);
        sb.append(", ").append(autoIncrement);
        sb.append(", ").append(createAt);

        sb.append(")");
        return sb.toString();
    }
}
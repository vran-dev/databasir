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
public class DatabaseDocumentPojo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer       id;
    private Integer       projectId;
    private String        databaseName;
    private String        productName;
    private String        productVersion;
    private Long          version;
    private Boolean       isArchive;
    private LocalDateTime updateAt;
    private LocalDateTime createAt;

    public DatabaseDocumentPojo() {}

    public DatabaseDocumentPojo(DatabaseDocumentPojo value) {
        this.id = value.id;
        this.projectId = value.projectId;
        this.databaseName = value.databaseName;
        this.productName = value.productName;
        this.productVersion = value.productVersion;
        this.version = value.version;
        this.isArchive = value.isArchive;
        this.updateAt = value.updateAt;
        this.createAt = value.createAt;
    }

    public DatabaseDocumentPojo(
        Integer       id,
        Integer       projectId,
        String        databaseName,
        String        productName,
        String        productVersion,
        Long          version,
        Boolean       isArchive,
        LocalDateTime updateAt,
        LocalDateTime createAt
    ) {
        this.id = id;
        this.projectId = projectId;
        this.databaseName = databaseName;
        this.productName = productName;
        this.productVersion = productVersion;
        this.version = version;
        this.isArchive = isArchive;
        this.updateAt = updateAt;
        this.createAt = createAt;
    }

    /**
     * Getter for <code>databasir.database_document.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>databasir.database_document.id</code>.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>databasir.database_document.project_id</code>.
     */
    public Integer getProjectId() {
        return this.projectId;
    }

    /**
     * Setter for <code>databasir.database_document.project_id</code>.
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * Getter for <code>databasir.database_document.database_name</code>.
     */
    public String getDatabaseName() {
        return this.databaseName;
    }

    /**
     * Setter for <code>databasir.database_document.database_name</code>.
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * Getter for <code>databasir.database_document.product_name</code>.
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * Setter for <code>databasir.database_document.product_name</code>.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Getter for <code>databasir.database_document.product_version</code>.
     */
    public String getProductVersion() {
        return this.productVersion;
    }

    /**
     * Setter for <code>databasir.database_document.product_version</code>.
     */
    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    /**
     * Getter for <code>databasir.database_document.version</code>.
     */
    public Long getVersion() {
        return this.version;
    }

    /**
     * Setter for <code>databasir.database_document.version</code>.
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Getter for <code>databasir.database_document.is_archive</code>.
     */
    public Boolean getIsArchive() {
        return this.isArchive;
    }

    /**
     * Setter for <code>databasir.database_document.is_archive</code>.
     */
    public void setIsArchive(Boolean isArchive) {
        this.isArchive = isArchive;
    }

    /**
     * Getter for <code>databasir.database_document.update_at</code>.
     */
    public LocalDateTime getUpdateAt() {
        return this.updateAt;
    }

    /**
     * Setter for <code>databasir.database_document.update_at</code>.
     */
    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Getter for <code>databasir.database_document.create_at</code>.
     */
    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    /**
     * Setter for <code>databasir.database_document.create_at</code>.
     */
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DatabaseDocumentPojo (");

        sb.append(id);
        sb.append(", ").append(projectId);
        sb.append(", ").append(databaseName);
        sb.append(", ").append(productName);
        sb.append(", ").append(productVersion);
        sb.append(", ").append(version);
        sb.append(", ").append(isArchive);
        sb.append(", ").append(updateAt);
        sb.append(", ").append(createAt);

        sb.append(")");
        return sb.toString();
    }
}

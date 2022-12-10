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
public class OauthAppProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer       id;
    private Integer       oauthAppId;
    private String        name;
    private String        value;
    private LocalDateTime createAt;

    public OauthAppProperty() {}

    public OauthAppProperty(OauthAppProperty value) {
        this.id = value.id;
        this.oauthAppId = value.oauthAppId;
        this.name = value.name;
        this.value = value.value;
        this.createAt = value.createAt;
    }

    public OauthAppProperty(
        Integer       id,
        Integer       oauthAppId,
        String        name,
        String        value,
        LocalDateTime createAt
    ) {
        this.id = id;
        this.oauthAppId = oauthAppId;
        this.name = name;
        this.value = value;
        this.createAt = createAt;
    }

    /**
     * Getter for <code>databasir.oauth_app_property.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>databasir.oauth_app_property.id</code>.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>databasir.oauth_app_property.oauth_app_id</code>.
     * oauth_app.id
     */
    public Integer getOauthAppId() {
        return this.oauthAppId;
    }

    /**
     * Setter for <code>databasir.oauth_app_property.oauth_app_id</code>.
     * oauth_app.id
     */
    public void setOauthAppId(Integer oauthAppId) {
        this.oauthAppId = oauthAppId;
    }

    /**
     * Getter for <code>databasir.oauth_app_property.name</code>.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>databasir.oauth_app_property.name</code>.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>databasir.oauth_app_property.value</code>.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Setter for <code>databasir.oauth_app_property.value</code>.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for <code>databasir.oauth_app_property.create_at</code>.
     */
    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    /**
     * Setter for <code>databasir.oauth_app_property.create_at</code>.
     */
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OauthAppProperty (");

        sb.append(id);
        sb.append(", ").append(oauthAppId);
        sb.append(", ").append(name);
        sb.append(", ").append(value);
        sb.append(", ").append(createAt);

        sb.append(")");
        return sb.toString();
    }
}

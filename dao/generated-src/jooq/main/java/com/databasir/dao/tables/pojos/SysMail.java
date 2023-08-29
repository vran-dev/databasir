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
public class SysMail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer       id;
    private String        username;
    private String        password;
    private String        smtpHost;
    private Integer       smtpPort;
    private Boolean       useSsl;
    private LocalDateTime updateAt;
    private LocalDateTime createAt;
    private String        mailFrom;
    private Boolean       useTls;

    public SysMail() {}

    public SysMail(SysMail value) {
        this.id = value.id;
        this.username = value.username;
        this.password = value.password;
        this.smtpHost = value.smtpHost;
        this.smtpPort = value.smtpPort;
        this.useSsl = value.useSsl;
        this.updateAt = value.updateAt;
        this.createAt = value.createAt;
        this.mailFrom = value.mailFrom;
        this.useTls = value.useTls;
    }

    public SysMail(
        Integer       id,
        String        username,
        String        password,
        String        smtpHost,
        Integer       smtpPort,
        Boolean       useSsl,
        LocalDateTime updateAt,
        LocalDateTime createAt,
        String        mailFrom,
        Boolean       useTls
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.useSsl = useSsl;
        this.updateAt = updateAt;
        this.createAt = createAt;
        this.mailFrom = mailFrom;
        this.useTls = useTls;
    }

    /**
     * Getter for <code>databasir.sys_mail.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>databasir.sys_mail.id</code>.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>databasir.sys_mail.username</code>.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter for <code>databasir.sys_mail.username</code>.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for <code>databasir.sys_mail.password</code>.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter for <code>databasir.sys_mail.password</code>.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for <code>databasir.sys_mail.smtp_host</code>.
     */
    public String getSmtpHost() {
        return this.smtpHost;
    }

    /**
     * Setter for <code>databasir.sys_mail.smtp_host</code>.
     */
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    /**
     * Getter for <code>databasir.sys_mail.smtp_port</code>.
     */
    public Integer getSmtpPort() {
        return this.smtpPort;
    }

    /**
     * Setter for <code>databasir.sys_mail.smtp_port</code>.
     */
    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    /**
     * Getter for <code>databasir.sys_mail.use_ssl</code>.
     */
    public Boolean getUseSsl() {
        return this.useSsl;
    }

    /**
     * Setter for <code>databasir.sys_mail.use_ssl</code>.
     */
    public void setUseSsl(Boolean useSsl) {
        this.useSsl = useSsl;
    }

    /**
     * Getter for <code>databasir.sys_mail.update_at</code>.
     */
    public LocalDateTime getUpdateAt() {
        return this.updateAt;
    }

    /**
     * Setter for <code>databasir.sys_mail.update_at</code>.
     */
    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Getter for <code>databasir.sys_mail.create_at</code>.
     */
    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    /**
     * Setter for <code>databasir.sys_mail.create_at</code>.
     */
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    /**
     * Getter for <code>databasir.sys_mail.mail_from</code>.
     */
    public String getMailFrom() {
        return this.mailFrom;
    }

    /**
     * Setter for <code>databasir.sys_mail.mail_from</code>.
     */
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    /**
     * Getter for <code>databasir.sys_mail.use_tls</code>.
     */
    public Boolean getUseTls() {
        return this.useTls;
    }

    /**
     * Setter for <code>databasir.sys_mail.use_tls</code>.
     */
    public void setUseTls(Boolean useTls) {
        this.useTls = useTls;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SysMail (");

        sb.append(id);
        sb.append(", ").append(username);
        sb.append(", ").append(password);
        sb.append(", ").append(smtpHost);
        sb.append(", ").append(smtpPort);
        sb.append(", ").append(useSsl);
        sb.append(", ").append(updateAt);
        sb.append(", ").append(createAt);
        sb.append(", ").append(mailFrom);
        sb.append(", ").append(useTls);

        sb.append(")");
        return sb.toString();
    }
}

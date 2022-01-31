CREATE DATABASE IF NOT EXISTS databasir;
USE databasir;

CREATE TABLE sys_key
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    rsa_public_key  TEXT      NOT NULL,
    rsa_private_key TEXT      NOT NULL,
    aes_key         TEXT      NOT NULL,
    update_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE sys_mail
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    username  TEXT         NOT NULL,
    password  TEXT         NOT NULL,
    smtp_host VARCHAR(512) NOT NULL,
    smtp_port INT          NOT NULL,
    update_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE user
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    email     VARCHAR(512) NOT NULL,
    username  VARCHAR(128) NOT NULL,
    password  TEXT         NOT NULL,
    nickname  VARCHAR(255) NOT NULL,
    avatar    VARCHAR(512)          DEFAULT NULL,
    enabled   BOOLEAN      NOT NULL DEFAULT FALSE,
    update_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_email (email),
    CONSTRAINT UNIQUE uk_username (username)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE user_role
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    user_id   INT          NOT NULL,
    role      VARCHAR(128) NOT NULL COMMENT 'SYS_OWNER, GROUP_OWNER, GROUP_MEMBER',
    group_id  INT                   DEFAULT NULL,
    create_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_user_id_group_id_role (user_id, group_id, role)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE `group`
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(512) NOT NULL,
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE,
    update_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_name (name)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE `project`
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    group_id    INT          NOT NULL,
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE,
    create_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_group_id_name (group_id, name)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE `project_sync_rule`
(
    id                             INT PRIMARY KEY AUTO_INCREMENT,
    project_id                     INT          NOT NULL,
    ignore_table_name_regex_array  JSON         NOT NULL,
    ignore_column_name_regex_array JSON         NOT NULL,
    is_auto_sync                   BOOLEAN      NOT NULL DEFAULT FALSE,
    auto_sync_cron                 VARCHAR(128) NOT NULL DEFAULT '',
    update_at                      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at                      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_project_id (project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE `data_source`
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    project_id    INT          NOT NULL,
    database_name VARCHAR(512) NOT NULL,
    database_type VARCHAR(255) NOT NULL,
    url           TEXT         NOT NULL,
    username      TEXT         NOT NULL,
    password      TEXT         NOT NULL,
    update_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_project_id (project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE `data_source_property`
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    data_source_id INT       NOT NULL,
    `key`          TEXT      NOT NULL,
    `value`        TEXT      NOT NULL,
    create_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_data_source_id (data_source_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE database_document
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    project_id      INT       NOT NULL,
    database_name   TEXT      NOT NULL,
    product_name    TEXT      NOT NULL,
    product_version TEXT      NOT NULL,
    version         BIGINT    NOT NULL DEFAULT 1,
    update_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_project_id UNIQUE (project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE database_document_history
(
    id                       INT PRIMARY KEY AUTO_INCREMENT,
    project_id               INT       NOT NULL,
    database_document_id     INT       NOT NULL,
    database_document_object JSON               DEFAULT NULL,
    version                  BIGINT    NOT NULL,
    create_at                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_connection_id_version UNIQUE (database_document_id, version),
    INDEX idx_project_id (project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE table_document
(

    id                   INT PRIMARY KEY AUTO_INCREMENT,
    database_document_id INT       NOT NULL,
    name                 TEXT      NOT NULL,
    type                 TEXT      NOT NULL,
    comment              TEXT      NOT NULL,
    create_at            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_database_document_id (database_document_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE table_column_document
(

    id                   INT PRIMARY KEY AUTO_INCREMENT,
    table_document_id    INT          NOT NULL,
    database_document_id INT          NOT NULL,
    name                 TEXT         NOT NULL,
    type                 VARCHAR(255) NOT NULL,
    comment              VARCHAR(512) NOT NULL,
    default_value        VARCHAR(512)          DEFAULT NULL,
    size                 INT          NOT NULL,
    decimal_digits       INT                   DEFAULT NULL,
    nullable             VARCHAR(64)  NOT NULL COMMENT 'YES, NO, UNKNOWN',
    auto_increment       VARCHAR(64)  NOT NULL COMMENT 'YES, NO, UNKNOWN',
    create_at            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_table_document_id (table_document_id),
    INDEX idx_database_document_id (database_document_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE table_index_document
(

    id                   INT PRIMARY KEY AUTO_INCREMENT,
    table_document_id    INT       NOT NULL,
    database_document_id INT       NOT NULL,
    name                 TEXT      NOT NULL,
    is_primary           BOOLEAN   NOT NULL,
    is_unique            BOOLEAN   NOT NULL,
    column_name_array    JSON      NOT NULL,
    create_at            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_table_document_id (table_document_id),
    INDEX idx_database_document_id (database_document_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE table_trigger_document
(

    id                   INT PRIMARY KEY AUTO_INCREMENT,
    table_document_id    INT          NOT NULL,
    database_document_id INT          NOT NULL,
    timing               VARCHAR(64)  NOT NULL,
    manipulation         VARCHAR(128) NOT NULL,
    statement            TEXT         NOT NULL,
    trigger_create_at    VARCHAR(255) NOT NULL,
    create_at            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_table_document_id (table_document_id),
    INDEX idx_database_document_id (database_document_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE login
(
    id                      INT PRIMARY KEY AUTO_INCREMENT,
    user_id                 INT       NOT NULL,
    access_token            TEXT      NOT NULL,
    refresh_token           TEXT      NOT NULL,
    access_token_expire_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    refresh_token_expire_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at               TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_user_id (user_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS document_remark
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    remark      TEXT         NOT NULL,
    user_id     INT          NOT NULL COMMENT 'user.id',
    project_id  INT          NOT NULL,
    table_name  VARCHAR(255) NOT NULL,
    column_name VARCHAR(255)          DEFAULT NULL,
    create_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_project_id (project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;
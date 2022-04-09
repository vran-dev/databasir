CREATE DATABASE IF NOT EXISTS databasir;
USE databasir;

CREATE TABLE IF NOT EXISTS sys_key
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    rsa_public_key  TEXT      NOT NULL,
    rsa_private_key TEXT      NOT NULL,
    aes_key         TEXT      NOT NULL,
    update_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS sys_mail
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    username  TEXT         NOT NULL,
    password  TEXT         NOT NULL,
    smtp_host VARCHAR(512) NOT NULL,
    smtp_port INT          NOT NULL,
    use_ssl   BOOLEAN      NOT NULL DEFAULT FALSE,
    update_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS user
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    email         VARCHAR(512) NOT NULL,
    username      VARCHAR(128) NOT NULL,
    password      TEXT         NOT NULL,
    nickname      VARCHAR(255) NOT NULL,
    avatar        VARCHAR(512)          DEFAULT NULL,
    enabled       BOOLEAN      NOT NULL DEFAULT FALSE,
    deleted       BOOLEAN      NOT NULL DEFAULT FALSE,
    deleted_token INT          NOT NULL DEFAULT 0,
    update_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_email (email, deleted_token),
    CONSTRAINT UNIQUE uk_username (username, deleted_token)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS user_role
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    user_id   INT          NOT NULL,
    role      VARCHAR(128) NOT NULL COMMENT 'SYS_OWNER, GROUP_OWNER, GROUP_MEMBER',
    group_id  INT                   DEFAULT NULL,
    create_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_user_id_group_id_role (user_id, group_id, role)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `group`
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(512) NOT NULL,
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE,
    update_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `project`
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(255) NOT NULL,
    description   TEXT         NOT NULL,
    group_id      INT          NOT NULL,
    deleted       BOOLEAN      NOT NULL DEFAULT FALSE,
    deleted_token INT          NOT NULL DEFAULT 0 COMMENT 'default is 0, it will be set to {id} when deleted',
    create_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_group_id_name_deleted_token (group_id, name, deleted_token)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `project_sync_rule`
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

CREATE TABLE IF NOT EXISTS `data_source`
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    project_id    INT          NOT NULL,
    database_name VARCHAR(255) NOT NULL,
    schema_name   VARCHAR(255) NOT NULL,
    database_type VARCHAR(255) NOT NULL,
    url           TEXT         NOT NULL,
    username      TEXT         NOT NULL,
    password      TEXT         NOT NULL,
    update_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_project_id (project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `data_source_property`
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    data_source_id INT       NOT NULL,
    `key`          TEXT      NOT NULL,
    `value`        TEXT      NOT NULL,
    create_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_data_source_id (data_source_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS database_document
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    project_id      INT          NOT NULL,
    database_name   VARCHAR(255) NOT NULL,
    schema_name     VARCHAR(255) NOT NULL,
    product_name    TEXT         NOT NULL,
    product_version TEXT         NOT NULL,
    version         BIGINT       NOT NULL DEFAULT 1,
    is_archive      BOOLEAN      NOT NULL DEFAULT FALSE,
    update_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_project_id (project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS table_document
(

    id                   INT PRIMARY KEY AUTO_INCREMENT,
    database_document_id INT          NOT NULL,
    name                 TEXT         NOT NULL,
    type                 VARCHAR(255) NOT NULL,
    comment              VARCHAR(512)          DEFAULT NULL,
    create_at            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_database_document_id (database_document_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS table_column_document
(

    id                   INT PRIMARY KEY AUTO_INCREMENT,
    table_document_id    INT          NOT NULL,
    database_document_id INT          NOT NULL,
    name                 TEXT         NOT NULL,
    type                 VARCHAR(255) NOT NULL,
    comment              VARCHAR(512)          DEFAULT NULL,
    default_value        VARCHAR(512)          DEFAULT NULL,
    size                 INT          NOT NULL,
    decimal_digits       INT                   DEFAULT NULL,
    is_primary_key       BOOLEAN      NOT NULL,
    nullable             VARCHAR(64)  NOT NULL COMMENT 'YES, NO, UNKNOWN',
    auto_increment       VARCHAR(64)  NOT NULL COMMENT 'YES, NO, UNKNOWN',
    create_at            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_table_document_id (table_document_id),
    INDEX idx_database_document_id (database_document_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS table_foreign_key_document
(
    id                   INT PRIMARY KEY AUTO_INCREMENT,
    table_document_id    INT          NOT NULL,
    database_document_id INT          NOT NULL,
    key_seq              INT          NOT NULL DEFAULT 0,
    fk_name              VARCHAR(255)          DEFAULT NULL,
    fk_table_name        VARCHAR(512) NOT NULL,
    fk_column_name       VARCHAR(512) NOT NULL,
    pk_name              VARCHAR(255)          DEFAULT NULL,
    pk_table_name        VARCHAR(512) NOT NULL,
    pk_column_name       VARCHAR(512) NOT NULL,
    update_rule          VARCHAR(128) NOT NULL COMMENT 'NO_ACTION, CASCADE, SET_NULL, SET_DEFAULT',
    delete_rule          VARCHAR(128) NOT NULL COMMENT 'NO_ACTION, CASCADE, SET_NULL, SET_DEFAULT',
    create_at            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_table_document_id (table_document_id),
    INDEX idx_database_document_id (database_document_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS table_index_document
(

    id                   INT PRIMARY KEY AUTO_INCREMENT,
    table_document_id    INT       NOT NULL,
    database_document_id INT       NOT NULL,
    name                 TEXT      NOT NULL,
    is_unique            BOOLEAN   NOT NULL,
    column_name_array    JSON      NOT NULL,
    create_at            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_table_document_id (table_document_id),
    INDEX idx_database_document_id (database_document_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS table_trigger_document
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

CREATE TABLE IF NOT EXISTS login
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

CREATE TABLE IF NOT EXISTS document_discussion
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    content     TEXT         NOT NULL,
    user_id     INT          NOT NULL COMMENT 'user.id',
    project_id  INT          NOT NULL,
    table_name  VARCHAR(255) NOT NULL,
    column_name VARCHAR(255)          DEFAULT NULL,
    create_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_project_id (project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS operation_log
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,

    operator_user_id    INT                               NOT NULL COMMENT 'ref to user.id',
    operator_username   VARCHAR(128)                      NOT NULL COMMENT 'user.username',
    operator_nickname   VARCHAR(255)                      NOT NULL COMMENT 'user.nickname',
    operation_module    VARCHAR(128)                      NOT NULL,
    operation_code      VARCHAR(255)                      NOT NULL,
    operation_name      VARCHAR(255)                      NOT NULL,
    operation_response  JSON                              NOT NULL,
    is_success          BOOLEAN                           NOT NULL DEFAULT FALSE,
    involved_project_id INT                                        DEFAULT NULL COMMENT 'ref to project.id',
    involved_group_id   INT                                        DEFAULT NULL COMMENT 'ref to group.id',
    involved_user_id    INT                                        DEFAULT NULL COMMENT 'ref to user.id',
    create_at           TIMESTAMP                         NOT NULL DEFAULT CURRENT_TIMESTAMP
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS user_favorite_project
(
    id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id    INT             NOT NULL,
    project_id INT             NOT NULL,
    create_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE uk_user_id_project_id (user_id, project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS oauth_app
(
    id              INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    registration_id VARCHAR(100)    NOT NULL,
    app_name        VARCHAR(128)    NOT NULL,
    app_icon        VARCHAR(256)    NOT NULL DEFAULT '',
    app_type        VARCHAR(64)     NOT NULL COMMENT 'github, gitlab',
    client_id       VARCHAR(256),
    client_secret   VARCHAR(256),
    auth_url        VARCHAR(256),
    resource_url    VARCHAR(256),
    scope           VARCHAR(256),
    update_at       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE uk_registration_id (registration_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci COMMENT 'oauth app info';

CREATE TABLE IF NOT EXISTS database_type
(
    id                     INT PRIMARY KEY AUTO_INCREMENT,
    database_type          VARCHAR(128)  NOT NULL COMMENT 'such as mysql, postgresql, mysql5.5 and so on',
    icon                   VARCHAR(512)  NOT NULL DEFAULT '',
    description            VARCHAR(512)  NOT NULL,
    jdbc_driver_file_url   VARCHAR(1024) NOT NULL,
    jdbc_driver_class_name VARCHAR(255)  NOT NULL,
    jdbc_protocol          VARCHAR(128)  NOT NULL,
    url_pattern            VARCHAR(255)  NOT NULL,
    deleted                BOOLEAN       NOT NULL DEFAULT FALSE,
    deleted_token          INT           NOT NULL DEFAULT 0,
    update_at              TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at              TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_database_type_deleted_deleted_token UNIQUE (database_type, deleted, deleted_token)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci COMMENT 'customer database types';

REPLACE INTO databasir.database_type (id, database_type, icon, DESCRIPTION, jdbc_driver_file_url,
                                      jdbc_driver_class_name,
                                      jdbc_protocol, url_pattern)
VALUES (1, 'mysql', '', 'system default mysql', 'N/A', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql',
        '{{jdbc.protocol}}://{{db.url}}/{{db.name}}'),
       (2, 'postgresql', '', 'system default postgresql', 'N/A', 'org.postgresql.Driver', 'jdbc:postgresql',
        '{{jdbc.protocol}}://{{db.url}}/{{db.name}}');

CREATE TABLE IF NOT EXISTS document_description
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    content     TEXT         NOT NULL,
    project_id  INT          NOT NULL,
    table_name  VARCHAR(255) NOT NULL,
    column_name VARCHAR(255)          DEFAULT NULL,
    update_by   INT          NOT NULL,
    update_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    create_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_project_id_table_name_column_name (project_id, table_name, column_name)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci COMMENT 'custom document description';

CREATE TABLE IF NOT EXISTS document_template_property
(
    `id`            INT PRIMARY KEY AUTO_INCREMENT,
    `key`           VARCHAR(255) NOT NULL,
    `value`         VARCHAR(255)          DEFAULT NULL,
    `default_value` VARCHAR(255) NOT NULL,
    `type`          VARCHAR(64)  NOT NULL,
    `create_at`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_type_key (`type`, `key`)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci COMMENT 'template property';
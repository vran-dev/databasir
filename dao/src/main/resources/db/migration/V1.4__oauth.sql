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


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
VALUES (1, 'mysql', '', 'system default mysql', 'N/A', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql', '{{jdbc.protocol}}://{{db.url}}/{{db.name}}'),
       (2, 'postgresql', '', 'system default postgresql', 'N/A', 'org.postgresql.Driver', 'jdbc:postgresql', '{{jdbc.protocol}}://{{db.url}}/{{db.name}}');

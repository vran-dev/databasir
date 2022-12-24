create table oauth_app_property
(
    `id`           INT PRIMARY KEY AUTO_INCREMENT,
    `oauth_app_id` int       not null comment 'oauth_app.id',
    `name`         text      not null,
    `value`        text      not null,
    `create_at`    TIMESTAMP not null default CURRENT_TIMESTAMP,
    INDEX idx_oauth_app_id (oauth_app_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;
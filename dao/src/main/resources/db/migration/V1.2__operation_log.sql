CREATE TABLE operation_log
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

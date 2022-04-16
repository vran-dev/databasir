CREATE TABLE IF NOT EXISTS project_sync_task
(
    `id`         INT PRIMARY KEY AUTO_INCREMENT,
    `project_id` INT           NOT NULL,
    `user_id`    INT           NOT NULL,
    `status`     VARCHAR(32)   NOT NULL DEFAULT 'NEW' COMMENT 'NEW \ RUNNING \ FINISHED \ FAILED \ CANCELED',
    `result`     VARCHAR(1024) NOT NULL DEFAULT '',
    `run_at`     DATETIME               DEFAULT NULL,
    `update_at`  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_at`  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_project_id (project_id),
    INDEX idx_user_id (user_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;
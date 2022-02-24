CREATE TABLE IF NOT EXISTS user_favorite_project
(
    id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id    INT             NOT NULL,
    project_id INT             NOT NULL,
    create_at  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE uk_user_id_project_id (user_id, project_id)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

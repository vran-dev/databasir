CREATE TABLE IF NOT EXISTS mock_data_rule
(
    `id`                    INT PRIMARY KEY AUTO_INCREMENT,
    `project_id`            INT          NOT NULL,
    `table_name`            VARCHAR(255) NOT NULL,
    `column_name`           VARCHAR(255) NOT NULL,
    `dependent_table_name`  VARCHAR(255)          DEFAULT NULL,
    `dependent_column_name` VARCHAR(255)          DEFAULT NULL,
    `mock_data_type`        VARCHAR(255) NOT NULL DEFAULT 'AUTO / REF / SCRIPT / PHONE / DATE / TIMESTAMP / ...',
    `mock_data_script`      TEXT                  DEFAULT NULL,
    `update_at`             TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `create_at`             TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UNIQUE uk_project_id_table_name_column_name (project_id, table_name, column_name)
) CHARSET utf8mb4
  COLLATE utf8mb4_unicode_ci;

ALTER TABLE databasir.table_column_document
    ADD COLUMN data_type INT NOT NULL DEFAULT 99999 AFTER type;
ALTER TABLE database_type
    ADD COLUMN jdbc_driver_file_path VARCHAR(512) DEFAULT NULL AFTER jdbc_driver_file_url;
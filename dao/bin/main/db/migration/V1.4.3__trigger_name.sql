ALTER TABLE table_trigger_document
    ADD COLUMN name VARCHAR(1024) DEFAULT '' NOT NULL AFTER id;
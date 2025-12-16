ALTER TABLE database_document
    ADD CONSTRAINT UNIQUE uk_project_id_version_is_archive (project_id, version, is_archive);
ALTER TABLE database_document
    DROP INDEX idx_project_id;
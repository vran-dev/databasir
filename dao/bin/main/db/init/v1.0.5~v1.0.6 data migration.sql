-- migration group info
INSERT INTO document_full_text(group_id, group_name, group_description)
SELECT `group`.id, `group`.name, `group`.description
FROM `group`
         LEFT JOIN document_full_text dft ON `group`.id = dft.group_id
WHERE dft.group_id IS NULL
  AND `group`.deleted = FALSE;

-- migration project info
INSERT INTO document_full_text(group_id, project_id, project_name, project_description, database_name, schema_name,
                               database_type)
SELECT project.group_id,
       project.id,
       project.name,
       project.description,
       ds.database_name,
       ds.schema_name,
       ds.database_type
FROM project
         LEFT JOIN document_full_text ON project.id = document_full_text.project_id
         LEFT JOIN `group` g ON project.group_id = g.id
         LEFT JOIN data_source ds ON project.id = ds.project_id
WHERE project.deleted = FALSE
  AND g.deleted = FALSE
  AND document_full_text.table_document_id IS NULL
  AND document_full_text.project_id IS NULL;


-- migration column
INSERT INTO document_full_text(group_id, project_id, database_document_id, database_document_version,
                               table_document_id, table_column_document_id, database_name, schema_name,
                               database_product_name, table_name, table_comment, col_name, col_comment)
SELECT pj.group_id,
       pj.id,
       dd.id,
       dd.version,
       td.id,
       tcd.id,
       ds.database_type,
       ds.schema_name,
       dd.product_name,
       td.name,
       td.comment,
       tcd.name,
       tcd.comment
FROM table_column_document tcd
         LEFT JOIN document_full_text dft ON dft.table_column_document_id = tcd.id
         INNER JOIN table_document td ON tcd.table_document_id = td.id
         INNER JOIN database_document dd ON tcd.database_document_id = dd.id
         INNER JOIN project pj ON dd.project_id = pj.id
         INNER JOIN data_source ds ON pj.id = ds.project_id
WHERE pj.deleted = FALSE
  AND dd.is_archive = FALSE
  AND dft.table_column_document_id IS NULL
  AND dft.project_id IS NULL;

-- update table description;

UPDATE document_full_text
    LEFT JOIN document_description dd ON document_full_text.project_id = dd.project_id
SET table_description=dd.content
WHERE dd.table_name = document_full_text.table_name
  AND dd.project_id = document_full_text.project_id
  AND dd.column_name IS NULL;

-- update column description;
UPDATE document_full_text
    INNER JOIN document_description dd ON document_full_text.project_id = dd.project_id
SET col_description=dd.content
WHERE dd.table_name = document_full_text.table_name
  AND dd.column_name = document_full_text.col_name
  AND dd.project_id = document_full_text.project_id
  AND dd.column_name IS NOT NULL;

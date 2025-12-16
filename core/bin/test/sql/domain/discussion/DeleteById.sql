INSERT INTO databasir.`group` (id, name, description, deleted, update_at, create_at)
VALUES (-999, '会员平台 ut', '会员平台', 0, '2022-03-06 02:10:40', '2022-03-06 02:10:40');

INSERT INTO databasir.project (id, name, description, group_id, deleted, deleted_token, create_at)
VALUES (-999, 'demo test', 'demo', -999, 0, 1, '2022-03-06 02:11:02');

INSERT INTO databasir.document_discussion(id, content, user_id, project_id, table_name, column_name)
VALUES (-999, 'demo test', -1, -999, 'ut', 'ut');

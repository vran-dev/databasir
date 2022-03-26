INSERT INTO databasir.`group` (id, name, description, deleted, update_at, create_at)
VALUES (-999, '会员平台 ut', '会员平台', 0, '2022-03-06 02:10:40', '2022-03-06 02:10:40');

INSERT INTO databasir.project (id, name, description, group_id, deleted, deleted_token, create_at)
VALUES (-1000, 'demo test2', 'demo', -999, 0, 0, '2022-03-06 02:11:35');

INSERT INTO databasir.project_sync_rule (project_id, ignore_table_name_regex_array, ignore_column_name_regex_array,
                                         is_auto_sync, auto_sync_cron)
VALUES (-1000, '[]', '[]', 1, '0 0/20 * * * ? ');


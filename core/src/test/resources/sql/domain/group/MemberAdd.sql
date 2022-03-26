INSERT INTO databasir.`group` (id, name, description, deleted, update_at, create_at)
VALUES (-999, '会员平台 ut', '会员平台', 0, '2022-03-06 02:10:40', '2022-03-06 02:10:40'),
       (-1000, '会员平台 ut2', '会员平台', 0, '2022-03-06 02:10:40', '2022-03-06 02:10:40');

INSERT INTO databasir.user_role (user_id, role, group_id)
VALUES (-2, 'GROUP_MEMBER', -999),
       (-2, 'GROUP_OWNER', -1000);

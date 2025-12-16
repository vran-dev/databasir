INSERT INTO databasir.`group` (id, name, description, deleted, update_at, create_at)
VALUES (-999, '会员平台 ut', '会员平台', 0, '2022-03-06 02:10:40', '2022-03-06 02:10:40'),
       (-1000, '商品中心 ut', '商品中心', 0, '2022-03-06 02:11:53', '2022-03-06 02:11:53');

INSERT INTO databasir.user_role (user_id, role, group_id)
VALUES (1000, 'GROUP_OWNER', -999),
       (1001, 'GROUP_OWNER', -999),
       (1002, 'GROUP_OWNER', -999),
       (1003, 'GROUP_OWNER', -999),
       (1004, 'GROUP_MEMBER', -999);

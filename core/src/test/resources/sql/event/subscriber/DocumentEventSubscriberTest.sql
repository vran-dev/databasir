REPLACE INTO databasir.user (id, email, username, password, nickname, avatar, enabled)
VALUES (-1, 'jack@databasir.com', 'jack', '$2a$10$8WjfdbbDpzvkz5Rc.8TBk76jDNbQ0zKq', 'Databasir Admin', NULL, 1),
       (-2, 'vran@databasir.com', 'vran', '$2a$10$wXPDzPceCpqYErlZ3DRh.gOpgXXeU1PLXGKRAEW', 'vranssss', NULL, 1),
       (-3, 'test@databasir.com', 'test', '$2a$10$XbnZlQB26cKKGB4WLlgZxxwbUr9tD1Amn/3Tf5H0i', 'test', NULL, 1),
       (-4, 'sample@databasir.com', 'sample', '$2a$10$Ua0zrJ7.HDb6ZIRNWb7fCJiG2OZRTN1.', 'sample', NULL, 1),
       (-5, 'demo@databasir.com', 'demo', '$2a$10$J4JT19JBO3LpWAvRARDxieFtm/pFQtna.dDq', 'demo', NULL, 0);

REPLACE INTO databasir.project (id, name, description, group_id, deleted, deleted_token)
VALUES (-1, 'demo', 'demo', 1, 0, 1),
       (-2, 'no member', 'no member', 1, 0, 1);

REPLACE INTO databasir.`group` (id, name, description, deleted)
VALUES (-1, '会员平台', '会员平台', 0),
       (-2, '成员为空', '会员平台', 0);

REPLACE INTO databasir.user_role (user_id, role, group_id)
VALUES (-1, 'SYS_OWNER', NULL),
       (-1, 'GROUP_OWNER', 1),
       (-2, 'GROUP_OWNER', 1),
       (-3, 'GROUP_MEMBER', 1),
       (-4, 'GROUP_OWNER', 1),
       (-5, 'GROUP_OWNER', 1);

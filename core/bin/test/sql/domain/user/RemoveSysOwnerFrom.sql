INSERT IGNORE INTO databasir.user (id, email, username, password, nickname, avatar, enabled)
VALUES (-998, 'sysOwner@databasir.com', 'sysOwner', '$2a$10$wXPDzPceCXeU1PLXGKRAEW', 'a', NULL, 1),
       (-999, 'notSysOwner@databasir.com', 'notSysOwner', '$2a$10$wXPDzPceU1PLXGKRAEW', 'b', NULL, 1);

INSERT IGNORE INTO databasir.user_role (user_id, role, group_id, create_at)
VALUES (-998, 'SYS_OWNER', NULL, '2022-03-06 02:05:30');

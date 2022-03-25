INSERT IGNORE INTO databasir.user (id, email, username, password, nickname, avatar, enabled)
VALUES (1, 'N/A', 'databasir', '$2a$10$8Wjfdbbk76jDNbQ0zKq', 'Databasir Admin', NULL, 1),
       (2, 'sysOwner@databasir.com', 'sysOwner', '$2a$10$wXPDzPceCXeU1PLXGKRAEW', 'a', NULL, 1),
       (3, 'notSysOwner@databasir.com', 'notSysOwner', '$2a$10$wXPDzPceU1PLXGKRAEW', 'b', NULL, 1);

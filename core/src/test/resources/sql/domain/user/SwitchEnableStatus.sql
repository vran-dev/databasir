INSERT IGNORE INTO databasir.user (id, email, username, password, nickname, avatar, enabled)
VALUES (1, 'N/A', 'databasir', '$2a$10$8WjfdbbDpzvkz5Rc.8TBk76jDNbQ0zKq', 'Databasir Admin', NULL, 1),
       (2, 'vran@databasir.com', 'vran', '$2a$10$wXPDzPceCpqYErlZ3DRh.gOpgXXeU1PLXGKRAEW', 'vranssss', NULL, 1);

INSERT IGNORE INTO databasir.login (user_id, access_token, refresh_token, access_token_expire_at,
                                    refresh_token_expire_at)
VALUES (2, 'eyJ0eXAiOiJKV1QM', '1a884c14ef6542ce0261', '2022-03-12 20:24:28', '2022-03-27 20:09:29');

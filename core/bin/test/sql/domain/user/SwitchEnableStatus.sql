INSERT IGNORE INTO databasir.user (id, email, username, password, nickname, avatar, enabled)
VALUES (-999, 'vran@databasir.com', 'vran', '$2a$10$wXPDzPceCpqYErlZ3DRh.gOpgXXeU1PLXGKRAEW', 'vranssss', NULL, 1);

INSERT IGNORE INTO databasir.login (user_id, access_token, refresh_token, access_token_expire_at,
                                    refresh_token_expire_at)
VALUES (-999, 'eyJ0eXAiOiJKV1QM', '1a884c14ef6542ce0261', '2022-03-12 20:24:28', '2022-03-27 20:09:29');

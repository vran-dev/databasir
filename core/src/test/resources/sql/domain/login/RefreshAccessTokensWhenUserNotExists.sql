INSERT INTO databasir.login (user_id, access_token, refresh_token, access_token_expire_at, refresh_token_expire_at)
VALUES (-1000,
        'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEYXRhYmFzaXIiLCJleHAiOjE2NDcwODc4NjgsInVzZXJuYW1lIjoidGVzdEBkYXRhYmFzaXIuY29tIn0.QXtY0R_2_nqpO2LueY0GFXHR1TCaFj1Y9yl1OoP2yYQ',
        '2a884c14ef654e14b069f8ca32ce0261', DATE_SUB(NOW(), INTERVAL 2 MINUTE), DATE_ADD(NOW(), INTERVAL 1 HOUR));

DELETE FROM role;
INSERT INTO role VALUES (gen_random_uuid(), 'ROLE_ADMIN');
INSERT INTO role VALUES (gen_random_uuid(), 'ROLE_USER');
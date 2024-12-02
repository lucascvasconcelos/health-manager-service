DELETE FROM user_table  WHERE cpf = '12345678901';

INSERT INTO user_table (name, cpf, password, role)
SELECT 'admin', '12345678901', '$2a$10$tzUlwJ33mmp3qZUk9eKnQ.K7vLsn/gzT8ASAAnt6P1x0uxgjr1o1q', 'ROLE_ADMIN';
INSERT INTO users (username, password, email, first_name, last_name) VALUES
('admin', '$2a$10$uCEKH9ZLdHFPF3./YLyLiuIBBJsPdqj8GOc/CUW0buJs8wromYugi', 'admin@bank.com', 'Admin', 'User'),
('user', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE4lBO8r3krJZ1FmK', 'user@bank.com', 'Regular', 'User')
ON CONFLICT (username) DO NOTHING;

INSERT INTO roles (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER')
ON CONFLICT (name) DO NOTHING;

INSERT INTO user_roles (user_id, role_id) VALUES
    ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
    ((SELECT id FROM users WHERE username = 'admin'), (SELECT id FROM roles WHERE name = 'ROLE_USER')),
    ((SELECT id FROM users WHERE username = 'user'), (SELECT id FROM roles WHERE name = 'ROLE_USER'))
ON CONFLICT DO NOTHING;

INSERT INTO cards (card_number, owner, expiry_date, status, balance, user_id) VALUES
  ('encrypted_1234567890123456', 'Ivan Petrov', '2025-12-31', 'ACTIVE', 15000.50,
  (SELECT id FROM users WHERE username = 'user')),
  ('encrypted_2345678901234567', 'Ivan Petrov', '2024-06-30', 'ACTIVE', 5000.00,
   (SELECT id FROM users WHERE username = 'user')),
   ('encrypted_3456789012345678', 'Ivan Petrov', '2023-01-01', 'BLOCKED', 0.00,
   (SELECT id FROM users WHERE username = 'user')),
    ('encrypted_4567890123456789', 'Admin User', '2026-03-31', 'ACTIVE', 100000.00,
    (SELECT id FROM users WHERE username = 'admin')),
    ('encrypted_5678901234567890', 'Admin User', '2025-09-30', 'ACTIVE', 50000.00,
    (SELECT id FROM users WHERE username = 'admin'))
ON CONFLICT (card_number) DO NOTHING;

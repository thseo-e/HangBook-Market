INSERT INTO users (id, name, password, ldap_id) VALUES (1, 'admin', 'admin', 'admin');

INSERT INTO book (id, title, created_user, created_date, updated_user, updated_date, status)
VALUES (1, 'The Catcher in the Rye', 1, '2025-01-01 10:00:00', 1, '2025-01-02 11:00:00', 'AVAILABLE');

INSERT INTO book (id, title, created_user, created_date, updated_user, updated_date, status)
VALUES (2, 'To Kill a Mockingbird', 1, '2025-01-03 12:00:00', 1, '2025-01-04 13:00:00', 'AVAILABLE');

INSERT INTO book (id, title, created_user, created_date, updated_user, updated_date, status)
VALUES (3, '1984', 1, '2025-01-05 14:00:00', 1, '2025-01-06 15:00:00', 'RENTED');

INSERT INTO book (id, title, created_user, created_date, updated_user, updated_date, status)
VALUES (4, 'Pride and Prejudice', 1, '2025-01-07 16:00:00', 1, '2025-01-08 17:00:00', 'AVAILABLE');

INSERT INTO book (id, title, created_user, created_date, updated_user, updated_date, status)
VALUES (5, 'The Great Gatsby', 1, '2025-01-09 18:00:00', 1, '2025-01-10 19:00:00', 'RENTED');

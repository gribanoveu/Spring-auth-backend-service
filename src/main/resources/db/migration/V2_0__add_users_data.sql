INSERT INTO users
(version, email, password, registration_date, position,
 account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES (0, 'admin@email.com', '$2a$12$FyLK5s44fub5XpaTRVtiHuPqUJXQplJD261p2jd7AmlDzrSsINH8i',
        '2023-01-01 00:00:00.000000', 'ADMIN',
        true, true, true, true),
       (0, 'user@email.com', '$2a$12$FyLK5s44fub5XpaTRVtiHuPqUJXQplJD261p2jd7AmlDzrSsINH8i',
        '2023-01-01 00:00:00.000000', 'USER',
        true, true, true, true);

INSERT INTO permission(version, name)
VALUES (0, 'AU_MAIN_INFO_VIEW'),
       (0, 'AU_PERMISSIONS_MANAGEMENT'),
       (0, 'AU_USERS_MANAGEMENT');

INSERT INTO users_permissions(role_id, user_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (3, 2);

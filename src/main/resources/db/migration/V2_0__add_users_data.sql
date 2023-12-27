INSERT INTO users
(version, email, password, birth_date, registration_date, ban_expiration, restriction_reason, role,
 account_non_expired, account_non_locked, credentials_non_expired, enabled)

VALUES
    (0, 'admin@email.com', '$2a$12$FyLK5s44fub5XpaTRVtiHuPqUJXQplJD261p2jd7AmlDzrSsINH8i',
    '2001-01-01', '2023-01-01 00:00:00.000000', null, null,
    'ADMIN', true, true, true, true),

    (0, 'moderator@email.com', '$2a$12$FyLK5s44fub5XpaTRVtiHuPqUJXQplJD261p2jd7AmlDzrSsINH8i',
     '2001-01-01', '2023-01-01 00:00:00.000000', null, null,
     'MODERATOR', true, true, true, true),

    (0, 'user@email.com', '$2a$12$FyLK5s44fub5XpaTRVtiHuPqUJXQplJD261p2jd7AmlDzrSsINH8i',
    '2001-01-01', '2023-01-01 00:00:00.000000', null, null,
     'USER', true, true, true, true);

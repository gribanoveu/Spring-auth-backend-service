CREATE TABLE users
(
    id                      BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    version                 BIGINT      NOT NULL,
    email                   VARCHAR(80) NOT NULL UNIQUE,
    password                VARCHAR(80) NOT NULL,
    birth_date              DATE        NOT NULL,
    registration_date       TIMESTAMP   NOT NULL,
    ban_expiration          TIMESTAMP   NULL,
    restriction_reason      VARCHAR(15) null,
    role                    VARCHAR(15) NOT NULL,
    account_non_expired     BOOLEAN     NOT NULL,
    account_non_locked      BOOLEAN     NOT NULL,
    credentials_non_expired BOOLEAN     NOT NULL,
    enabled                 BOOLEAN     NOT NULL
);

CREATE INDEX idx_is_banned on users (ban_expiration);

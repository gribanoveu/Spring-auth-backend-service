CREATE TABLE users
(
    id                BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    version           INTEGER      NOT NULL,
    email             VARCHAR(30)  NOT NULL UNIQUE,
    password          VARCHAR(80)  NOT NULL,
    position          VARCHAR(30)  NOT NULL,
    registration_date TIMESTAMP(6) NOT NULL,
    account_non_expired     BOOLEAN NOT NULL,
    account_non_locked      BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    enabled                 BOOLEAN NOT NULL
);

CREATE TABLE permission
(
    id      BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    version BIGINT NOT NULL,
    name    VARCHAR(30) UNIQUE
);

create table users_permissions
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES permission,
    FOREIGN KEY (user_id) REFERENCES users
);
CREATE TABLE userdata
(
    id              uuid         NOT NULL PRIMARY KEY,
    email           varchar(255) NOT NULL unique,
    enabled         boolean      NOT NULL,
    name            varchar(255) NOT NULL,
    password        varchar(255) NOT NULL,
    registered_on   bigint       NOT NULL,
    private_profile boolean      NOT NULL
);

CREATE TABLE password_reset_token
(
    id          uuid    NOT NULL PRIMARY KEY,
    expiry_date timestamp    DEFAULT NULL,
    token       varchar(255) DEFAULT NULL,
    user_id     uuid    NOT NULL,
    valid       boolean NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES userdata (id) ON DELETE CASCADE
);

CREATE TABLE posting
(
    id      uuid    NOT NULL PRIMARY KEY,
    link    varchar(255) DEFAULT NULL,
    title   varchar(255) DEFAULT NULL,
    user_id uuid    NOT NULL,
    date    bigint  NOT NULL,
    secret  boolean NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES userdata (id) ON DELETE CASCADE
);

CREATE TABLE posting_tag
(
    posting_id uuid NOT NULL,
    tag_id     uuid NOT NULL,
    PRIMARY KEY (posting_id, tag_id)
);

CREATE TABLE role
(
    id   uuid NOT NULL PRIMARY KEY,
    role varchar(255) DEFAULT NULL
);

CREATE TABLE tag
(
    id   uuid         NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE user_role
(
    user_id uuid NOT NULL,
    role_id uuid NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

-- indices
CREATE INDEX pwd_reset_user_id ON password_reset_token (user_id);
CREATE INDEX posting_user_id ON posting (user_id);
CREATE INDEX posting_tag_tag_id ON posting_tag (tag_id);

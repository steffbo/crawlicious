CREATE TABLE password_reset_token
(
    id          bigint NOT NULL,
    expiry_date timestamp     DEFAULT NULL,
    token       varchar(255) DEFAULT NULL,
    user_id     bigint NOT NULL,
    valid       bit(1) NOT NULL,
    PRIMARY KEY (id)
);
CREATE INDEX pwd_reset_user_id ON password_reset_token(user_id);

CREATE TABLE posting
(
    posting_id bigint NOT NULL,
    link       varchar(255) DEFAULT NULL,
    title      varchar(255) DEFAULT NULL,
    user_id    bigint NOT NULL,
    date       bigint NOT NULL,
    secret     bit(1) NOT NULL,
    PRIMARY KEY (posting_id)
);
CREATE INDEX posting_user_id ON posting(user_id);

CREATE TABLE posting_tag
(
    post_id bigint NOT NULL,
    tag_id  bigint NOT NULL,
    PRIMARY KEY (post_id, tag_id)
);
CREATE INDEX posting_tag_tag_id ON posting_tag(tag_id);

CREATE TABLE role
(
    role_id bigint NOT NULL,
    role    varchar(255) DEFAULT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE tag
(
    tag_id bigint NOT NULL,
    name   varchar(255) NOT NULL,
    PRIMARY KEY (tag_id),
    UNIQUE (name)
);

CREATE TABLE userdata
(
    user_id         bigint NOT NULL,
    email           varchar(255) NOT NULL unique,
    enabled         bit(1)       NOT NULL,
    name            varchar(255) NOT NULL,
    password        varchar(255) NOT NULL,
    registered_on   bigint NOT NULL,
    private_profile bit(1)       NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE user_role
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (user_id, role_id)
);
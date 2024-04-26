create table password_reset_token
(
    id          bigint auto_increment
        primary key,
    expiry_date datetime     null,
    token       varchar(255) null,
    user_id     bigint       not null,
    valid       bit          not null
)
    engine = MyISAM
    charset = utf8;

create index FK5lwtbncug84d4ero33v3cfxvl
    on password_reset_token (user_id);

create table posting
(
    posting_id bigint auto_increment
        primary key,
    link       varchar(255) null,
    title      varchar(255) null,
    user_id    bigint       not null,
    date       bigint       not null,
    secret     bit          not null
)
    engine = MyISAM
    charset = utf8;

create index FK9jcjpid91kqcndm4o267k1too
    on posting (user_id);

create table posting_tag
(
    post_id bigint not null,
    tag_id  bigint not null,
    primary key (post_id, tag_id)
)
    engine = MyISAM
    charset = utf8;

create index FKj3x7uaraccgakr4kam2wec6yo
    on posting_tag (tag_id);

create table role
(
    role_id bigint auto_increment
        primary key,
    role    varchar(255) null
)
    engine = MyISAM
    charset = utf8;

create table tag
(
    tag_id bigint auto_increment
        primary key,
    name   varchar(255) not null,
    constraint UK_qp93jyuw586kcgdajsb3tfbjy
        unique (name)
)
    engine = MyISAM
    charset = utf8;

create table user
(
    user_id         bigint auto_increment
        primary key,
    email           varchar(255) not null,
    enabled         bit          not null,
    name            varchar(255) not null,
    password        varchar(255) not null,
    registered_on   bigint       not null,
    private_profile bit          not null,
    constraint UK_t8tbwelrnviudxdaggwr1kd9b
        unique (email)
)
    engine = MyISAM
    charset = utf8;

create table user_role
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
)
    engine = MyISAM
    charset = utf8;

create index FKa68196081fvovjhkek5m97n3y
    on user_role (role_id);


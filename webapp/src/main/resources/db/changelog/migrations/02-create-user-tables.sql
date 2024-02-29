-- liquibase formatted sql
-- changeset artalexm:create-user-table
create table users
(
    id         uuid         not null primary key,
    username   varchar(50)  not null unique check ( length(username) > 0 ),
    password   varchar(500) not null check ( length(password) > 0 ),
    created_at timestamp    not null
);
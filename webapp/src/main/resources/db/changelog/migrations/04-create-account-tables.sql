-- liquibase formatted sql
-- changeset artalexm:create-account-table
create table accounts
(
    id         uuid      not null primary key,
    user_id    uuid      not null,
    created_at timestamp not null
);

-- changeset artalexm:create-operations-table
create table account_operations
(
    id         uuid           not null,
    account_id uuid           not null,
    amount     numeric(10, 4) not null check ( amount > 0 ),
    type       varchar(50)    not null check ( length(type) > 0 ),
    created_at timestamp      not null,
    updated_at timestamp      not null,
    constraint pk_account_operations primary key (id, account_id),
    constraint fk_operations_to_account foreign key (account_id) references accounts (id)
        on update cascade on delete cascade
);

-- changeset artalexm:create-operations-index
create index ix_operations_account_types on account_operations (account_id, type);
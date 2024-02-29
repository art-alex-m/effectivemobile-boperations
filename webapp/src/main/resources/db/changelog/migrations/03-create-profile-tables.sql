-- liquibase formatted sql
-- changeset artalexm:create-profile-table
create table profiles
(
    id         uuid      not null primary key,
    created_at timestamp not null
);

-- changeset artalexm:create-profile-emails
create table profile_emails
(
    id         uuid         not null primary key,
    profile_id uuid         not null,
    value      varchar(255) not null check ( length(value) > 0 ) unique,
    created_at timestamp    not null,
    updated_at timestamp    not null,
    constraint fk_emails_to_profile foreign key (profile_id) references profiles (id)
        on update cascade on delete cascade
);

-- changeset artalexm:create-profile-phones
create table profile_phones
(
    id         uuid         not null primary key,
    profile_id uuid         not null,
    value      varchar(255) not null check ( length(value) > 0 ) unique,
    created_at timestamp    not null,
    updated_at timestamp    not null,
    constraint fk_phones_to_profile foreign key (profile_id) references profiles (id)
        on update cascade on delete cascade
);

-- changeset artalexm:create-profile-names
create table profile_names
(
    id         uuid         not null primary key,
    profile_id uuid         not null,
    value      varchar(255) not null check ( length(value) > 0 ),
    created_at timestamp    not null,
    updated_at timestamp    not null,
    constraint fk_names_to_profile foreign key (profile_id) references profiles (id)
        on update cascade on delete cascade
);

-- changeset artalexm:create-profile-birthday
create table profile_birthdays
(
    id         uuid      not null primary key,
    profile_id uuid      not null,
    value      timestamp not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    constraint fk_birthday_to_profile foreign key (profile_id) references profiles (id)
        on update cascade on delete cascade
);

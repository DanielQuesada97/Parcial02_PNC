create table book
(
    id               uuid         not null
        primary key,
    author           varchar(255) not null,
    genre            varchar(255) not null,
    isbn             varchar(255) not null
        constraint ukehpdfjpu1jm3hijhj4mm0hx9h
            unique,
    language         varchar(255),
    pages            integer      not null,
    publication_year integer      not null,
    title            varchar(255) not null
);

alter table book
    owner to postgres;

create table permissions
(
    id          uuid                        not null
        primary key,
    created_at  timestamp(6) with time zone not null,
    description varchar(255),
    name        varchar(255)                not null
        constraint ukpnvtwliis6p05pn6i3ndjrqt2
            unique
);

alter table permissions
    owner to postgres;

create table roles
(
    id          uuid                        not null
        primary key,
    created_at  timestamp(6) with time zone not null,
    description varchar(255),
    name        varchar(255)                not null
        constraint ukofx66keruapi6vyqpv6f2or37
            unique
);

alter table roles
    owner to postgres;

create table role_permissions
(
    role_id       uuid not null
        constraint fkn5fotdgk8d1xvo8nav9uv3muc
            references roles,
    permission_id uuid not null
        constraint fkegdk29eiy7mdtefy5c7eirr6e
            references permissions,
    primary key (role_id, permission_id)
);

alter table role_permissions
    owner to postgres;

create table users
(
    id         uuid                        not null
        primary key,
    created_at timestamp(6) with time zone not null,
    email      varchar(255)                not null
        constraint uk6dotkott2kjsp8vw4d0m25fb7
            unique,
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255)                not null,
    status     varchar(255)                not null
        constraint users_status_check
            check ((status)::text = ANY
                   ((ARRAY ['ACTIVE'::character varying, 'INACTIVE'::character varying, 'BLOCKED'::character varying])::text[])),
    updated_at timestamp(6) with time zone,
    username   varchar(255)                not null
        constraint ukr43af9ap4edm43mmtq01oddj6
            unique
);

alter table users
    owner to postgres;

create table refresh_tokens
(
    id          uuid                        not null
        primary key,
    created_at  timestamp(6) with time zone not null,
    expiry_date timestamp(6) with time zone not null,
    is_revoked  boolean                     not null,
    token       varchar(255)                not null
        constraint ukghpmfn23vmxfu3spu3lfg4r2d
            unique,
    user_id     uuid                        not null
        constraint fk1lih5y2npsf8u5o3vhdb9y0os
            references users
);

alter table refresh_tokens
    owner to postgres;

create table user_roles
(
    user_id uuid not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users,
    role_id uuid not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles,
    primary key (user_id, role_id)
);

alter table user_roles
    owner to postgres;



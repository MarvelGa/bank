
CREATE SCHEMA IF NOT EXISTS bank_system;
use bank_system;
DROP TABLE IF EXISTS cards, accounts, clients, managers, users ;

create table users
(
    id         bigint auto_increment
        primary key,
    first_name varchar(25)  not null,
    last_name  varchar(25)  not null,
    email  varchar(25)  not null,
    `passwords`  varchar(25)  not null,
    `role`  varchar(25)  not null,
    birth_day  date         not null
)ENGINE = InnoDB;

create table clients
(
    id         bigint auto_increment
        primary key,
    user_id bigint not null,
    constraint FK_id_user
        foreign key (user_id) references users (id)
            ON DELETE CASCADE
)ENGINE = InnoDB;

create table managers
(
    id         bigint auto_increment
        primary key,
    spacialization varchar(25)  not null,
    user_id bigint not null,
    constraint FK_user_id
        foreign key (user_id) references users (id)
            ON DELETE CASCADE
)ENGINE = InnoDB;

create table accounts
(
    id         bigint auto_increment
        primary key,
    manager_id bigint not null,
    client_id bigint not null,
    constraint FK_manager_id
        foreign key (manager_id) references managers (id) ON DELETE CASCADE,
    constraint FK_client_id
        foreign key (client_id) references clients (id) ON DELETE CASCADE
)ENGINE = InnoDB;

create table cards
(
    id         bigint auto_increment
        primary key,
    `number` varchar(25)  not null,
    amount bigint not null,
    currency varchar(25)  not null,
    account_id bigint not null,
    expiration_date date  not null,
    constraint FK_account_id
        foreign key (account_id) references accounts (id)
            ON DELETE CASCADE
)ENGINE = InnoDB;
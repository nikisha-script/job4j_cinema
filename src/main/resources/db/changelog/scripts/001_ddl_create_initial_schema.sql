----liquibase formatted sql

--changeset nikishin:create_users
create table if not exists users (
    id serial primary key,
    username varchar not null unique,
    email VARCHAR NOT NULL UNIQUE,
    phone VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

--changeset nikishin:create_films
create table if not exists films (
    id serial primary key,
    name varchar,
    description varchar,
    img bytea
);

--changeset nikishin:create_ticket
create table if not exists ticket (
    id serial primary key,
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id int not null references users(id),
    film_id int not null references films(id)
);

--changeset nikishin:alter_ticket_unique
ALTER TABLE ticket
    ADD CONSTRAINT film_id_row_cell UNIQUE (film_id, cell, pos_row);
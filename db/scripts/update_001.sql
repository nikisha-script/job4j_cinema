CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE,
  password VARCHAR NOT NULL
);

ALTER TABLE users
    ADD CONSTRAINT username_email_phone_unique UNIQUE (username, email, phone);

create table films (
    id serial primary key,
    name text,
    description text,
    img bytea
);

create table ticket (
    id serial primary key,
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id int not null references users(id),
    film_id int not null references films(id)
);

ALTER TABLE ticket
    ADD CONSTRAINT film_id_row_cell UNIQUE (film_id, cell, pos_row);
create table users (
    id serial primary key,
    username varchar not null,
    email varchar not null,
    phone varchar not null
);

create table sessions (
  id serial primary key,
  name text
);

create table films (
    id serial primary key,
    name text,
    description text,
    img bytea
);

create table ticket (
    id serial primary key,
    pos_row int not null,
    cell int not null,
    session_id int not null references sessions(id),
    user_id int not null references users(id),
    film_id int not null references films(id)
);
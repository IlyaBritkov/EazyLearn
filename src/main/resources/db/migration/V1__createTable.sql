CREATE TABLE IF NOT EXISTS role
(
    id   integer GENERATED ALWAYS AS IDENTITY,
    name varchar(25) NOT NULL,

    CONSTRAINT role_id_pk PRIMARY KEY (id)
    )
;

CREATE TABLE IF NOT EXISTS users
(
    id       bigint GENERATED ALWAYS AS IDENTITY,
    nickname varchar(35) NOT NULL,
    email    varchar(45) NOT NULL,
    password varchar NOT NULL,
    status varchar(30),
    role_id  integer,

    CONSTRAINT users_id_pk PRIMARY KEY (id),
    CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT email_unique UNIQUE(email)
    )
;

CREATE TABLE IF NOT EXISTS category
(
    id      bigint GENERATED ALWAYS AS IDENTITY,
    name    varchar(30) NOT NULL,
    user_id bigint,

    CONSTRAINT category_id_pk PRIMARY KEY (id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
    )
;

CREATE TABLE IF NOT EXISTS card
(
    id                bigint GENERATED ALWAYS AS IDENTITY,
    foreign_word      varchar          NOT NULL,
    translate_word    varchar          NOT NULL,
    proficiency_level double precision NOT NULL,
    time_addition     bigint           NOT NULL,
    user_id           bigint,
    category_id       bigint,

    CONSTRAINT card_id_pk PRIMARY KEY (id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT category_id_fk FOREIGN KEY (category_id) REFERENCES category (id)
    )
;
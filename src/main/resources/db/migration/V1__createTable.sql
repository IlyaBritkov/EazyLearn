CREATE TABLE IF NOT EXISTS users
(
    id                varchar,
    username          varchar(50) NOT NULL,
    email             varchar(60) NOT NULL,
    password          varchar     NOT NULL,
    registration_date date        NOT NULL,
    avatar_image_path varchar,
    status            varchar(30) NOT NULL,
    role              varchar(30) NOT NULL,

    CONSTRAINT users_id_pk PRIMARY KEY (id),
    CONSTRAINT email_unique UNIQUE (email)
)
;

CREATE TABLE IF NOT EXISTS cardSet
(
    id           varchar,
    name         varchar(30) NOT NULL,
    is_favourite boolean     NOT NULL,
    created_time bigint      NOT NULL,
    user_id      varchar,

    CONSTRAINT cardSet_id_pk PRIMARY KEY (id),
    CONSTRAINT name_per_user_unique UNIQUE (name, user_id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
)
;

CREATE TABLE IF NOT EXISTS card
(
    id                varchar,
    term              varchar(100)     NOT NULL,
    definition        varchar(1000)    NOT NULL,
    proficiency_level double precision NOT NULL,
    created_time      bigint           NOT NULL,
    is_favourite      boolean          NOT NULL,
    user_id           varchar,

    CONSTRAINT card_id_pk PRIMARY KEY (id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
)
;

CREATE TABLE IF NOT EXISTS set_card
(
    set_id  varchar,
    card_id varchar,

    CONSTRAINT set_card_pk PRIMARY KEY (set_id, card_id),
    CONSTRAINT set_id_fk FOREIGN KEY (set_id) REFERENCES cardSet (id),
    CONSTRAINT card_id_fk FOREIGN KEY (card_id) REFERENCES card (id)
)
;
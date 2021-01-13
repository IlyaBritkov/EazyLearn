create table cards
(
    id                bigint not null
        constraint cards_pk
            primary key,
    foreign_word      varchar,
    translate_word    varchar,
    proficiency_level double precision,
    category_id       bigint,
    user_id           bigint

-- TODO add all fields from CardEntity
);


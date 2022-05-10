-- change users table
ALTER TABLE users
    DROP COLUMN registration_date;

ALTER TABLE users
    ADD registration_datetime timestamp without time zone;

ALTER TABLE users
    ADD last_update_datetime timestamp without time zone;

-- change cardSet table
ALTER TABLE cardSet
    DROP COLUMN created_time;

ALTER TABLE cardSet
    ADD created_datetime timestamp without time zone;

ALTER TABLE cardSet
    ADD last_update_datetime timestamp without time zone;

-- change card table
ALTER TABLE card
    DROP COLUMN created_time;

ALTER TABLE card
    DROP COLUMN updated_time;

ALTER TABLE card
    ADD created_datetime timestamp without time zone;

ALTER TABLE card
    ADD last_update_datetime timestamp without time zone;

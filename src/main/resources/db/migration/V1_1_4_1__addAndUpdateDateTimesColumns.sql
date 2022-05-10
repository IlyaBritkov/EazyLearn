-- change users table
ALTER TABLE users
    DROP COLUMN registration_datetime;

ALTER TABLE users
    ADD created_datetime timestamp without time zone;
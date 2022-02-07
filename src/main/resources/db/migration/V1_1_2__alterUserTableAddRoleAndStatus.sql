-- user status
CREATE TABLE status
(
    id   varchar PRIMARY KEY,
    name varchar NOT NULL
);
-- user role
CREATE TABLE role
(
    id   varchar PRIMARY KEY,
    name varchar NOT NULL
);

-- intermediate table for many-to-many relation of user and role
CREATE TABLE user_role
(
    user_id varchar,
    role_id varchar,

    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

-- change users table
ALTER TABLE users
    DROP COLUMN role;

ALTER TABLE users
    DROP COLUMN status;

ALTER TABLE users
    ADD status_id varchar;

ALTER TABLE users
    ADD CONSTRAINT fk_user_status
        FOREIGN KEY (status_id) REFERENCES status (id);

ALTER TABLE card
    ADD updated_time TIMESTAMP WITHOUT TIME ZONE;
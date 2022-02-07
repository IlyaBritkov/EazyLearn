-- change user_role table
ALTER TABLE user_role
    DROP COLUMN role_id;

    ALTER TABLE user_role
        DROP COLUMN user_id;

-- change role table
ALTER TABLE role
    DROP COLUMN id;

ALTER TABLE role
    DROP COLUMN name;

ALTER TABLE role
    ADD name varchar(128) PRIMARY KEY;

-- change user_role table
ALTER TABLE user_role
    ADD COLUMN user_id varchar,
    ADD COLUMN role_name varchar(128),
    ADD PRIMARY KEY (user_id,role_name),
    ADD FOREIGN KEY (role_name) REFERENCES role (name);

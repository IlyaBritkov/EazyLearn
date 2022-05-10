-- clear all tables
CREATE OR REPLACE FUNCTION truncate_tables(username IN VARCHAR) RETURNS void AS
$$
DECLARE
    statements CURSOR FOR
        SELECT tablename
        FROM pg_tables
        WHERE tableowner = username
          AND schemaname = 'public'
          AND tablename != 'flyway_schema_history';
BEGIN
    FOR stmt IN statements
        LOOP
            EXECUTE 'TRUNCATE TABLE ' || quote_ident(stmt.tablename) || ' CASCADE;';
        END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT truncate_tables('postgres');

ALTER TABLE role_authority
    DROP COLUMN role_name;

ALTER TABLE role_authority
    DROP COLUMN authority_name;

ALTER TABLE user_role
    DROP COLUMN role_name;

ALTER TABLE role
    DROP COLUMN name;

ALTER TABLE authority
    DROP COLUMN name;

ALTER TABLE user_role
    DROP COLUMN user_id;

ALTER TABLE role
    ADD COLUMN id   varchar PRIMARY KEY,
    ADD COLUMN name varchar;

ALTER TABLE authority
    ADD COLUMN id   varchar PRIMARY KEY,
    ADD COLUMN name varchar;

ALTER TABLE user_role
    ADD COLUMN user_id varchar,
    ADD COLUMN role_id varchar,
    ADD PRIMARY KEY (user_id, role_id),
    ADD FOREIGN KEY (user_id)
        REFERENCES users (id),
    ADD FOREIGN KEY (role_id)
        REFERENCES role (id);

ALTER TABLE role_authority
    ADD COLUMN role_id      varchar,
    ADD COLUMN authority_id varchar,
    ADD PRIMARY KEY (role_id, authority_id),
    ADD FOREIGN KEY (role_id)
        REFERENCES role (id),
    ADD FOREIGN KEY (authority_id)
        REFERENCES authority (id);
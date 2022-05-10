ALTER TABLE user_role
    DROP CONSTRAINT user_role_user_id_fkey;

ALTER TABLE user_role
    ADD CONSTRAINT user_role_user_id_fkey
        FOREIGN KEY (user_id)
            REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE role_authority
    DROP CONSTRAINT role_name_fk;

ALTER TABLE role_authority
    ADD FOREIGN KEY (role_name)
        REFERENCES role (name) ON DELETE CASCADE;
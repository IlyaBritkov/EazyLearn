-- change users table
ALTER TABLE users
    DROP COLUMN status_id;

ALTER TABLE users
    ADD status varchar;

-- delete status table
DROP TABLE status;
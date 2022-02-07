CREATE TABLE IF NOT EXISTS authority(
    name varchar NOT NULL,
    PRIMARY KEY(name)
);

CREATE TABLE IF NOT EXISTS role_authority(
    role_name  varchar,
    authority_name varchar,

    CONSTRAINT role_authority_pk PRIMARY KEY (role_name, authority_name),
    CONSTRAINT role_name_fk FOREIGN KEY (role_name) REFERENCES role (name),
    CONSTRAINT authority_name_fk FOREIGN KEY (authority_name) REFERENCES authority (name)
)
;
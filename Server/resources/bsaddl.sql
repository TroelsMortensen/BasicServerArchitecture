CREATE SCHEMA bsa_test;
CREATE SCHEMA bsa_production;

SET SCHEMA 'bsa_test';
SET SCHEMA 'bsa_production';

-- v1
CREATE TABLE "user"
(
    id       uuid,
    username VARCHAR
);

-- v2 -- add password to user table
ALTER TABLE "user"
    ADD COLUMN password VARCHAR;

-- v3 add primary key constraint
alter table "user"
add primary key (id);

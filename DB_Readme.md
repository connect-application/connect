DB setup

1. Download PostgreSQL latest version
2. Create a database using:
CREATE DATABASE "connect"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
3. Switch DB using "\c connect"
4. Create a table using:
create table account(name varchar(255), email varchar(255));
5. Insert some records

DB Queries

1. CREATE TYPE user_role_enum AS ENUM ( 'USER', 'ADMIN' );
2. CREATE SEQUENCE email_token_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
3. CREATE TABLE token ( confirmedat timestamp(6) without time zone, createdat timestamp(6) without time zone NOT NULL, expiresat timestamp(6) without time zone NOT NULL, id bigint NOT NULL, user_id bigint NOT NULL, token character varying(255) NOT NULL );
4. CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
5. CREATE TABLE users ( active boolean NOT NULL, dateofbirth date, locked boolean NOT NULL, id bigint NOT NULL, email character varying(255), firstname character varying(255), lastname character varying(255), password character varying(255), username character varying(255), userrole character varying(255), CONSTRAINT users_userrole_check CHECK (((userrole)::text = ANY ((ARRAY['USER'::character varying, 'ADMIN'::character varying])::text[]))) );
6. ALTER TABLE ONLY token ADD CONSTRAINT token_pkey PRIMARY KEY (id);
7. ALTER TABLE ONLY users ADD CONSTRAINT users_pkey PRIMARY KEY (id);
8. ALTER TABLE ONLY token ADD CONSTRAINT fkecxghdteeur3iyqd15ab9yyu7 FOREIGN KEY (user_id) REFERENCES users(id);



Test DB

1. Update application.yml and application.properties files with your DB password
2. Build and run the project
3. Open the following URL: "localhost:8080/api/accounts" and assert your inserted records
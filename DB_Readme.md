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
3. Switch DB using "\c DBNAME"
4. Create a table using:
create table account(name varchar(255), email varchar(255));
5. Insert some records
6. Test DB using "localhost:8080/api/accounts" (Can use Postman)
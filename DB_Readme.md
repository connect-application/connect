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
6. CREATE TABLE users ( id SERIAL PRIMARY KEY, firstName TEXT NOT NULL, lastName TEXT NOT NULL, userName TEXT UNIQUE NOT NULL, email TEXT UNIQUE NOT NULL, password TEXT NOT NULL, dateOfBirth DATE NOT NULL, active BOOLEAN NOT NULL DEFAULT TRUE,createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL );
7. INSERT INTO users (firstName, lastName, userName, email, password, dateOfBirth) VALUES ('John', 'Doe', 'johndoe', 'john@example.com', 'password123', '1990-01-01');

Test DB

1. Update application.yml and application.properties files with your DB password
2. Build and run the project
3. Open the following URL: "localhost:8080/api/accounts" and assert your inserted records
CREATE TABLE users
(
    id       BIGINT PRIMARY KEY,
    username VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128)        NOT NULL

);

CREATE SEQUENCE users_seq START WITH 1
    INCREMENT BY 50;

CREATE INDEX idx_username
    ON users (username);
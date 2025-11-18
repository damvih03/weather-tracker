CREATE TABLE sessions
(
    id         UUID PRIMARY KEY,
    expires_at TIMESTAMP(6) NOT NULL,
    user_id    BIGINT       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
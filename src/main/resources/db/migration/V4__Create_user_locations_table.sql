CREATE TABLE users_locations
(
    user_id      BIGINT      NOT NULL,
    location_id    BIGINT    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (location_id) REFERENCES locations (id),
    PRIMARY KEY(user_id, location_id)
);
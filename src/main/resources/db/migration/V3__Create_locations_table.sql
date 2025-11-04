CREATE TABLE locations
(
    id        BIGINT PRIMARY KEY,
    name      VARCHAR(128) NOT NULL,
    latitude  DECIMAL      NOT NULL,
    longitude DECIMAL      NOT NULL,
    UNIQUE (latitude, longitude)
);

CREATE SEQUENCE locations_seq START WITH 1
    INCREMENT BY 50;
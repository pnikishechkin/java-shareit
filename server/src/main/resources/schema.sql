DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id    INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255)                         NOT NULL,
    email VARCHAR(512)                         NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS requests
(
    id          INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    author_id   BIGINT                               NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    description VARCHAR(255)                         NOT NULL,
    create_date TIMESTAMP WITHOUT TIME ZONE          NOT NULL,
    CONSTRAINT pk_request PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS items
(
    id          INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    owner_id    BIGINT                               NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name        VARCHAR(255)                         NOT NULL,
    description VARCHAR(512)                         NOT NULL,
    available   BOOLEAN                              NOT NULL,
    request_id BIGINT NULL REFERENCES requests (id) ON DELETE CASCADE,
    CONSTRAINT pk_item PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bookings
(
    id         INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    booker_id  BIGINT                               NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    item_id    BIGINT                               NOT NULL REFERENCES items (id) ON DELETE CASCADE,
    status     VARCHAR(255)                         NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE          NOT NULL,
    end_date   TIMESTAMP WITHOUT TIME ZONE          NOT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id        INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    author_id BIGINT                               NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    item_id   BIGINT                               NOT NULL REFERENCES items (id) ON DELETE CASCADE,
    text      VARCHAR(255)                         NOT NULL,
    created   TIMESTAMP WITHOUT TIME ZONE          NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id)
);
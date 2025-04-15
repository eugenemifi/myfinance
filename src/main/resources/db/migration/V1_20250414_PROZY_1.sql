CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS user_types
(
    id   UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO user_types(name)
VALUES ('INDIVIDUAL'),
       ('LEGAL');

CREATE TABLE IF NOT EXISTS transaction_types
(
    id   UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO transaction_types(name)
VALUES ('TOP_UP'),
       ('WITHDRAWAL');

CREATE TABLE IF NOT EXISTS transaction_statuses
(
    id     UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    status VARCHAR(100) NOT NULL
);

INSERT INTO transaction_statuses(status)
VALUES ('NEW'),
       ('CONFIRMED'),
       ('PROCESSING'),
       ('CANCELED'),
       ('SUCCESS'),
       ('REMOVED');

CREATE TABLE IF NOT EXISTS categories
(
    id            UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    category_name VARCHAR(200) NOT NULL,
    category_type VARCHAR(50)  NOT NULL
);

INSERT INTO categories(category_name, category_type)
VALUES ('Зарплата', 'Доход'),
       ('Продажа', 'Доход'),
       ('Покупка', 'Расход'),
       ('Офисные расходы', 'Расход'),
       ('Маркетинг', 'Расход');

CREATE TABLE IF NOT EXISTS banks
(
    bank_id   UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    bank_name VARCHAR(200) NOT NULL,
    bic_code  VARCHAR(20)
);

INSERT INTO banks(bank_name, bic_code)
VALUES ('Сбербанк', '044525225'),
       ('ВТБ', '044525411'),
       ('Тинькофф', '044525974');

CREATE TABLE IF NOT EXISTS users
(
    user_id       UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    login         VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(200) NOT NULL,
    first_name    VARCHAR(100),
    last_name     VARCHAR(100),
    email         VARCHAR(100),
    user_role     VARCHAR(50),
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions
(
    id                     UUID      DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id                UUID      NOT NULL,
    transaction_type_id    UUID      NOT NULL,
    transaction_status_id  UUID      NOT NULL,
    category_id            UUID, -- optional
    transaction_date_time  TIMESTAMP NOT NULL,
    comment                TEXT,
    amount                 NUMERIC(15, 5),
    sender_bank_id         UUID,
    recipient_bank_id      UUID,
    recipient_inn          VARCHAR(20),
    recipient_bank_account VARCHAR(50),
    recipient_phone        VARCHAR(20),
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE RESTRICT,

    CONSTRAINT fk_transaction_type
        FOREIGN KEY (transaction_type_id)
            REFERENCES transaction_types (id)
            ON DELETE RESTRICT,

    CONSTRAINT fk_transaction_status
        FOREIGN KEY (transaction_status_id)
            REFERENCES transaction_statuses (id)
            ON DELETE RESTRICT,

    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE SET NULL,

    CONSTRAINT fk_sender_bank
        FOREIGN KEY (sender_bank_id)
            REFERENCES banks (bank_id)
            ON DELETE SET NULL,

    CONSTRAINT fk_recipient_bank
        FOREIGN KEY (recipient_bank_id)
            REFERENCES banks (bank_id)
            ON DELETE SET NULL
);

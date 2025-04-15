CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS user_types
(
    id UUID PRIMARY KEY,
    name VARCHAR (100) NOT NULL
);

INSERT INTO user_types(name)
VALUES ('INDIVIDUAL'),
       ('LEGAL');

CREATE TABLE IF NOT EXISTS transaction_types(
    id UUID PRIMARY KEY,
    name VARCHAR (100) NOT NULL
);

INSERT INTO transaction_types(name)
VALUES ('TOP_UP'),
       ('WITHDRAWAL');

CREATE TABLE IF NOT EXISTS transaction_statuses (
    id UUID PRIMARY KEY,
    status VARCHAR (100) NOT NULL
);

INSERT INTO transaction_status(status_name)
VALUES ('NEW'),
       ('CONFIRMED'),
       ('PROCESSING'),
       ('CANCELED'),
       ('SUCCESS'),
       ('REMOVED'),
       ('CANCELED');

CREATE TABLE IF NOT EXISTS categories
(
    id
    UUID
    PRIMARY
    KEY,
    category_name
    VARCHAR
(
    200
) NOT NULL,
    category_type VARCHAR
(
    50
) NOT NULL -- Например: 'Доход' или 'Расход'
    );

INSERT INTO category(category_name, category_type)
VALUES ('Зарплата', 'Доход'),
       ('Продажа', 'Доход'),
       ('Покупка', 'Расход'),
       ('Офисные расходы', 'Расход'),
       ('Маркетинг', 'Расход');

-- ======================================================
-- 5) Справочник банков (BANK)
-- ======================================================
CREATE TABLE IF NOT EXISTS bank
(
    bank_id
    SERIAL
    PRIMARY
    KEY,
    bank_name
    VARCHAR
(
    200
) NOT NULL,
    bic_code VARCHAR
(
    20
)
    );

INSERT INTO bank(bank_name, bic_code)
VALUES ('Сбербанк', '044525225'),
       ('ВТБ', '044525411'),
       ('Тинькофф', '044525974');

-- ======================================================
-- 6) Таблица пользователей (USER)
--    (В PostgreSQL слово user может конфликтовать с системной сущностью,
--     поэтому берём в кавычки или используем другое название, например: app_user)
-- ======================================================
CREATE TABLE IF NOT EXISTS "user"
(
    user_id
    SERIAL
    PRIMARY
    KEY,
    login
    VARCHAR
(
    100
) NOT NULL UNIQUE,
    password_hash VARCHAR
(
    200
) NOT NULL,
    first_name VARCHAR
(
    100
),
    last_name VARCHAR
(
    100
),
    email VARCHAR
(
    100
),
    user_role VARCHAR
(
    50
), -- Например, ADMIN / USER и т.д.
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Пример тестового пользователя
INSERT INTO "user"(login, password_hash, first_name, last_name, email, user_role)
VALUES ('testuser', 'hash12345', 'John', 'Doe', 'johndoe@example.com', 'USER');

-- ======================================================
-- 7) Таблица TRANSACTION
--    Связана с пользователем, справочниками и т.д.
-- ======================================================
CREATE TABLE IF NOT EXISTS transaction
(
    transaction_id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    persona_type_id
    INT
    NOT
    NULL,
    transaction_type_id
    INT
    NOT
    NULL,
    transaction_status_id
    INT
    NOT
    NULL,
    category_id
    INT, -- Не всегда обязательно
    transaction_date_time
    TIMESTAMP
    NOT
    NULL,
    comment
    TEXT,
    amount
    NUMERIC
(
    15,
    5
), -- С точностью до 5 знаков
    sender_bank_id INT,
    recipient_bank_id INT,
    recipient_inn VARCHAR
(
    20
),
    recipient_bank_account VARCHAR
(
    50
),
    recipient_phone VARCHAR
(
    20
),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Внешние ключи
    CONSTRAINT fk_user
    FOREIGN KEY
(
    user_id
)
    REFERENCES "user"
(
    user_id
)
    ON DELETE RESTRICT,
    CONSTRAINT fk_persona_type
    FOREIGN KEY
(
    persona_type_id
)
    REFERENCES persona_type
(
    persona_type_id
)
    ON DELETE RESTRICT,
    CONSTRAINT fk_transaction_type
    FOREIGN KEY
(
    transaction_type_id
)
    REFERENCES transaction_type
(
    transaction_type_id
)
    ON DELETE RESTRICT,
    CONSTRAINT fk_transaction_status
    FOREIGN KEY
(
    transaction_status_id
)
    REFERENCES transaction_status
(
    transaction_status_id
)
    ON DELETE RESTRICT,
    CONSTRAINT fk_category
    FOREIGN KEY
(
    category_id
)
    REFERENCES category
(
    category_id
)
    ON DELETE SET NULL,
    CONSTRAINT fk_sender_bank
    FOREIGN KEY
(
    sender_bank_id
)
    REFERENCES bank
(
    bank_id
)
    ON DELETE SET NULL,
    CONSTRAINT fk_recipient_bank
    FOREIGN KEY
(
    recipient_bank_id
)
    REFERENCES bank
(
    bank_id
)
    ON DELETE SET NULL
    );


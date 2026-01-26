CREATE TABLE cards
(
    id                 BIGSERIAL PRIMARY KEY,
    user_id            BIGINT      NOT NULL,
    account_id         BIGINT      NOT NULL,
    card_number_masked VARCHAR(64) NOT NULL,
    card_hash          VARCHAR(64) NOT NULL UNIQUE,
    expiry_date        DATE        NOT NULL,
    status             VARCHAR(20) NOT NULL,

    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active          BOOLEAN   DEFAULT TRUE
);



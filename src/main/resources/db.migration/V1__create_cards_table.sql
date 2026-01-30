CREATE TABLE cards
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    account_id  BIGINT      NOT NULL,

    card_number VARCHAR(64) NOT NULL,
    token       VARCHAR(64) NOT NULL UNIQUE,

    expiry_date DATE        NOT NULL,

    status      VARCHAR(20) NOT NULL,
    card_type   VARCHAR(20),
    user_type   VARCHAR(20),

    pin         VARCHAR(255),
    ccv         VARCHAR(4),

    holder_name VARCHAR     NOT NULL,

    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

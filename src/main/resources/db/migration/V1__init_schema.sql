CREATE TYPE card_status AS ENUM (
    'ACTIVE',
    'INACTIVE',
    'EXPIRED',
    'FROZEN'
);

CREATE TYPE card_network_type AS ENUM (
    'VISA',
    'MASTERCARD',
    'MAESTRO',
    'UZCARD'
);

CREATE TYPE card_type AS ENUM (
    'PERSONAL',
    'CORPORATE'
);

CREATE TABLE cards (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_id UUID NOT NULL,
    user_id UUID NOT NULL,
    card_number varchar(64) NOT NULL UNIQUE,
    token varchar(64) NOT NULL UNIQUE,
    expiry_date date NOT NULL,
    status card_status NOT NULL DEFAULT 'ACTIVE',
    card_network_type card_network_type NOT NULL,
    card_type card_type NOT NULL,
    pin varchar(255) NOT NULL,
    cvv varchar(3) NOT NULL,
    holder_name varchar(255) NOT NULL,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);
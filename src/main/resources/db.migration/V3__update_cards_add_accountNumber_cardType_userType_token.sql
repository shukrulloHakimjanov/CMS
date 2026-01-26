
ALTER TABLE cards
    ADD COLUMN account_number BIGINT NOT NULL;

ALTER TABLE cards
    ADD CONSTRAINT uq_account_number UNIQUE (account_number);

ALTER TABLE cards
    ADD COLUMN card_type VARCHAR(20);

ALTER TABLE cards
    ADD CONSTRAINT chk_card_type CHECK (card_type IN ('VISA', 'MASTERCARD', 'MAESTRO'));

ALTER TABLE cards
    ADD COLUMN user_type VARCHAR(20);

ALTER TABLE cards
    ADD CONSTRAINT chk_user_type CHECK (user_type IN ('PERSONAL', 'CORPORATE'));

ALTER TABLE cards
    ADD COLUMN token VARCHAR(255);

ALTER TABLE cards
    ALTER COLUMN card_type SET DEFAULT 'VISA';
ALTER TABLE cards
    ALTER COLUMN user_type SET DEFAULT 'PERSONAL';

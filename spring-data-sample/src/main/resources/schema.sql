CREATE TABLE IF NOT EXISTS TRANSACTION_TBL (
        id SERIAL primary key,
        account_id VARCHAR(255),
        currency VARCHAR(3),
        amount decimal(10,2),
        descr VARCHAR(255),
        date TIMESTAMP
);
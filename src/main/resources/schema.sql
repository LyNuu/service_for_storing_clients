CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE account (
    currency VARCHAR(50) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    number SERIAL PRIMARY KEY,
    client_id INTEGER NOT NULL,

    CONSTRAINT fk_client
        FOREIGN KEY (client_id)
        REFERENCES client(id)
        ON DELETE CASCADE
);

CREATE SEQUENCE account_sequence
    START WITH 22391143
    INCREMENT BY 1
    MINVALUE 22391143
    NO MAXVALUE
    OWNED BY account.number;
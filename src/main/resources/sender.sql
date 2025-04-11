-- database banking_app_db
--rollback;
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************

DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS users;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

-- users
CREATE TABLE users (
    id SERIAL,
    username varchar(50) NOT NULL UNIQUE,
    email varchar(100) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    role varchar(255) DEFAULT 'USER',
    CONSTRAINT PK_users PRIMARY KEY (id)
);

-- accounts
CREATE TABLE accounts (
    id SERIAL,
    user_id integer,
    balance numeric(10,2) NOT NULL CHECK (balance >= 0),
    account_type varchar(10) NOT NULL DEFAULT 'checking' CHECK (account_type IN ('checking', 'savings', 'business')),
    CONSTRAINT PK_accounts PRIMARY KEY (id),
    CONSTRAINT FK_accounts_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- transactions
CREATE TABLE transactions (
    id SERIAL,
    sender_account_id integer,
    receiver_account_id integer,
    amount numeric(10,2) NOT NULL CHECK (amount > 0),
    "timestamp" timestamp DEFAULT now(),
    CONSTRAINT PK_transactions PRIMARY KEY (id),
    CONSTRAINT FK_transactions_sender FOREIGN KEY (sender_account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    CONSTRAINT FK_transactions_receiver FOREIGN KEY (receiver_account_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Passwords should be hashed in real use
INSERT INTO users (username, email, password, role) VALUES
    ('jdoe', 'jdoe@example.com', 'password123', 'USER'),
    ('admin', 'admin@example.com', 'adminpassword', 'ADMIN');

-- Accounts
INSERT INTO accounts (user_id, balance, account_type) VALUES
    (1, 1000.00, 'checking'),
    (1, 2500.00, 'savings'),
    (2, 10000.00, 'business');

-- Transactions
INSERT INTO transactions (sender_account_id, receiver_account_id, amount) VALUES
    (1, 2, 150.00),
    (3, 1, 500.00),
    (2, 3, 200.00);

COMMIT TRANSACTION;

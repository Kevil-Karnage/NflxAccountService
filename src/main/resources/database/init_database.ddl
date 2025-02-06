CREATE DATABASE Transaction_db;
USE Transaction_db;

CREATE TABLE IF NOT EXISTS Account(
    id      bigint  NOT NULL PRIMARY_KEY,
    balance money   NOT NULL        
);

CREATE TABLE IF NOT EXISTS Transaction(
    id              uuid        NOT NULL PRIMARY KEY,
    timestamp       timestamp   NOT NULL,
    from_account    bigint,
    to_account      bigint,
    amount          money
);
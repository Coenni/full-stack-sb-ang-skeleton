DROP TABLE IF EXISTS regions CASCADE;

CREATE TABLE regions(
   id SERIAL PRIMARY KEY,
   name VARCHAR (250)
);

DROP TABLE IF EXISTS clients CASCADE;

CREATE TABLE clients(
   id SERIAL PRIMARY KEY,
   name VARCHAR (250) NOT NULL,
   lastname VARCHAR (250) NOT NULL,
   email VARCHAR (250) NOT NULL UNIQUE,
   created_at TIMESTAMP NOT NULL,
   photo VARCHAR (250),
   region_id BIGINT NOT NULL,
   FOREIGN KEY (region_id) REFERENCES regions
);

DROP TABLE IF EXISTS invoices CASCADE;

CREATE TABLE invoices(
   id SERIAL PRIMARY KEY,
   description VARCHAR (250),
   note VARCHAR (250),
   created_at DATE,
   client_id BIGINT NOT NULL,
   FOREIGN KEY (client_id) REFERENCES clients
);

DROP TABLE IF EXISTS products CASCADE;

CREATE TABLE products(
   id SERIAL PRIMARY KEY,
   name VARCHAR (250),
   price DOUBLE PRECISION,
   created_at TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS invoice_items CASCADE;

CREATE TABLE invoice_items(
   id SERIAL PRIMARY KEY,
   quantity BIGINT,
   invoice_id BIGINT,
   product_id BIGINT NOT NULL,
   FOREIGN KEY (invoice_id) REFERENCES invoices,
   FOREIGN KEY (product_id) REFERENCES products
);

DROP TABLE IF EXISTS profile_imgs CASCADE;

CREATE TABLE profile_imgs(
   id SERIAL PRIMARY KEY,
   filename VARCHAR (250) NOT NULL UNIQUE,
   file_type VARCHAR (250) NOT NULL,
   img bytea NOT NULL
);

DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS authorities CASCADE;

CREATE TABLE roles(
   id SERIAL PRIMARY KEY,
   authority VARCHAR (30) UNIQUE
);

CREATE TABLE users(
   id SERIAL PRIMARY KEY,
   enabled BOOLEAN,
   username VARCHAR (20) UNIQUE,
   firstname VARCHAR (50),
   lastname VARCHAR (50),
   password VARCHAR (400)
);

CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(256) NOT NULL,
    authority VARCHAR(256) NOT NULL,
    PRIMARY KEY(username, authority)
    );


CREATE TABLE IF NOT EXISTS oauth_client_details (
    client_id VARCHAR(256) PRIMARY KEY,
    resource_ids VARCHAR(256),
    client_secret VARCHAR(256) NOT NULL,
    scope VARCHAR(256),
    authorized_grant_types VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities VARCHAR(256),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4000),
    autoapprove VARCHAR(256)
    );

CREATE TABLE IF NOT EXISTS oauth_client_token (
    token_id VARCHAR(256),
    token VARCHAR(1000),
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name VARCHAR(256),
    client_id VARCHAR(256)
    );

CREATE TABLE IF NOT EXISTS oauth_access_token (
    token_id VARCHAR(256),
    token VARCHAR(1000),
    authentication_id VARCHAR(256),
    user_name VARCHAR(256),
    client_id VARCHAR(256),
    authentication VARCHAR(1000),
    refresh_token VARCHAR(256)
    );

CREATE TABLE IF NOT EXISTS oauth_refresh_token (
    token_id VARCHAR(256),
    token VARCHAR(1000),
    authentication VARCHAR(1000)
    );

CREATE TABLE IF NOT EXISTS oauth_code (
    code VARCHAR(256),
    authentication VARCHAR(1000)
);

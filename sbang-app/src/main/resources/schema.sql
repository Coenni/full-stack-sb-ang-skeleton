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
DROP TABLE IF EXISTS user_roles CASCADE;

CREATE TABLE roles(
   id SERIAL PRIMARY KEY,
   name VARCHAR (30) UNIQUE
);

CREATE TABLE users(
   id SERIAL PRIMARY KEY,
   enabled BOOLEAN,
   username VARCHAR (20) UNIQUE,
   password VARCHAR (60)
);

CREATE TABLE user_roles(
   user_id BIGINT NOT NULL,
   role_id BIGINT NOT NULL,
   PRIMARY KEY (user_id, role_id),
   FOREIGN KEY (role_id) REFERENCES roles,
   FOREIGN KEY (user_id) REFERENCES users
);

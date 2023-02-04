DROP TABLE IF EXISTS regions;

CREATE TABLE regions(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR (250)
);

DROP TABLE IF EXISTS clients;

CREATE TABLE clients(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR (250) NOT NULL,
   lastname VARCHAR (250) NOT NULL,
   email VARCHAR (250) NOT NULL UNIQUE,
   created_at TIMESTAMP NOT NULL,
   photo VARCHAR (250),
   region_id BIGINT NOT NULL,
   FOREIGN KEY (region_id) REFERENCES regions
);

DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   description VARCHAR (250),
   note VARCHAR (250),
   created_at DATE,
   client_id BIGINT NOT NULL,
   FOREIGN KEY (client_id) REFERENCES clients
);

DROP TABLE IF EXISTS products;

CREATE TABLE products(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR (250),
   price DOUBLE,
   created_at TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS invoice_items;

CREATE TABLE invoice_items(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   quantity BIGINT,
   invoice_id BIGINT,
   product_id BIGINT NOT NULL,
   FOREIGN KEY (invoice_id) REFERENCES invoices,
   FOREIGN KEY (product_id) REFERENCES products
);

DROP TABLE IF EXISTS profile_imgs;

CREATE TABLE profile_imgs(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   filename VARCHAR (250) NOT NULL UNIQUE,
   file_type VARCHAR (250) NOT NULL,
   img BLOB NOT NULL
);

DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_roles;

CREATE TABLE roles(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR (30) UNIQUE
);

CREATE TABLE users(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
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

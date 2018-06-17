DROP DATABASE IF EXISTS isp;
CREATE DATABASE IF NOT EXISTS isp;

use isp;

----------------------------------------------------------------
-- ROLE
-- users roles
----------------------------------------------------------------
CREATE TABLE roles(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL PRIMARY KEY,

-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE) 	
	name VARCHAR(10) NOT NULL UNIQUE
);

-- this two commands insert data into roles table
----------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Role entity, so the numeration must started 
-- from 0 with the step equaled to 1
----------------------------------------------------------------
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'client');

----------------------------------------------------------------
-- USERS
----------------------------------------------------------------
CREATE TABLE users(

-- 'generated always AS identity' means id is autoincrement field 
-- (from 1 up to Integer.MAX_VALUE with the step 1)
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	
-- 'UNIQUE' means logins values should not be repeated in login column of table	
	login VARCHAR(10) NOT NULL UNIQUE,
	
-- not null string columns	
	password VARCHAR(10) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	bill INTEGER NOT NULL DEFAULT 0,
	
-- this declaration contains the foreign key constraint	
-- role_id in users table is associated with id in roles table
-- role_id of user = id of role
	role_id INTEGER NOT NULL REFERENCES roles(id) 

-- removing a row with some ID from roles table implies removing 
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in 
-- users table with ROLES_ID=ID
		ON DELETE CASCADE 

-- the same as previous but updating is used insted deleting
		ON UPDATE RESTRICT
);

----------------------------------------------------------------
-- STATUSES
-- statuses for orders
----------------------------------------------------------------
CREATE TABLE statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

----------------------------------------------------------------
-- ATTENTION!!!
-- we use ENUM as the Status entity, so the numeration must started 
-- from 0 with the step equaled to 1
----------------------------------------------------------------
INSERT INTO statuses VALUES(0, 'opened');
INSERT INTO statuses VALUES(1, 'confirmed');
INSERT INTO statuses VALUES(2, 'blocked');
INSERT INTO statuses VALUES(3, 'paid');
INSERT INTO statuses VALUES(4, 'unpaid');

----------------------------------------------------------------
-- TARIFFES
----------------------------------------------------------------
CREATE TABLE tariffes(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL UNIQUE,
	price INTEGER NOT NULL
);

----------------------------------------------------------------
-- SERVICES
----------------------------------------------------------------
CREATE TABLE services(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL UNIQUE,
	id_tariff INTEGER NOT NULL REFERENCES tariffes(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

----------------------------------------------------------------
-- ORDERS
----------------------------------------------------------------
CREATE TABLE orders(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	service_id INTEGER NOT NULL REFERENCES service(id) ON DELETE CASCADE ON UPDATE RESTRICT,
	status_id INTEGER NOT NULL REFERENCES statuses(id) ON DELETE CASCADE ON UPDATE RESTRICT
);


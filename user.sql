create database minizalo;

use minizalo;

drop table users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
	is_logged_in boolean Not null
);

ALTER TABLE users MODIFY is_logged_in BOOLEAN DEFAULT false;
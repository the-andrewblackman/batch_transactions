DROP TABLE IF EXISTS roles CASCADE;

CREATE TABLE roles (
	id SERIAL PRIMARY KEY,
	name VARCHAR(128) NOT NULL
	);
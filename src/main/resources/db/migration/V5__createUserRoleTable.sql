DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles (
    id SERIAL,
    name VARCHAR(50) UNIQUE NOT NULL
);

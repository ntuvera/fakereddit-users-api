DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS user_profiles;
DROP TABLE IF EXISTS user_roles;

CREATE TABLE users (
    id SERIAL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    user_profile_id INT,
    user_role_id INT NOT NULL
);

DROP TABLE IF EXISTS posts;
CREATE TABLE posts (
    id SERIAL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(50),
    user_id INT NOT NULL
);

DROP TABLE IF EXISTS comments;
DROP TYPE IF EXISTS user_obj;

CREATE TABLE comments (
    id SERIAL,
    text VARCHAR(250) NOT NULL,
    post_id INT NOT NULL,
    user_id INT NOT NULL
);

DROP TABLE IF EXISTS user_profiles;
CREATE TABLE user_profiles (
    id SERIAL,
    additional_email VARCHAR(100),
    mobile VARCHAR(50),
    address VARCHAR(50),
    user_id INT
);

DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles (
    id SERIAL,
    name VARCHAR(50) UNIQUE NOT NULL
);

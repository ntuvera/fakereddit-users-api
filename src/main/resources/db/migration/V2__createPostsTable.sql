DROP TABLE IF EXISTS posts;
CREATE TABLE posts (
    id SERIAL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(50),
    user_id INT NOT NULL
);

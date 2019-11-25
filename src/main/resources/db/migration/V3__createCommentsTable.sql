DROP TABLE IF EXISTS comments;
DROP TYPE IF EXISTS user_obj;

CREATE TABLE comments (
    id SERIAL,
    text VARCHAR(250) NOT NULL,
    post_id INT NOT NULL,
    user_id INT NOT NULL
);

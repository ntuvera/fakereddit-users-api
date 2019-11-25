DROP TABLE IF EXISTS user_profiles;
CREATE TABLE user_profiles (
    id SERIAL,
    additional_email VARCHAR(100),
    mobile VARCHAR(50),
    address VARCHAR(50),
    user_id INT
);

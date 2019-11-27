DELETE FROM user_roles WHERE id>0;
INSERT INTO user_roles
(name)
VALUES ('ROLE_DBA'),
('ROLE_ADMIN'),
('ROLE_USER');

INSERT INTO users
(username, password, email, user_profile_id, user_role_id)
VALUES ('batman', 'bat', 'iam@batman.com', null, 2),
('robin', 'bird', 'not@batman.com', null, 3);

INSERT INTO posts
(title, description, user_id)
VALUES ('I am Batman', 'duh',1),
('To the batmobile', 'hey robin', 1),
('Gosh Batman', 'robin says', 2);

INSERT INTO comments
(text, post_id, user_id)
VALUES ('BANG', 1,1),
('ZONK', 1, 1),
('POW', 2, 2);

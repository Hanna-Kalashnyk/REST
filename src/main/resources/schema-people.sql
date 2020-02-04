DROP TABLE IF EXISTS people;
CREATE TABLE people
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(30) NOT NULL,
    email      VARCHAR(30)

);
INSERT INTO people
VALUES (1, 'Herring', 'Herring@gmail.com'),
       (2, 'Malcolm', 'Malcolm@gmail.com'),
       (3, 'Jack', null)
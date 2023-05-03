DROP DATABASE TPJAVADAL;
CREATE DATABASE IF NOT EXISTS TPJAVADAL;
Use TPJAVADAL;

CREATE TABLE IF NOT EXISTS Former (
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    firstName varchar(30),
    lastName varchar(30)
                                  );

CREATE TABLE IF NOT EXISTS Trainee (
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    firstName varchar(30),
    lastName varchar(30)
                                   );

CREATE TABLE IF NOT EXISTS Level(
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name varchar(20)
                                );

CREATE TABLE IF NOT EXISTS Category(
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name varchar(20)
                                   );

CREATE TABLE IF NOT EXISTS TRAINING (
    id INTEGER UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name varchar(30),
    start_at DATE,
    duration INTEGER,
    price INTEGER,
    online boolean,
    level_id INTEGER UNSIGNED,
    category_id INTEGER UNSIGNED,
    FOREIGN KEY (level_id) REFERENCES Level(id),
    FOREIGN KEY (category_id) REFERENCES  Category(id)
                                    );

CREATE TABLE IF NOT EXISTS training_trainee(
    training_id INTEGER UNSIGNED,
    trainee_id INTEGER UNSIGNED,
    PRIMARY KEY (training_id, trainee_id),
    FOREIGN KEY (training_id) REFERENCES training(id) ON DELETE CASCADE,
    FOREIGN KEY (trainee_id) REFERENCES trainee(id) ON DELETE CASCADE
                                           );
CREATE TABLE training_former(
    training_id INTEGER UNSIGNED,
    former_id INTEGER UNSIGNED,
    PRIMARY KEY (training_id, former_id),
    FOREIGN KEY (training_id) REFERENCES training(id) ON DELETE CASCADE,
    FOREIGN KEY (former_id) REFERENCES former(id) ON DELETE CASCADE) ;

INSERT INTO trainee (firstName, lastName) VALUES ('Edgard', 'Edwards');
INSERT INTO trainee (firstName, lastName) VALUES ('Curtis', 'Pena');
INSERT INTO trainee (firstName, lastName) VALUES ('Kelly', 'Hilda');
INSERT INTO trainee (firstName, lastName) VALUES ('Stanley', 'Wade');

INSERT INTO level (name) VALUES ('Facile');
INSERT INTO level (name) VALUES ('Moyen');
INSERT INTO level (name) VALUES ('Difficile');

INSERT INTO category (name) VALUES ('Bureautique');
INSERT INTO category (name) VALUES ('Développement');
INSERT INTO category (name) VALUES ('Design');

INSERT INTO former (firstName, lastName) VALUES ('Yann', 'Radlo');
INSERT INTO former (firstName, lastName) VALUES ('Gustav', 'Ploe');

INSERT INTO training (name, start_at, duration, price, online, level_id, category_id) VALUES ('NodeJS', '2023-8-10', 6, 2500, true, 2, 2);

INSERT INTO training_former (training_id, former_id) VALUES (1, 2);

INSERT INTO training_trainee (training_id, trainee_id) VALUES (1, 1),(1, 2),(1, 3)
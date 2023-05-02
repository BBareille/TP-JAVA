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
                                        type boolean,

                                        level_id INTEGER UNSIGNED,
                                        category_id INTEGER UNSIGNED,
                                        former_id INTEGER UNSIGNED,

                                        FOREIGN KEY (level_id) REFERENCES Level(id),
                                        FOREIGN KEY (category_id) REFERENCES  Category(id),
                                        FOREIGN KEY (former_id) REFERENCES Former(id)
);

CREATE TABLE IF NOT EXISTS training_trainee(
                                               training_id INTEGER UNSIGNED,
                                               trainee_id INTEGER UNSIGNED,

                                               PRIMARY KEY (training_id, trainee_id),

                                               FOREIGN KEY (training_id) REFERENCES training(id),
                                               FOREIGN KEY (trainee_id) REFERENCES trainee(id)
);

CREATE TABLE training_former(
                                training_id INTEGER UNSIGNED,
                                former_id INTEGER UNSIGNED,

                                PRIMARY KEY (training_id, former_id),

                                FOREIGN KEY (training_id) REFERENCES training(id),
                                FOREIGN KEY (former_id) REFERENCES former(id)
);

SELECT * FROM training
                  LEFT JOIN level
                            ON training.id = level_id
                  LEFT JOIN category
                            ON training.id = category_id
                  LEFT JOIN training_former tf
                            on training.id = tf.training_id
                  LEFT JOIN former f on tf.former_id = f.id

GROUP BY TRAINING.id;


SELECT f.id, firstName, lastName FROM TRAINING
                                          LEFT JOIN training_former tf on TRAINING.id = tf.training_id
                                          LEFT JOIN former f on tf.former_id = f.id


CREATE TABLE restrictedWord
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    restricted_word VARCHAR(100)
);
CREATE UNIQUE INDEX restictedWords_id_uindex ON restrictedWord (id);
CREATE UNIQUE INDEX restictedWords_restricted_word_uindex ON restrictedWord (restricted_word);




CREATE TABLE user
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(32)
);
CREATE UNIQUE INDEX users_username_uindex ON user (username);
CREATE TABLE movie_list(
    id INT NOT NULL AUTO_INCREMENT,
    year_ VARCHAR(4) NOT NULL,
    title VARCHAR(255) NOT NULL,
    studios VARCHAR(255) NOT NULL,
    producers VARCHAR(255) NOT NULL,
    winner VARCHAR(3),
    PRIMARY KEY (id)
);
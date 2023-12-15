-- Replace the value of this variable with your file path
SET @initialDataFilePath = '/Users/fseniuk/Downloads/movielist.csv';

INSERT INTO MOVIE_LIST(YEAR_, TITLE, STUDIOS, PRODUCERS, WINNER) SELECT * FROM CSVREAD(@initialDataFilePath, null, 'fieldSeparator=;');
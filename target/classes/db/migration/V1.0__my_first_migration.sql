DROP TABLE IF EXISTS ms_sport;
CREATE TABLE ms_sport(
CODE VARCHAR(2) NOT NULL,
NAME VARCHAR(100) NOT NULL,
PRIMARY KEY(CODE)
);

DROP TABLE IF EXISTS user;
CREATE TABLE user(
ID int NOT NULL AUTO_INCREMENT,
NAME VARCHAR(50) NOT NULL,
SURNAMES VARCHAR(50) NOT NULL,
EMAIL VARCHAR(50) NOT NULL,
PASSWORD VARCHAR(200) NOT NULL,
BORN_DATE DATE NOT NULL,
PRIMARY KEY(ID),
UNIQUE(EMAIL)
);

DROP TABLE IF EXISTS event;
CREATE TABLE event(
ID int NOT NULL AUTO_INCREMENT,
SPORT VARCHAR(2) NOT NULL,
NUMBER_OF_PARTICIPANTS int NOT NULL,
CITY VARCHAR(100) NOT NULL,
PROVINCE VARCHAR(100) NOT NULL,
COUNTRY VARCHAR(100) NOT NULL,
POSTAL_CODE VARCHAR(20) NOT NULL,
DIRECTION VARCHAR(100) NOT NULL,
OBSERVATION VARCHAR(100) NOT NULL,
START_DATE DATETIME NOT NULL,
LATITUDE VARCHAR(100) NOT NULL,
LONGITUDE VARCHAR(100) NOT NULL,
DURATION int NOT NULL,
CREATOR int NOT NULL,
PRIMARY KEY(ID),
FOREIGN KEY (SPORT) REFERENCES ms_sport(CODE),
FOREIGN KEY (CREATOR) REFERENCES user(ID)
);

DROP TABLE IF EXISTS r_event_user;
CREATE TABLE r_event_user(
ID int NOT NULL AUTO_INCREMENT,
EVENT int NOT NULL,
USER int NOT NULL,
PRIMARY KEY(ID),
FOREIGN KEY (EVENT) REFERENCES event(id),
FOREIGN KEY (USER) REFERENCES user(id)
);

INSERT INTO ms_sport (CODE, NAME) VALUES ("FT", "Fútbol");
INSERT INTO ms_sport (CODE, NAME) VALUES ("SE", "Senderismo");
INSERT INTO ms_sport (CODE, NAME) VALUES ("SU", "Surf");
INSERT INTO ms_sport (CODE, NAME) VALUES ("CI", "Ciclismo");
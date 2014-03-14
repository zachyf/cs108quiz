USE c_cs108_schottj;

DROP TABLE IF EXISTS messages;

CREATE TABLE messages (
	fromUser CHAR(20),
    toUser CHAR(20),
    subject CHAR(64),
    note CHAR(255),
    hasRead INT,
    sentTime TIMESTAMP
    );
    
DROP TABLE IF EXISTS friends;

CREATE TABLE friends (
	user1 CHAR(64),
    user2 CHAR(64)
    );
    
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_name CHAR(64),
    password CHAR(64),
    salt INT,
    numCreated INT,
    numPlayed INT,
    highScore TINYINT(1),
    practiceMode TINYINT(1),
    adminStatus TINYINT(1)
    );
    
DROP TABLE IF EXISTS pending;

CREATE TABLE pending (
	user1 CHAR(64),
    user2 CHAR(64),
    pending TINYINT(1)
    );

DROP TABLE IF EXISTS announcements;

CREATE TABLE announcements (
    userName CHAR(64),
    announcement TEXT,
    postTime TIMESTAMP
);

DROP TABLE IF EXISTS challenges;

CREATE TABLE challenges (
    challenger CHAR(64),
    challenged CHAR(64),
    quizID INTEGER,
    pending TINYINT(1),
    sentTime TIMESTAMP
);

DROP TABLE IF EXISTS achievements;

CREATE TABLE achievements (
    userName CHAR(64),
    achievement TEXT,
    timeEarned TIMESTAMP
);



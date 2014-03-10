USE c_cs108_schottj;

DROP TABLE IF EXISTS questions;
 -- remove table if it already exists and start from scratch

CREATE TABLE questions (
	quizID SMALLINT(5),
    question VARCHAR(128),
    answer TEXT,
    questionNum TINYINT(3),
    type VARCHAR(128),
    choice TEXT
    );

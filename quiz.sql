USE c_cs108_jfutrell;

DROP TABLE IF EXISTS quizzes;
 -- remove table if it already exists and start from scratch

CREATE TABLE quizzes (
	id SMALLINT(5),
    name VARCHAR(128),
    description TEXT,
    creatorName VARCHAR(64),
    date DATETIME,
    onePage TINYINT(1),
    isRandomOrder TINYINT(1),
    isImmediate TINYINT(1),
    hasPracticeMode TINYINT(1)
    );
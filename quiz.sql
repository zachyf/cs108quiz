USE c_cs108_schottj;

DROP TABLE IF EXISTS quizzes;
 -- remove table if it already exists and start from scratch

CREATE TABLE quizzes (
	id SMALLINT(5),
    quizName VARCHAR(128),
    description TEXT,
    creatorName VARCHAR(64),
    createtime TIMESTAMP,
    onePage TINYINT(1),
    isRandomOrder TINYINT(1),
    isImmediate TINYINT(1),
    hasPracticeMode TINYINT(1)
    );

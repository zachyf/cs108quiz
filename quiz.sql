USE c_cs108_jfutrell;

DROP TABLE IF EXISTS quizzes;
 -- remove table if it already exists and start from scratch

CREATE TABLE quizzes (
	id SMALLINT(5),
    name VARCHAR(128),
    description TEXT,
    creatorName VARCHAR(64),
    date DATE,
    onePage TINYINT(1),
    isRandomOrder TINYINT(1),
    isImmediate TINYINT(1),
    hasPracticeMode TINYINT(1)
    );


INSERT INTO quizzes VALUES
	(0,"Test Quiz 1","This is a test quiz for testing","JR",2013-12-31,0,0,0,0),
    (1,"Test Quiz 2","This is a test quiz for testing","JR",2014-02-11,0,0,0,0),
    (2,"Test Quiz 4","This is a test quiz for testing","Ethan",2014-02-12,0,0,0,0)

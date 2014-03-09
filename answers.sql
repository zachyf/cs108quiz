USE c_cs108_jfutrell;

DROP TABLE IF EXISTS answers;
 -- remove table if it already exists and start from scratch

CREATE TABLE answers (
	numCorrect SMALLINT(5),
    numQuestions SMALLINT(5),
    timeToComplete BIGINT(15),
    userName VARCHAR(64),
    quizID SMALLINT(5),
    date DATETIME
    );

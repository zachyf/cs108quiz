USE c_cs108_jfutrell;

DROP TABLE IF EXISTS quizRecords;
 -- remove table if it already exists and start from scratch

CREATE TABLE quizRecords (
	numCorrect SMALLINT(5),
    numQuestions SMALLINT(5),
    timeToComplete BIGINT(15), --in miliseconds
    userName VARCHAR(64),
    quizID SMALLINT(5),
    timeSubmitted TIMESTAMP
    );

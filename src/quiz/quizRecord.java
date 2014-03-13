package quiz;

import java.sql.Timestamp;

public class quizRecord {
	private int numCorrect;
	private int numQuestions;
	private int timeToComplete;
	private String userName;
	private int quizID;
	private Timestamp timeSubmitted;
	
	public quizRecord(int nc, int nq, int tc, String u, int qi, Timestamp ts){
		numCorrect = nc;
		numQuestions = nq;
		timeToComplete = tc;
		userName = u;
		quizID = qi;
		timeSubmitted = ts;
	}
	
	public String getUser(){
		return userName;
	}
	
	public int getScore(){
		return (int)(numCorrect*100.0/numQuestions);
	}
	
	public int getQuizID(){
		return quizID;
	}
}


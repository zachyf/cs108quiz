package quiz;

import java.sql.Timestamp;

public class Challenge {
	private String challenger;
	private String challenged;
	private int quizID;
	private boolean pending;
	private Timestamp sentTime;
	
	public Challenge(String cer, String ced, int id, boolean p, Timestamp st){
		challenger = cer;
		challenged = ced;
		quizID = id;
		pending = p;
		sentTime = st; 
	}
	
	public String getChallenger(){
		return challenger;
	}
	
	public String getChallenged(){
		return challenged;
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public boolean getPending(){
		return pending;
	}
	
	public Timestamp getTime(){
		return sentTime;
	}
}

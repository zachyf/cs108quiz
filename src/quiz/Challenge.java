
package quiz;

import java.sql.Timestamp;

public class Challenge {
	private String challenger;
	private String challenged;
	private Integer quizID;
	private boolean pending;
	private Timestamp sentTime;
	
	public Challenge(String cer, String ced,Integer ID, boolean p, Timestamp st){
		challenger = cer;
		challenged = ced;
		quizID = ID;
		pending = p;
		sentTime = st; 
	}
	
	public String getChallenger(){
		return challenger;
	}
	
	public String getChallenged(){
		return challenged;
	}
	
	public Integer getQuizID(){
		return quizID;
	}
	
	public boolean getPending(){
		return pending;
	}
	
	public Timestamp getTime(){
		return sentTime;
	}
}


package quiz;

import java.sql.Timestamp;

public class Challenge {
	private String challenger;
	private String challenged;
	private String quizName;
	private boolean pending;
	private Timestamp sentTime;
	
	public Challenge(String cer, String ced, String name, boolean p, Timestamp st){
		challenger = cer;
		challenged = ced;
		quizName = name;
		pending = p;
		sentTime = st; 
	}
	
	public String getChallenger(){
		return challenger;
	}
	
	public String getChallenged(){
		return challenged;
	}
	
	public String getQuizName(){
		return quizName;
	}
	
	public boolean getPending(){
		return pending;
	}
	
	public Timestamp getTime(){
		return sentTime;
	}
}

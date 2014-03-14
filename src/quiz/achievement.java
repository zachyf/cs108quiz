package quiz;

import java.sql.Timestamp;

public class achievement {
	private String user;
	private String achievementName;
	private Timestamp timeEarned;
	
	public achievement(String u, String an, Timestamp te){
		user = u;
		achievementName = an;
		timeEarned = te;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getAchievementName(){
		return achievementName;
	}
	
	public Timestamp getTime(){
		return timeEarned;
	}
	
}

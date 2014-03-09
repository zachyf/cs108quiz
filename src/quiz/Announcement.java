package quiz;

import java.sql.Timestamp;

public class Announcement {
	private String announcement;
	private String user;
	private Timestamp posted; 

	public Announcement(String a, String u, Timestamp p){
		announcement = a;
		user = u;
		posted = p;
	}
	
	public String getAnnouncement(){
		return announcement;
	}
	
	public String getUser(){
		return user;
	}
	
	public Timestamp getTime(){
		return posted;
	}
}

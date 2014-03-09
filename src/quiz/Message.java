package quiz;

import java.sql.Timestamp;

public class Message {
	private String to;
	private String from;
	private String subject;
	private String message;
	private boolean read;
	private Timestamp sent; 
	
	public Message(String t, String f, String s, String m, Timestamp time, int r){
		this.to = t;
		this.from = f;
		this.subject = s;
		this.message = m;
		this.sent = time;
		if (r == 0)
			this.read = false;
		else
			this.read = true;
	}
	
	public String getTo(){
		return this.to;
	}
	
	public String getFrom(){
		return from;
	}
	
	public String getSubject(){
		return subject;
	}
	
	public String getMessage(){
		return message;
	}
	
	public boolean getRead(){
		return read;
	}
	
	public Timestamp getSentTime(){
		return sent;
	}
	
	public void read(){
		read = true;
	}
}

package quiz;

import java.sql.Timestamp;

public class Message {
	String to;
	String from;
	String subject;
	String message;
	boolean read;
	Timestamp sent; 
	
	public Message(String t, String f, String s, String m, Timestamp time, int r){
		to = t;
		from = f;
		subject = s;
		message = m;
		sent = time;
		if (r == 0)
			read = false;
		else
			read = true;
	}
	
	public String getTo(){
		return to;
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

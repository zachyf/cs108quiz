package quiz;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Time;
import java.util.Random;


public class DBConnection {
	 static String account = MyDBInfo.MYSQL_USERNAME; 
	 static String password = MyDBInfo.MYSQL_PASSWORD; 
	 static String server = MyDBInfo.MYSQL_DATABASE_SERVER; 
	 static String database = MyDBInfo.MYSQL_DATABASE_NAME; 
	 private Connection con;

	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	 public DBConnection() throws SQLException{
	
		 try{
			 Class.forName("com.mysql.jdbc.Driver"); 
	 
			 con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password); 

	 		} catch (SQLException e) { 
	 			// TODO Auto-generated catch block 
	 			e.printStackTrace(); 
	 		} 
		 catch (ClassNotFoundException e) { 
			 // TODO Auto-generated catch block 
			 e.printStackTrace(); 
		 }
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 
		
	 }
	 public Boolean alreadyPending(String user1, String user2) throws SQLException{
		 Statement stmt=null;
			try {
				stmt = con.createStatement();
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			} 
			 try {
				stmt.executeQuery("USE " + database);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			 ResultSet rs=null;
			 try {
				rs = stmt.executeQuery("SELECT * FROM pending where user1=\""+user1+"\" and user2=\""+user2+"\" and pending=TRUE;");
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(rs.next()){ 
				return true;
			}
		 return false;
	 }
	 
	 public void setRequestAsViewed(String user1, String user2) throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 stmt.executeUpdate("UPDATE pending SET pending=FALSE where user1=\""+user1+"\" and user2=\""+user2+"\";");
	 }
	 
	 public ArrayList<String> usersWhoSentRequests(String user) throws SQLException{
		 ArrayList<String> friends = new ArrayList<String>();
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 ResultSet rs =  stmt.executeQuery("SELECT user1 FROM pending where user2=\""+user+"\" and pending=true;");
		 while(rs.next()){ 
			 friends.add(rs.getString("user1"));
		 }
		 return friends;
	 }
	 
	 public void setHighestScore(String user){
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 String q = "Update users set highScore=1 where user_name=\""+user+"\"";
			 stmt.executeUpdate(q);
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }
	 }
    
    public boolean isHighScorer(String user){
        try{
            Statement stmt = con.createStatement();
            stmt.executeQuery("USE " + database);
            String query = "Select userName from quizRecords order by numCorrect  desc, timeToComplete asc limit 5;";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()){
                return rs.getString("username").equals(user);
            }
            return false;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	 
	 public ArrayList<Message> getMessages(String user){
		 ArrayList<Message> m = new ArrayList<Message>();
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 ResultSet rs =  stmt.executeQuery("SELECT * FROM messages where toUser=\""+user+"\" ORDER BY sentTime;");
			 while(rs.next()){
					 m.add(new Message(rs.getString("fromUser"), rs.getString("toUser"), rs.getString("subject"), rs.getString("note"), Timestamp.valueOf(rs.getString("sentTime")), Integer.parseInt(rs.getString("hasRead"))));
			 }
		 } catch (SQLException e) {
	         e.printStackTrace();
		 }
		 return m;
	 }
	 
	 public void bumpNumQuizesTaken(String user){
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 String q = "Update users set numPlayed=numPlayed+1 where user_name=\""+user+"\"";
			 stmt.executeUpdate(q);
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }
	 }
	 public void bumpNumQuizesCreated(String user){
		try{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 String q = "Update users set numCreated=numCreated+1 where user_name=\""+user+"\"";
		 stmt.executeUpdate(q);
	 	}catch(SQLException e) {
	 		e.printStackTrace();
	 	} 
	 }
	 public void insertMessage(Message m){
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 String q = "INSERT into messages VALUES('" + m.getFrom() +"','" + m.getTo() + "','" + m.getSubject() + "','" + m.getMessage() + "',"  + "0, CURRENT_TIMESTAMP);";
			 //String q = "INSERT into messages VALUES(\""+ m.getFrom() +"\",\"" + m.getTo() + "\",\"" + m.getSubject() + "\",\"" + m.getMessage() + "\",\""  + "0, CURRENT_TIMESTAMP);";
			 stmt.executeUpdate(q);
		 }catch (SQLException e) {
	         e.printStackTrace();
		 }
	 }
	 
	 public void readMessage(Message m){
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);

			 String q = "Update messages set hasRead=1 where toUser=\"" + m.getTo() +"\" and fromUser=\"" + m.getFrom() + "\" and sentTime=\"" + m.getSentTime().toString() + "\";";
			 stmt.executeUpdate(q);
		 }catch (SQLException e) {
	         e.printStackTrace();
		 }
	 }
	 
	 public int getNumUnread(String user){
		 ArrayList<Message> m = getMessages(user);
		 int numUnread = 0;
		 for (int i = 0; i < m.size(); ++i){
			 if (!m.get(i).getRead()) ++numUnread;
		 }
		 return numUnread;
	 }
	 
	 public ArrayList<Announcement> getAnnouncements(){
		 ArrayList<Announcement> a = new ArrayList<Announcement>();
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 ResultSet rs =  stmt.executeQuery("SELECT * FROM announcements ORDER BY postTime;");
			 while(rs.next()){
					 a.add(new Announcement(rs.getString("announcement"), rs.getString("admin"),  Timestamp.valueOf(rs.getString("postTime"))));
			 }
		 } catch (SQLException e) {
	         e.printStackTrace();
		 }
		 return a;
	 }
	 
	 public boolean addAnnouncement(Announcement a){
		 String user = a.getUser();
		 try {
			if (!userExists(user) || !isAdmin(user)){
				 return false;
			 }
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 String q = "INSERT into announcements VALUES('" + a.getUser() +"','" + a.getAnnouncement() + "', CURRENT_TIMESTAMP);";
			 stmt.executeUpdate(q);
			 return true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 return false;
	 }
	 
	 public ArrayList<Challenge> getChallenges(String challenged){
		 ArrayList<Challenge> a = new ArrayList<Challenge>();
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 ResultSet rs =  stmt.executeQuery("SELECT * FROM challenges where challenged='" + challenged + "' and pending=1 ORDER BY sentTime;");
			 while(rs.next()){
					 a.add(new Challenge(rs.getString("challenger"), rs.getString("challenged"),  Integer.parseInt(rs.getString("quizID")), true, Timestamp.valueOf(rs.getString("sentTime"))));
			 }
		 } catch (SQLException e) {
	         e.printStackTrace();
		 }
		 return a;
	 }
	 
	 public boolean challengePending(Challenge a){
		 int count = 0;
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 ResultSet rs =  stmt.executeQuery("SELECT * FROM challenges where challenged='" + a.getChallenged() + "' and challenger='" + a.getChallenger() + "' and quizID=" + a.getQuizID() + " and pending=1;");
			 while(rs.next()){
				 count++;
			 }
		 } catch (SQLException e) {
	         e.printStackTrace();
		 }
		 if (count > 0) return true;
		 return false;
	 }
	 
	 public boolean addChallenge(Challenge a){
		 String user = a.getChallenged();
		 try {
			if (!userExists(user) || !isAdmin(user) || challengePending(a)){
				 return false;
			 }
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 String q = "INSERT into challenges VALUES('" + a.getChallenger() +"','" + a.getChallenged() + "',"  + a.getQuizID() + "', 1, CURRENT_TIMESTAMP);";
			 stmt.executeUpdate(q);
			 return true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 return false;
	 }
	 
	 
	 
	 public int removeUser(String username){
		 try{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 String q = "DELETE FROM users WHERE user_name=\""+username + "\";";
		 String r = "DELETE FROM friends WHERE user1=\""+username + "\" or user2=\"" + username + "\";";
		 String s = "DELETE FROM pending WHERE user1=\""+username + "\" or user2=\"" + username + "\";";
		 String t = "DELETE FROM messages WHERE fromUser=\""+username + "\" or toUser=\"" + username + "\";";
	     stmt.executeUpdate(q);
	     stmt.executeUpdate(r);
	     stmt.executeUpdate(s);
	     stmt.executeUpdate(t);
		 }catch(SQLException e) { 
	         e.printStackTrace();
		} 
		 return 0;
	 }
	 
	public ResultSet executeQuery(String query){
			try {
				Statement stmt = this.con.createStatement();
				stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
				return stmt.executeQuery(query);
			} catch (SQLException e) {
		         e.printStackTrace();
			} 
			return null;
		}
	 
	public void updateDB(String insertion){
			try {
				Statement stmt = this.con.createStatement();
				stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
				stmt.executeUpdate(insertion);
			} catch (SQLException e) {
		         e.printStackTrace();
			} 
		}
	 
	 public int getTotalUsers() throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 ResultSet rs =  stmt.executeQuery("SELECT count(user_name) FROM users;");
		 if(rs.next()){ 
			 return rs.getInt("count(user_name)");
		 }else{
			 return -1;
		 }
	 }
	 
	 public int getNumRequests(String user) throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 ResultSet rs =  stmt.executeQuery("SELECT count(user1) FROM pending where user2=\""+user+"\" and pending=true;");
		 if(rs.next()){ 
			 return rs.getInt("count(user1)");
		 }else{
			 return -1;
		 }
	 }
	 
	 public Boolean alreadyFriends(String user1, String user2) throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 ResultSet rs =  stmt.executeQuery("SELECT * FROM friends where user1=\""+user1+"\" and user2=\""+user2+"\";");
		 if(rs.next()){ 
				return true;
		}else{
			return false;
		}
		 
	 }
	 public void addRequest(String user1,String user2) throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 stmt.executeUpdate("INSERT INTO pending VALUES(\""+user1+"\",\""+user2+"\",TRUE);");
			
	 }
	 public Message findMessage(String Time, String To, String From) throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 ResultSet rs=null;
		 try {
				rs = stmt.executeQuery("SELECT * FROM messages where toUser=\""+To+"\" and fromUser=\""+From+"\" and sentTime=\""+Time+"\";");
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		 	Message m = null;
			if(rs.next()){ 
				m = new Message(rs.getString("toUser"),rs.getString("fromUser"),rs.getString("subject"),rs.getString("note"),Timestamp.valueOf(rs.getString("sentTime")),rs.getInt("hasRead"));
			}
			return m;
		 
	 }
	 public Boolean isAdmin(String user) throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 ResultSet rs =  stmt.executeQuery("SELECT * FROM users where user_name=\""+user+"\" and adminStatus=true;");
		 if(rs.next()){ 
				return true;
		}else{
			return false;
		}
	 }
	 
	 public void addFriend(String user1,String user2) throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 stmt.executeUpdate("INSERT INTO friends VALUES(\""+user1+"\",\""+user2+"\");");
			
	 }
	 
	 public Boolean userExists(String account) throws SQLException{
		 Statement stmt=null;
			try {
				stmt = con.createStatement();
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			} 
			 try {
				stmt.executeQuery("USE " + database);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			 ResultSet rs=null;
			 try {
				rs = stmt.executeQuery("SELECT * FROM users where user_name=\""+account+"\";");
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(rs.next()){ 
				return true;
			}
		 return false;
	 }
	 
	 public Boolean passwordCheck(String password, String account){
		Statement stmt=null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		} 
		 try {
			stmt.executeQuery("USE " + database);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		 ResultSet rs=null;
		 try {
			rs = stmt.executeQuery("SELECT password,salt FROM users where user_name=\""+account+"\";");
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 String passSalt=null;
		try {
			if(rs.next()){
				passSalt = password + Integer.toString(rs.getInt("salt"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 String hash = null;
			try{
				MessageDigest md = MessageDigest.getInstance("SHA");
			    md.update(passSalt.getBytes());
			    byte[] digest = md.digest();
			    hash =hexToString(digest);
			}catch(NoSuchAlgorithmException e){
				e.printStackTrace();
		}
		 try {
			if(hash.equals(rs.getString("password"))){
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return false;
	}
	public Integer numQuizesTaken(String account) throws SQLException{
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT numPlayed FROM users where user_name=\""+account+"\";");
		if(rs.next()){
			return rs.getInt("numPlayed");
		}
		return -1;
	}
	
	public Integer numQuizesCreated(String account) throws SQLException{
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT numCreated FROM users where user_name=\""+account+"\";");
		if(rs.next()){
			return rs.getInt("numCreated");
		}
		return -1;
	}
	
	public ArrayList<Quiz> getQuizzes(){
		ArrayList<Quiz> quizList = new ArrayList<Quiz>();
		try {
			ResultSet rs = executeQuery("SELECT * FROM quizzes order by id;");
			while(rs.next()) {
	
						Quiz quiz = new Quiz(rs.getString("name"), rs.getInt("id"), rs.getString("creatorName"), rs.getString("description"), 
						Quiz.intToBoolean(rs.getInt("onePage")), Quiz.intToBoolean(rs.getInt("isRandomOrder")), 
						Quiz.intToBoolean(rs.getInt("isImmediate")), Quiz.intToBoolean(rs.getInt("hasPracticeMode")));
				quizList.add(quiz);
				//quiz.addQuestions();
			}
		} catch (SQLException e) {
	         e.printStackTrace();
		} 
		return quizList;
	}
	
	public Boolean gottenHighScore(String account) throws SQLException{
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT highScore FROM users where user_name=\""+account+"\";");
		if(rs.next()){
			return rs.getBoolean("highScore");
		}
		return false;
	}
	
	public Boolean practiced(String account) throws SQLException{
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT practiceMode FROM users where user_name=\""+account+"\";");
		if(rs.next()){
			return rs.getBoolean("practiceMode");
		}
		return false;
	}
	
	public Boolean accountExists(String account){
		 Statement stmt=null;
			try {
				stmt = con.createStatement();
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			} 
			 try {
				stmt.executeQuery("USE " + database);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			 ResultSet rs=null;
			 try {
				rs = stmt.executeQuery("SELECT * FROM users where user_name=\""+account+"\";");
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				while(rs.next()) { 
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
		
	public void createAccount(String account, String password) throws SQLException{
		Random randomGenerator = new Random();
		int salt = randomGenerator.nextInt(1000);
		String passSalt= password + Integer.toString(salt);
		String hash = null;
			try{
				MessageDigest md = MessageDigest.getInstance("SHA");
			     md.update(passSalt.getBytes());
			     byte[] digest = md.digest();
			     hash =hexToString(digest);
			}catch(NoSuchAlgorithmException e){
				 e.printStackTrace();
			}
		Statement stmt = con.createStatement();
		stmt.executeUpdate("INSERT INTO users VALUES(\""+account+"\",\""+hash+"\","+Integer.toString(salt)+",0,0,FALSE,FALSE,FALSE);");
		
	}
	 
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
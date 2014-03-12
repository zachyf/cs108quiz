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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
			 ResultSet rs =  stmt.executeQuery("SELECT * FROM messages where toUser=\""+user+"\" ORDER BY sentTime desc;");
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
			 ResultSet rs =  stmt.executeQuery("SELECT * FROM announcements ORDER BY postTime desc;");
			 while(rs.next()){
					 a.add(new Announcement(rs.getString("announcement"), rs.getString("userName"),  Timestamp.valueOf(rs.getString("postTime"))));
			 }
		 } catch (SQLException e) {
	         e.printStackTrace();
		 }
		 return a;
	 }
	 
	 public boolean addAnnouncement(String user, String note){
		 try {
			if (!userExists(user) || !isAdmin(user)){
				 return false;
			 }
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 String q = "INSERT into announcements VALUES('" + user+"','" + note + "', CURRENT_TIMESTAMP);";
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
					 a.add(new Challenge(rs.getString("challenger"), rs.getString("challenged"),  rs.getInt("quizID"), true, Timestamp.valueOf(rs.getString("sentTime"))));
			 }
		 } catch (SQLException e) {
	         e.printStackTrace();
		 }
		 return a;
	 }
	 
	 public boolean challengePending(String challenger, String challenged, Integer quizID){
		 int count = 0;
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 ResultSet rs =  stmt.executeQuery("SELECT * FROM challenges where challenged='" + challenged + "' and challenger='" + challenger + "' and quizID=" + quizID + " and pending=1;");
			 while(rs.next()){
				 count++;
			 }
		 } catch (SQLException e) {
	         e.printStackTrace();
		 }
		 if (count > 0) return true;
		 return false;
	 }
	 
	 public int addChallenge(String challenger, String challenged, Integer quizID){
		 try {
			 if (!userExists(challenger)){
				 	return 1;
				 }
			if (!userExists(challenged)){
					return 2;
				}
			if (challengePending(challenger, challenger, quizID)){
					return 3;
				}

			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 String q = "INSERT into challenges VALUES('" + challenger +"','" + challenged + "','"  + quizID+ "',1,CURRENT_TIMESTAMP);";
			 stmt.executeUpdate(q);
			 return 0;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 return 4;
	 }
	 
	 public void makeAdmin(String username) throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 stmt.executeUpdate("UPDATE users SET adminStatus=TRUE where user_name=\""+username+"\";");
	 }
	 
	 public void deleteQuiz(Integer id){
		 try{
			 Statement stmt = con.createStatement();
			 stmt.executeQuery("USE " + database);
			 String q = "DELETE FROM quizzes WHERE id="+id + ";";
			 String r = "DELETE FROM quizRecords WHERE quizID="+id + ";";
			 String s = "DELETE FROM challenges WHERE quizID="+id + ";";
			 stmt.executeUpdate(q);
		     stmt.executeUpdate(r);
		     stmt.executeUpdate(s);
			 }catch(SQLException e) { 
		         e.printStackTrace();
			} 
	 }
	 public int removeUser(String username){
		 try{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 String q = "DELETE FROM users WHERE user_name=\""+username + "\";";
		 String r = "DELETE FROM friends WHERE user1=\""+username + "\" or user2=\"" + username + "\";";
		 String s = "DELETE FROM pending WHERE user1=\""+username + "\" or user2=\"" + username + "\";";
		 String t = "DELETE FROM messages WHERE fromUser=\""+username + "\" or toUser=\"" + username + "\";";
		 String u = "DELETE FROM challenges WHERE challenger=\""+username + "\" or challenged=\"" + username + "\";";
		 String v = "DELETE FROM quizzes WHERE creatorName=\""+username + "\";";
		 String w = "DELETE FROM quizRecords WHERE username=\""+username + "\";";
		 stmt.executeUpdate(q);
	     stmt.executeUpdate(r);
	     stmt.executeUpdate(s);
	     stmt.executeUpdate(t);
	     stmt.executeUpdate(u);
	     stmt.executeUpdate(v);
	     stmt.executeUpdate(w);
		 }catch(SQLException e) { 
	         e.printStackTrace();
		} 
		 return 0;
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
	 
	 public ArrayList<String> getAllUsers(){
		 ArrayList<String> users = new ArrayList<String>();
		 String query = "Select * from users;";
		 ResultSet rs = executeQuery(query);
		 try {
			 while(rs.next()){
				users.add(rs.getString("user_name"));
			 }
		 }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
		 return users;
	 }
	 
	 public int getQuizzesTotal() throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 ResultSet rs =  stmt.executeQuery("SELECT count(numCorrect) FROM quizRecords;");
		 if(rs.next()){ 
			 return rs.getInt("count(numCorrect)");
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
	 
	 public void unfriend(String user1,String user2) throws SQLException{
		 Statement stmt = con.createStatement();
		 stmt.executeQuery("USE " + database);
		 stmt.executeUpdate("DELETE FROM friends where user1='" + user1 + "' and user2='" + user2 + "';");
		 stmt.executeUpdate("DELETE FROM friends where user1='" + user2 + "' and user2='" + user1 + "';");
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
	
	public boolean quizExists(String quizName){
		try{
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String q = "SELECT * FROM quizzes where quizName='" + quizName + "';";
			ResultSet rs = stmt.executeQuery(q);
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		}catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return false;
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
		
	public String getAnimal(String username) throws SQLException{
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT animal FROM users where user_name=\""+username+"\";");
		if(rs.next()){
			return rs.getString("animal");
		}
		return "";
	}
	
	public String winningTeam() throws SQLException{
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT animal, sum(numPlayed) FROM users group by animal;");
		if(rs.next()){
			return rs.getString("animal");
		}
		return "";
	}
	
	public ArrayList<Integer> getMostPopularQuizzes() throws SQLException{
		ArrayList<Integer> result= new ArrayList<Integer>();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT count(*) as c, quizID  FROM quizRecords group by quizID order by c desc limit 5;");
		while(rs.next()){
			result.add(rs.getInt("quizID"));
		}
		return result;
	}
	
	public ArrayList<Integer> getSearchedQuizzes(String search) throws SQLException{
 		ArrayList<Integer> result= new ArrayList<Integer>();
 		Statement stmt = con.createStatement();
 		stmt.executeQuery("USE " + database);
 		ResultSet rs = executeQuery("SELECT * FROM quizzes WHERE quizName LIKE \"%" + search + "%\";");
 		StringBuilder sb = new StringBuilder();
 		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
 		while(rs.next()){
 			result.add(rs.getInt("id"));
 		}
 		return result;
 	}
	
	public ArrayList<Integer>  getRecentQuizzes1() throws SQLException{
		ArrayList<Integer> result= new ArrayList<Integer>();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes order by createtime desc limit 5;");
		while(rs.next()){
			result.add(rs.getInt("id"));
		}
		return result;
	}
	
	public ArrayList<Integer>  getYourCreatedQuizzes(String username) throws SQLException{
		ArrayList<Integer> result= new ArrayList<Integer>();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT * FROM quizzes where creatorName=\""+username+"\" order by createtime desc limit 5;");
		while(rs.next()){
			result.add(rs.getInt("id"));
		}
		return result;
	}
	public ArrayList<Integer> getTakenQuizzes(String username) throws SQLException{
		ArrayList<Integer> result= new ArrayList<Integer>();
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT * FROM quizRecords where userName=\""+username+"\" order by timeSubmitted desc limit 5;");
		while(rs.next()){
			result.add(rs.getInt("quizID"));
		}
		return result;
	}
	public int totalTeamQuizesTaken(String animal) throws SQLException{
		Statement stmt = con.createStatement();
		stmt.executeQuery("USE " + database);
		ResultSet rs = stmt.executeQuery("SELECT sum(numPlayed) FROM users where animal=\""+animal+"\";");
		if(rs.next()){
			return rs.getInt("sum(numPlayed)");
		}
		return -1;
	}
	
	public void createAccount(String account, String password, String animal) throws SQLException{
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
		stmt.executeUpdate("INSERT INTO users VALUES(\""+account+"\",\""+hash+"\","+Integer.toString(salt)+",0,0,FALSE,FALSE,FALSE,\""+animal+"\");");
		
	}
	 
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	public int getNextQuizID() {
		try{
			ResultSet rs = executeQuery("SELECT count(*) as max FROM quizzes;");
			while(rs.next()){
				return (int)rs.getLong("max");
			}
				
			return -1;
		}
		catch(SQLException e){
			return -1;
		}
		catch (NumberFormatException e){
			return -1;
		}
	}
	
	public Quiz getQuizAt(int id){
		try {
			ResultSet rs = executeQuery("SELECT * FROM quizzes where id=" + id + ";");
			ResultSet numQuestionsResult = executeQuery("Select count(*) as numQuestions FROM questions where quizID = " + id);
			numQuestionsResult.next();
			if(rs.next()){
				Quiz quiz = new Quiz(rs.getString("creatorName"), rs.getInt("id"), rs.getString("quizName"), rs.getString("description"), 
						Quiz.intToBoolean(rs.getInt("onePage")), Quiz.intToBoolean(rs.getInt("isRandomOrder")), 
						Quiz.intToBoolean(rs.getInt("isImmediate")), Quiz.intToBoolean(rs.getInt("hasPracticeMode")));
				return quiz;
			}
		}
		catch(SQLException e){
			return null;
		}
		return null;
	}
	
	public int quizNameToID(String quizName){
		String q = "Select id from quizzes where quizName='" + quizName + "';";
		ResultSet rs = executeQuery(q);
		try{
			if (rs.next()){
				int id = rs.getInt("id");
				return id;
			}
			return -1;
		}catch(SQLException e){
			return -1;
		}
	}
	
	/**
	 * Gets 5 most recent quizzes, sorted by date descending,
	 * and returns in ordered list htmL.
	 * @return
	 */
	public String getRecentQuizzes(){
		try {
			ResultSet rs = executeQuery("SELECT id, quizName, createtime FROM quizzes ORDER BY createtime desc LIMIT 5;");
			StringBuilder sb = new StringBuilder();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			while(rs.next()){
				sb.append("<ol>" + "<a href=quizPage.jsp?id=" + rs.getInt("id") + ">" + rs.getString("quizName") + " " + df.format(rs.getTimestamp("createtime")) + "</a></ol>");
			}	
			return sb.toString();
		}
		catch(SQLException e){
			return "";
		}
	}
	
	public ArrayList<ArrayList<Object>> getHighScorers(int quizID){
		String query = "Select userName, numCorrect, numQuestions, timeToComplete" +
				" from quizRecords where quizID = " + quizID + 
				" order by numCorrect  desc, timeToComplete asc limit 5;";
		return getList(query);
	}
	
	public int getHighScoreQuizUser(String user, int quizID){
		String query = "Select * from quizRecords where quizID =" + quizID + " order by numCorrect desc;";
		ResultSet rs = executeQuery(query);
		if (rs == null) return 0;
		try {
		if (rs.next()){
			int numCorrect;
				numCorrect = rs.getInt("numCorrect");
				int numQuestions = rs.getInt("numQuestions");
				double percent = numCorrect*100.0/numQuestions;
				return (int)percent;
			} 
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public ArrayList<ArrayList<Object>> getAllQuizzes(){
		String query = "Select quizName,id from quizzes order by quizName asc;";
		return getList3(query);
	}
	
	public ArrayList<ArrayList<Object>> getRecentScores(int quizID){
		String query = "Select userName, numCorrect, numQuestions, timeToComplete" +
				" from quizRecords where quizID = " + quizID + 
				" order by timeSubmitted  desc limit 5;";
		return getList(query);
	}
	
	public ArrayList<ArrayList<Object>> getRecentHighScores(int quizID){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String query = "Select userName, numCorrect, numQuestions, timeToComplete" +
				" from quizRecords where quizID = " + quizID + 
				" and timeSubmitted > " + df.format(new Timestamp(System.currentTimeMillis() - (24 * 60 * 60 * 1000))) +
				" order by numCorrect  desc, timeToComplete asc limit 5;";
		return getList(query);
	}
	
	public ArrayList<ArrayList<Object>> getMyRecentPerformance(String username, int quizID){
		String query = "Select userName, numCorrect, numQuestions, timeToComplete" +
				" from quizRecords where quizID = " + quizID + " and userName = \"" + username + "\"" +
				" order by timeSubmitted desc limit 5;";
		return getList(query);
	}
	
	public ArrayList<ArrayList<Object>> getMyRecentPerformanceAll(String username){
		String query = "Select quizID, numCorrect, numQuestions, timeToComplete, timeSubmitted from quizRecords where userName = \"" + username + "\" order by timeSubmitted desc;";
		
		return getList2(query);
	}
	
	
	
	private void addChoices(MultipleChoice question, int quizID){
		String query = "Select choice from questions where quizID = " + quizID + " and questionNum = "
			+ question.getNum() + " and not choice = \"" + question.getAnswer() + "\";";
		try {
			ResultSet rs = executeQuery(query);
			while(rs.next()){
				question.addChoices(rs.getString("choice"));
			}
		}
		catch(SQLException e){}
	}
	
	public void getQuestions(Quiz quiz){
	String query = "SELECT question, answer, questionNum, type from questions where quizID = " + quiz.getID() + " " +
			"group by question, answer, questionNum, type order by questionNum asc;";
		try {
			ResultSet rs = executeQuery(query);
			while(rs.next()){
				String type = rs.getString("type");
				if(type.equals("QuestionResponse"))
	            	quiz.addQuestion(new QuestionResponse(rs.getString("question"), rs.getString("answer"), rs.getInt("questionNum")));
				else if (type.equals("Picture"))
					quiz.addQuestion(new PictureResponse(rs.getString("question"), rs.getString("answer"), rs.getInt("questionNum")));
				else if (type.equals("FillInBlank"))
					quiz.addQuestion(new FillInTheBlank(rs.getString("question"), rs.getString("answer"), rs.getInt("questionNum")));
				else if (type.equals("MultipleChoice")){
					//	public MultipleChoice(String question, List<String> choices, String answer, int num) {
					MultipleChoice question = new MultipleChoice(rs.getString("question"), rs.getString("answer"), rs.getInt("questionNum"));
					addChoices(question, quiz.getID());
					quiz.addQuestion(question);
				}
			}
		}
		catch (SQLException e){
		}
	}
	
	private ArrayList<ArrayList<Object>> getList(String query){
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
		try {
			ResultSet rs = executeQuery(query);
			while(rs.next()) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add(rs.getString("userName"));
				row.add((double)rs.getInt("numCorrect")/rs.getInt("numQuestions"));
				row.add(rs.getInt("timeToComplete")/1000);
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	private ArrayList<ArrayList<Object>> getList2(String query){
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
		try {
			ResultSet rs = executeQuery(query);
			while(rs.next()) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add(rs.getInt("quizID"));
				row.add((double)rs.getInt("numCorrect")/rs.getInt("numQuestions"));
				row.add(rs.getInt("timeToComplete")/1000);
				row.add(Timestamp.valueOf(rs.getString("timeSubmitted")));
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	private ArrayList<ArrayList<Object>> getList3(String query){
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
		try {
			ResultSet rs = executeQuery(query);
			while(rs.next()) {
				ArrayList<Object> row = new ArrayList<Object>();
				row.add(rs.getString("quizName"));
				row.add(rs.getInt("id"));
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	public void addQuizToDB(Quiz quiz, int isOnePage, int isRandom, int isImmediateCorrection, int hasPracticeMode){
		Calendar calendar = Calendar.getInstance();
		String insertion = "INSERT INTO quizzes VALUES (" + quiz.getID() + ",\""  + quiz.getName() + "\",\"" 
				+ quiz.getDescription() + "\",\"" + quiz.getCreator() + "\",\'" + 
				new java.sql.Timestamp(calendar.getTime().getTime()) + "\'," 
			+ isOnePage + "," + isRandom + "," + 
			isImmediateCorrection +  "," + hasPracticeMode + ");";
		updateDB(insertion);
	}
	
	public void addAnswerToDB(Answer answer){
		String insertion = "INSERT INTO quizRecords VALUES (" + answer.getNumCorrect() + ","  + answer.getQuiz().getNumQuestions() + "," 
			+ answer.getTimeToComplete() + ",\"" + answer.getUser() + "\"," + answer.getQuiz().getID() + ",\'" + answer.getDateCompleted() + "\');";
		this.updateDB(insertion);
	}
	
	public String getMultipleChoiceQuestionString(Question question, Quiz quiz){
		StringBuilder insertion = new StringBuilder();
		MultipleChoice mcQuestion = (MultipleChoice)question;
		List<String> choices = mcQuestion.getChoices();
		if(choices.size() > 0) insertion.append("INSERT INTO questions VALUES ");
		for(int i = 0; i < choices.size(); i++){
			if(i > 0) insertion.append(",");
			insertion.append("(" + quiz.getID() + ",\""  + question.rawQuestion() + "\",\"" 
			+ question.getAnswer() + "\"," + question.getNum() + ",\"MultipleChoice\",\"" + choices.get(i) + "\")");
			System.out.println("choices");
		}
		insertion.append(";");
		return insertion.toString();
	}
	
	public void addQuestion(Question question, Quiz quiz){
		String insertion = "";
		if(question.getType().equals("MultipleChoice"))
			insertion = getMultipleChoiceQuestionString(question, quiz);
		else
			insertion = "INSERT INTO questions VALUES (" + quiz.getID() + ",\""  + question.rawQuestion() + "\",\"" 
				+ question.getAnswer() + "\"," + question.getNum() + ",\"" + question.getType() + "\",\"\");";
		updateDB(insertion);
		quiz.addQuestion(question);
	}
	
	public String getQuizStats(Quiz quiz){
		StringBuilder sb = new StringBuilder();
		try{
			ResultSet rs = executeQuery("SELECT count(*) as c, avg(numCorrect) as av, count(distinct userName) as numUsers,  " + 
					"avg(numQuestions) as numQuestions, avg(timeToComplete)/1000 as avgTime from quizRecords where quizID = " + quiz.getID() + ";");
			rs.next();
			sb.append(quiz.getName() + " has been taken " + rs.getInt("c") + " times by " + rs.getInt("numUsers"));
			if(rs.getInt("numUsers") > 1)
				sb.append(" different users with an average score of ");
			else sb.append(" user with an average score of ");
			Double averageNumCorrect = rs.getDouble("av");
			Double numQuestions = rs.getDouble("numQuestions");
			int average = Math.round((int)(averageNumCorrect*100/numQuestions));
			sb.append(average + "% and average time of " + rs.getInt("avgTime") + " seconds.");
		}
		catch (SQLException e) {}
		return sb.toString();
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
	
	
}

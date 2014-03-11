package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.xml.internal.ws.api.server.Container;



/**
 * Servlet implementation class userWelcome
 */
@WebServlet("/userWelcome")
public class userWelcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userWelcome() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		String username = (String) ses.getAttribute("name");
		String animal = (String) ses.getAttribute("animal");
		String animalPic="";
		String teamWelcome="";
		String info ="";
		int totalTaken=0;
		try {
			totalTaken = DB.totalTeamQuizesTaken(animal);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		if(animal.equals("Cow")){
			animalPic="Cow.png";
			info="You are a member of Team Cow.";
			teamWelcome = "Make Team Cow proud!";
			
		}
		if(animal.equals("Owl")){
			animalPic="Owl.png";
			info="You are a member of Team Owl.";
			teamWelcome = "Make Team Owl proud!";
		}
		if(animal.equals("Elephant")){
			animalPic="Elephant.png";
			info="You are a member of Team Elephant.";
			teamWelcome = "Make Team Elephant proud!";
		}
		if(animal.equals("Sheep")){
			animalPic="Sheep.png";
			info="You are a member of Team Sheep.";
			teamWelcome = "Make Team Sheep proud!";
		}
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<title> Welcome "+username+"</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<a href=\"logout\" align=\"right\"><img src=\"logout.jpg\" title=\"Click to Logout\" align=\"right\"></img></a>");
		out.println("<h1>Welcome "+username+"</h1>");
		out.println("<h2>Awards:</h2>");
		int check=0;
		try {
			if(DB.winningTeam().equals(animal)){
				out.print("<img src=\"LionAward.png\" title=\"Your Team is in 1st Place. \"><img>");
				check+=1;
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			if(DB.practiced(username)==true){
				out.println("<img src=\"Practice.jpg\" title=\"Practice Makes Perfect-- Awarded when user takes quiz in practice mode\">");
				check+=1;
			}
			if(DB.gottenHighScore(username)==true){
				out.println("<img src=\"HighScore.jpg\" title=\"I Am the Greatest-- Awarded when user gets high score in a quiz\">");
				check+=1;
			}
			if(DB.numQuizesCreated(username)>=1){
				out.println("<img src=\"1Quiz.jpg\" title=\"Amateur Author-- Awarded when user creates one quiz\">");
				check+=1;
			}
			if(DB.numQuizesCreated(username)>=5){
				out.println("<img src=\"5Quiz.jpg\" title=\"Prolific Author-- Awarded when user creates five quizes\">");
			}
			if(DB.numQuizesCreated(username)>=10){
				out.println("<img src=\"10Quiz.jpg\" title=\"Prodigious Author-- Awarded when user creates ten quizes\">");
			}
			if(DB.numQuizesTaken(username)>=10){
				out.println("<img src=\"Took10.jpg\" title=\"Quiz Machine-- Awarded when user takes ten quizes\">");
				check+=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(check==0){
			out.println("<h4> No awards yet.  To win awards, start taking quizzes, making quizzes, or practicing in quiz practice mode.</h4>");
		}
		out.println("<h2>Team Info:</h2>");
		out.println("<table><tr><td>");
		if(totalTaken==1){
			out.println("<img src=\""+animalPic+"\" title=\"Team Crest\"></img></td><td><h3>"+info+"<br> Team "+animal+" has taken a total of "+totalTaken+" quiz. <br> Continue to take quizzes to help your team take the lead.</h3></td></tr></table>");
		}else{
			out.println("<img src=\""+animalPic+"\" title=\"Team Crest\"></img></td><td><h3>"+info+"<br> Team "+animal+" has taken a total of "+totalTaken+" quizzes. <br> Continue to take quizzes to help your team take the lead.</h3></td></tr></table>");
		}
		out.println("<h2>Notifications:</h2>");
		int check2=0;
		try {
			int requests = DB.getNumRequests(username);
			if(requests > 0){
				check2+=1;
				if(requests==1){
					out.println("You have <a href=\"Mailbox\">"+ requests + "</a> friend request pending.");
				}else{
					out.println("You have <a href=\"Mailbox\">"+ requests + "</a> friend requests pending.");
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int numUnread = DB.getNumUnread(username);
		if(numUnread > 0){
			check2+=1;
			if(numUnread==1){
				out.println("You have <a href=\"Mailbox\">"+ numUnread + "</a> unread message.");
			}else{
				out.println("You have <a href=\"Mailbox\">"+ numUnread + "</a> unread messages.");
			}
		}
		ArrayList<Challenge> pendingChallenges = DB.getChallenges(username);
		int numChallenges = pendingChallenges.size();
		if (numChallenges > 0){
			check2 += 1;
			if(numChallenges==1){
				out.println("You have <a href=\"#challenges\">"+ numChallenges + "</a> pending challenge.");
			}else{
				out.println("You have <a href=\"#challenges\">"+ numChallenges + "</a> pending challenges.");
			}
		}
		if(check2==0){
			out.println("<h4>No new notifications at the moment.</h4>");
		}
		try {
			if(DB.isAdmin(username)){
				
				out.println("<h2>Admin Priviledges:</h2>");
				out.println();
				out.println("<p><h4>Remove A User from the Database</h4>");
				out.println("Enter the User You Wish to Remove:");
				out.println("<form action=\"RemoveUser\" METHOD=\"post\">");
				out.println("<input type=\"text\" name=\"userName\">");
				out.println("<input type=\"submit\" value=\"Remove User\"><br>"); 
				out.println("</form></p>");
				
				int totalUsers=DB.getTotalUsers();
				out.println("<p><h4>There are "+totalUsers+" users in the database.</h4></p>");
				int totalQuizzesTaken=DB.getQuizzesTotal();
				out.println("<p><h4>There have been a total of "+totalQuizzesTaken+" quizzes taken thus far.</h4></p>");
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("<h2>Leader Boards and Recent Activity:</h2>");
		out.println("<table>");
		out.println("<tr><th><h4>Most Popular Quizzes:</h4></th><th><h4>Recently Created Quizzes:</h4></th>");
		ArrayList<Integer> takenQuizzes = null;
		ArrayList<Integer> yourCreatedQuizzes = null;
		try {
			takenQuizzes = DB.getTakenQuizzes(username);
			yourCreatedQuizzes = DB.getYourCreatedQuizzes(username);
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if(takenQuizzes.size()!=0){
			out.println("<th><h4>Your Recently Taken Quizzes:</h4></th>");
		}
		if(yourCreatedQuizzes.size()!=0){
			out.println("<th><h4>Your Recently Created Quizzes:</h4></th>");
		}
		out.println("</tr><tr><td>");
		
		ArrayList<Integer> popularQuizzes = null;
		try {
			popularQuizzes = DB.getMostPopularQuizzes();
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for(int i=0; i<popularQuizzes.size();i++){
			int index = popularQuizzes.get(i);
			int ip=i+1;
			out.println(ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a>");
			out.println("<a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a><br>");
		}
		out.println("</td><td>");

		ArrayList<Integer> recentQuizzes = null;
		try {
			recentQuizzes = DB.getRecentQuizzes1();
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for(int i=0; i<recentQuizzes.size();i++){
			int index = recentQuizzes.get(i);
			int ip=i+1;
			out.println(ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a>");
			out.println("<a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a><br>");
		}
		out.println("</td>");
		
		if(takenQuizzes!=null){
			out.println("<td>");
			for(int i=0; i<takenQuizzes.size();i++){
				int index = takenQuizzes.get(i);
				int ip=i+1;
				out.println(ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a>");
				out.println("<a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a><br>");
			}
			out.println("</td>");
		}
		if(yourCreatedQuizzes!=null){
			out.println("<td>");
			for(int i=0; i<yourCreatedQuizzes.size();i++){
				int index = yourCreatedQuizzes.get(i);
				int ip=i+1;
				out.println(ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a><br>");
			}
			out.println("</td>");
		}
		out.println("</tr></table>");
		out.println("<h4> View your entire past quiz performance here: <a href=\"quizPerformanceSummary\"><img src=\"quizPerformance.jpg\" title=\"Click to view history\"></img></a></h4>");

		out.println("<br>");

	
		
		out.println("<h2>Explore:</h2>");
	
		out.println("<table><tr><th>Find New Friends</th><th>Challenge Other Users</th><th>Create Quizzes</th><th>Send Messages</th></tr>");
		out.println("<tr><td>");
		out.println("Enter A User Name:");
		out.println("<form action=\"FriendRequest\" METHOD=\"post\">");
		out.println("<input type=\"text\" name=\"userName\"><br>");
		out.println("<input type=\"submit\" value=\"Add Friend\"><br>"); 
		out.println("</form>");
		
		out.println("</td><td>");
		out.println("Enter a User Name:");
		out.println("<form action=\"SendNewChallenge\" METHOD=\"post\">");
		out.println("<input type=\"text\" name=\"userName\"><br>");
		out.println("Enter a Quiz Name:");
		out.println("<br>");
		out.println("<input type=\"text\" name=\"quizName\"><br>");
		out.println("<input type=\"submit\" value=\"Send Challenge\"><br>"); 
		out.println("</form>");
		out.println("</td><td><a href=\"createQuiz.html\"> <img src=\"createQuiz.jpg\"></img></a></td>");
		out.println("<td><a href=\"NewMessage.jsp?user=" + username + "\"><img src=\"Message.png\" title=\"Click to Message Friends\"></img></a></td></tr></table>");
		
		out.println("<div id=\"message\"></div>");
		out.println("<script type=\"text/javascript/\">");
		out.println("var xmlhttp;");
		    out.println("if (window.XMLHttpRequest){");
		       out.println("xmlhttp = new XMLHttpRequest();"); //for IE7+, Firefox, Chrome, Opera, Safari
		    out.println("} else {");
		        out.println("xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");"); //for IE6, IE5
		    out.println("}");
		    out.println("xmlhttp.open(\"GET\", \"FriendRequest\", true);"); 
		    out.println("xmlhttp.onreadystatechange = function() {");
		        out.println("if (xmlhttp.readyState == 4) { ");
		            out.println("if (xmlhttp.status == 200) {");
		            out.println("document.getElementById(\"message\").innerHTML = xmlhttp.responseText;}else{");
		            out.println("alert(\"problem\");}}};");   
		    out.println("xmlhttp.send(null);</script>");        
		   
		out.println("<h2>Your Recent Messages:</h2>");
		ArrayList<Message> ml = DB.getMessages(username);
		if(ml.size()!=0){
		out.println("<table style=\"width:500px\"><tr><th>From</th><th>Subject</th><th>Time</th>\n\t<th>Note</th>\n</tr>");
		
		for(int i = 0; i < ml.size(); ++i){
			//make it limited size and scrolling 
			String date = new SimpleDateFormat("HH:mm MM/dd/yyyy").format( ml.get(i).getSentTime());
			if(i<5){
				out.println("<tr><td><a href=\"userPage?ID=" + ml.get(i).getTo() + "\">"+ ml.get(i).getTo() +"</a></td><td>" + ml.get(i).getSubject() + "</td><td>" + date + "</td><td>" + ml.get(i).getMessage() + "</td></tr>");
			}
		}
		out.println("</table>");
		out.println("<table><tr><td>");
		out.println("<h4> View all messages here:</h4></td>");
		out.println("<td><a href=\"MailboxFull\"><img src=\"mailbox.png\" title=\"Click to view all messages.\"></img></a></td></tr></table>");
		}else{
			out.println("<h4>You have no recent messages. </h4>");
		}
		out.println("<div id=\"challenges\">");
		out.println("<h2>Pending Challenges:</h2>");
		if (numChallenges == 0){
			out.println("<h4>You have no pending challenges. </h4>");
		}else{
			out.println("<table style=\"width:500px\"><tr><th>Challenger</th><th>Quiz</th><th>Date</th><th>Take Quiz</th></tr>");
			for (int i = 0; i < pendingChallenges.size(); ++i){
				Challenge c = pendingChallenges.get(i);
				String challenger = c.getChallenger();
				String quizName = c.getQuizName();
				String date = new SimpleDateFormat("HH:mm MM/dd/yyyy").format(c.getTime());
				int index = DB.quizNameToID(quizName);
				out.println("<tr><td><a href=\"userPage?ID="+ challenger + "\">" + challenger + "</a></td><td><a href=\"quizPage.jsp?id="+index+"\">"+quizName + "</a></td><td>" + date + "</td><td><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a></td></tr>");

			}
		}
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		

	}

}

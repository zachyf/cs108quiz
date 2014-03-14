package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
		int numChallenges = 0;
		String username = (String) ses.getAttribute("name");
		String animal = (String) ses.getAttribute("animal");
		String animalPic="";
		String teamWelcome="";
		String info ="";
		int totalTaken=0;
		ArrayList<Challenge> pendingChallenges = null;
		try {
			totalTaken = DB.totalTeamQuizesTaken(animal);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		if(animal!=null){

		if(animal.equals( "Cow")){
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
		}
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"bootstrap/css/bootstrap-theme.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"css/jumbotron.css\" rel=\"stylesheet\">");
		if(username!=null){
			out.println("<title> Welcome "+username+"</title>");
		}
		out.println("</head>");
		out.println("<body>");
		     
		// Nav bar html
		out.println("<div class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">");
		out.println("<div class=\"container\"><div class=\"navbar-header\">");
		out.println("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">");
		out.println("<span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span>");
		out.println("</button>");
		out.println("<a class=\"navbar-brand\" href=\"userWelcome\">Quiz Mania!</a>");
		out.println("</div><div class=\"navbar-collapse collapse\">");

		out.println("<ul class=\"nav navbar-nav\">");
		out.println("<li><a href=\"userWelcome\">Home <span class=\"glyphicon glyphicon-home\"></span></a></li>");
		out.println("<li><a href=\"quizPerformanceSummary\">My Quiz History <span class=\"glyphicon glyphicon-th-list\"></a></li>");
		out.println("<li><a href=\"createQuiz.html\">Create Quiz <span class=\"glyphicon glyphicon-pencil\"></a></li>");
		out.println("<li><a href=\"logout\">Logout <span class=\"glyphicon glyphicon-off\"></a></li>");
		out.println("</ul>");
		out.println("<form action=\"SearchQuizzesServlet\" method=\"GET\" class=\"navbar-form navbar-right\" role=\"form\">");
		out.println("<div class=\"form-group\">");
		out.println("<input name=\"search\" type=\"text\" placeholder=\"Search Quizzes...\" class=\"form-control\">");
		out.println("</div><button type=\"submit\" class=\"btn btn-success\">Search <span class=\"glyphicon glyphicon-search\"></button>");
		out.println("</form>");
		out.println("</div></div></div>");

		// Jumbotron html
	    out.println("<div class=\"jumbotron\">");
	    out.println("<div class=\"container\">");
	    if(username!=null){
	    	out.println("<h1>Welcome "+username+"</h1>");
	    }else{
	    	out.println("<h1>Welcome</h1>");
	    }
	    if(username!=null){
	    out.println("<div class=\"row\">");
		out.println("<div class=\"col-md-4\">");

		// Team panel
		
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Team "+animal+"</div>");
		out.println("<center><img src=\""+animalPic+"\" title=\"Team Crest\"></img></center>");
		out.println("<p>Team "+animal+" has taken "+totalTaken+" quizzes. </p>");
		out.println("<p>Continue to take quizzes to help your team take the lead.</p>");
		out.println("</div>"); // Panel
		out.println("</div>"); // Col 1
		out.println("<div class=\"col-md-4\">");
		

		// Awards panel
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Awards</div>");
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
			if(DB.hasAchievement(username, "I am the Greatest")){
				out.println("<img src=\"HighScore.jpg\" title=\"I am the Greatest-- Awarded when user achieves a high score on a quiz\">");
				check+=1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(check==0){
			out.println("<h4> No awards yet.  To win awards, start taking quizzes, making quizzes, or practicing in quiz practice mode.</h4>");
		}
		out.println("</div>"); // Panel
		out.println("</div>"); // Col 2
		out.println("<div class=\"col-md-4\">");
		
		//Announcements
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Announcements</div>");
		ArrayList<Announcement> ann = DB.getAnnouncements();
		if (ann.size() == 0){
			out.println("<h4>No recent announcements.</h4>");
		}else{
			for (int i = 0; i < ann.size(); ++i){
				if (i == 5) 
					break;
				String date = new SimpleDateFormat("HH:mm MM/dd/yyyy").format(ann.get(i).getTime());
				out.println("(" + date + ") " + ann.get(i).getUser() + ": "+ ann.get(i).getAnnouncement());
				out.println("<br>");
			}
		}
	    out.println("</div>"); // panel
		
		// Notifications panel
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Notications</div>");
		int check2=0;
		try {
			int requests = DB.getNumRequests(username);
			if(requests > 0){
				check2+=1;
				if(requests==1){
					out.println("You have <a href=\"Mailbox\">"+ requests + "</a> friend request pending. <br>");
				}else{
					out.println("You have <a href=\"Mailbox\">"+ requests + "</a> friend requests pending. <br>");
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
				out.println("You have <a href=\"Mailbox\">"+ numUnread + "</a> unread message. <br>");
			}else{
				out.println("You have <a href=\"Mailbox\">"+ numUnread + "</a> unread messages. <br>");
			}
		}
		pendingChallenges = DB.getChallenges(username);
		numChallenges = pendingChallenges.size();
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
		out.println("</div>"); // Thumbnail
		out.println("</div>"); // Thumbnail
		out.println("</div>"); // Col 3
		out.println("</div>"); // Row 1
	    out.println("</div>"); // Container
	    out.println("</div>"); // Jumbotron


		out.println("<div class=\"container\">");

	    
		try {
			if(DB.isAdmin(username)){

				out.println("<h2>Admin Privileges:</h2>");
				out.println();
				out.println("<h4>Review Flagged Quizzes:</h4>");
				ArrayList<ArrayList<Object>> flaggedQuizzes =DB.getFlaggedQuizzes();
				if(flaggedQuizzes.size()==0){
					out.println("None to Review Now.");
				}
				out.println("<table>");
				for(int i=0;i<flaggedQuizzes.size();i++){
					int index = (Integer) flaggedQuizzes.get(i).get(0);
					int ip=i+1;
					out.println("<tr>");
					out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a></td>");
					out.println("<td><a href=\"userPage?ID="+flaggedQuizzes.get(i).get(1)+"\">"+flaggedQuizzes.get(i).get(1)+"</a></td>");
					out.println("</tr>");
					
				}
				out.println("<table>");
				
			
				
				
				out.println("<p><h4>Make an Announcement</h4>");
				out.println("Enter an Announcement:");
				out.println("<form action=\"MakeAnnouncement\" METHOD=\"post\">");
				out.println("<input type=\"text\" name=\"note\">");
				out.println("<input type=\"submit\" value=\"Announce\"><br>"); 
				out.println("</form></p>");
				out.println("<p><h4>Remove A User from the Database</h4>");
				out.println("Choose the User You Wish to Remove:");
				out.println("<br>");
				out.println("<form action=\"RemoveUser\" METHOD=\"post\">");
				out.println("<select name=\"userName\">");
				ArrayList<String> userNamesR = DB.getAllUsers();
				for(int i=0;i<userNamesR.size();i++){
					if (!userNamesR.get(i).equals(username)){
						out.println("<option value=\""+userNamesR.get(i)+"\">" + userNamesR.get(i) +"</option>");
					}
				}
				out.println("</select><br>");
				out.println("<input type=\"submit\" value=\"Remove User\"><br>"); 
				out.println("</form></p>");
				out.println("<p><h4>Promote A User to Admin Status</h4>");
				out.println("Enter the User You Wish to Promote:");
				out.println("<form action=\"promote\" METHOD=\"post\">");
				out.println("<select name=\"userName\">");
				ArrayList<String> userNamesP = DB.getAllUsersNotAdmin();
				for(int i=0;i<userNamesP.size();i++){
					if (!userNamesP.get(i).equals(username)){
						out.println("<option value=\""+userNamesP.get(i)+"\">" + userNamesP.get(i) +"</option>");
					}
				}
				out.println("</select><br>");
				out.println("<input type=\"submit\" value=\"Promote User\"><br>"); 
				out.println("</form></p>");
				out.println("<p><h4>Delete A Quiz:</h4>");
				out.println("Select the Quiz You Wish to Delete:");
				out.println("<form action=\"deleteQuiz\" METHOD=\"post\">");
				out.println("<select name=\"quizID\">");
				ArrayList<ArrayList<Object>> allQuizzes = DB.getAllQuizzes();
				for(int i=0;i<allQuizzes.size();i++){
					out.println("<option value=\""+allQuizzes.get(i).get(1)+"\">"+allQuizzes.get(i).get(0)+"</option>");
				}
				out.println("</select>");
				out.println("<input type=\"submit\" value=\"Delete\"><br>"); 
				out.println("</form></p>");
				out.println("<p><h4>Clear Quiz History:</h4>");
				out.println("Select which Quiz's History You Wish to Clear:");
				out.println("<form action=\"ClearHistory\" METHOD=\"post\">");
				out.println("<select name=\"quizID\">");
				for(int i=0;i<allQuizzes.size();i++){
					out.println("<option value=\""+allQuizzes.get(i).get(1)+"\">"+allQuizzes.get(i).get(0)+"</option>");
				}
				out.println("</select>");
				out.println("<input type=\"submit\" value=\"Clear\"><br>"); 
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
		}
		out.println("<h2>Leader Boards and Recent Activity:</h2>");
		out.println("<div class=\"row\">");

		// Most popular quizzes table
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Most Popular Quizzes</div>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
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
			out.println("<tr>");
			out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a></td>");
			out.println("<td align=\"right\"><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a></td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</div></div>"); // Column 1

	
		// Recently Created Quizzes Table
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Recently Created Quizzes</div>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
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
			out.println("<tr>");
			out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a></td>");
			out.println("<td align=\"right\"><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a></td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</div></div>"); // Column 2
		out.println("</div>"); // Row 1
		out.println("<div class=\"row\">");
		if(username!=null){
		// Your recently taken quizzes table
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Your Recently Taken Quizzes</div>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
		ArrayList<Integer> takenQuizzes = null;
		ArrayList<Integer> yourCreatedQuizzes = null;
		try {
			takenQuizzes = DB.getTakenQuizzes(username);
			yourCreatedQuizzes = DB.getYourCreatedQuizzes(username);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		for(int i=0; i<takenQuizzes.size();i++){
			int index = takenQuizzes.get(i);
			int ip=i+1;
			out.println("<tr>");
			out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a></td>");
			out.println("<td align=\"right\"><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a></td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</div></div>"); // Column 1

		// Your recently created quizzes table
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Your Recently Created Quizzes</div>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
		for(int i=0; i<yourCreatedQuizzes.size();i++){
			int index = yourCreatedQuizzes.get(i);
			int ip=i+1;
			out.println("<tr>");
			out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a></td>");
			out.println("<td align=\"right\"><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"></img></a></td>");
			out.println("</tr>");
		}
		out.println("</table>");

		out.println("</div></div>"); // Column 2
		
		// Friends' recently taken quizzes table
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Your Friends' Recently Taken Quizzes</div>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
		ArrayList<Quiz> friendCreatedQuizzes = null;
		ArrayList<quizRecord> friendTakenQuizzes = null;
		try {
			friendTakenQuizzes = DB.getTakenQuizzes();
			friendCreatedQuizzes = DB.getCreatedQuizzes();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		int numPrinted = 0;
		for(int i=0; i< friendTakenQuizzes.size();i++){
			if (numPrinted == 5) break;
			quizRecord qr = friendTakenQuizzes.get(i);
			try {
				if (DB.alreadyFriends(qr.getUser(), username)){
					int ip=numPrinted+1;
					out.println("<tr>");
					out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+qr.getQuizID()+"\">"+DB.getQuizAt(qr.getQuizID()).getName()+"</a></td>");
					out.println("<td><a href=\"userPage?ID="+qr.getUser()+"\">"+ qr.getUser() +"</a></td>");
					out.println("<td>Score: "+ qr.getScore() +"</a></td>");
					out.println("<td align=\"right\"><a href=\"TakeQuiz.jsp?quizID="+qr.getQuizID()+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a></td>");
					out.println("</tr>");
					numPrinted++;
				}
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		out.println("</table>");
		out.println("</div></div>"); // Column 1
		
		// Friends' recently created quizzes table
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Your Friends' Recently Created Quizzes</div>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
		int numP2 = 0;
		for(int i=0; i< friendCreatedQuizzes.size();i++){
			if (numP2 == 5) break;
			Quiz q = friendCreatedQuizzes.get(i);
			try {
				if(DB.alreadyFriends(username, q.getCreator())){
					int ip=numP2+1;
					out.println("<tr>");
					out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+q.getID()+"\">"+DB.getQuizAt(q.getID()).getName()+"</a></td>");
					out.println("<td>Created by: <a href=\"userPage?ID="+q.getCreator()+"\">"+ q.getCreator() +"</a></td>");
					out.println("<td align=\"right\"><a href=\"TakeQuiz.jsp?quizID="+q.getID()+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a></td>");
					out.println("</tr>");
					numP2++;
				}
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();     
			}
		}
		out.println("</table>");
		out.println("</div></div>"); // Column 1
		
		// Friends' recently achieved awards
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Your Friends' Recently Achieved Awards</div>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
		ArrayList<achievement> recentlyAchieved = DB.getFriendsAchievements(username);
		for(int i=0; i< recentlyAchieved.size();i++){
			if (i == 5) break;
			achievement a = recentlyAchieved.get(i);
			int ip=i+1;
			out.println("<tr>");
			out.println("<td>"+ip+") <a href=\"userPage?ID="+a.getUser()+"\">"+ a.getUser() +"</a> acheived " + a.getAchievementName() + "</td>");
			out.println("</tr>");
			numPrinted++;
		}
		out.println("</table>");
		out.println("</div></div>"); // Column 1
		
		

		// Your highly rated quizzes table
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Highest Rated Quizzes</div>");
		out.println("<table class=\"table\">");
		
		ArrayList<ArrayList<Object>>ratedQuizzes = null;
		ratedQuizzes = DB.getHighestRated();
		for(int i=0; i<ratedQuizzes.size();i++){
			int index = (Integer) ratedQuizzes.get(i).get(1);
			int ip=i+1;
			out.println("<tr>");
			out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a></td>");
			Double avg =(Double) ratedQuizzes.get(i).get(2);
			DecimalFormat df = new DecimalFormat("#.0");
			out.println("<td> Average Rating:  "+df.format(avg)+"</td>");
			out.println("<td align=\"right\"><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"></img></a></td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</div></div>"); // Column 1
		if(username!=null){
		out.println("<br><h2>Explore:</h2>");

		out.println("<table class=\"table\"><tr><th>Find New Friends</th><th>Challenge Other Users</th><th>Create Quizzes</th><th>Send Messages</th></tr>");
		out.println("<tr><td>");
		out.println("Choose a User:");
		out.println("<form action=\"FriendRequest\" METHOD=\"post\">");
		out.println("<select name=\"userName\" class=\"form-control\">");
		ArrayList<String> userNamesF = DB.getAllUsersNotFriends(username);
		for(int i=0;i<userNamesF.size();i++){
			try {
				if (!DB.alreadyFriends(username, userNamesF.get(i)) && !username.equals(userNamesF.get(i))){
					out.println("<option value=\""+userNamesF.get(i)+"\">" + userNamesF.get(i) +"</option>");
				}
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
				

			}
		
		}
		out.println("</select><br>");
		out.println("<button type=\"submit\" class=\"btn btn-default\">Add Friend</button>"); 
		out.println("</form>");
		out.println("<div id=\"message1\"></td></div><td>");
		out.println("Select a User Name:");
		out.println("<form action=\"SendNewChallenge\" METHOD=\"post\">");
		out.println("<select name=\"userName\" class=\"form-control\">");
		ArrayList<String> userNamesC = DB.getAllUsers();
		for(int i=0;i<userNamesC.size();i++){
			if (!username.equals(userNamesC.get(i))){
				out.println("<option value=\""+userNamesC.get(i)+"\">" + userNamesC.get(i) +"</option>");
			}
		}
		out.println("</select><br>");
		out.println("Select a Quiz:");
		out.println("<br>");
		out.println("<select name=\"quizID\" class=\"form-control\">");
		ArrayList<ArrayList<Object>> allQuizzes = DB.getAllQuizzes();
		for(int i=0;i<allQuizzes.size();i++){
			out.println("<option value=\""+allQuizzes.get(i).get(1)+"\"><a href=\"quizPage.jsp?id="+allQuizzes.get(i).get(1)+"\">"+allQuizzes.get(i).get(0)+"</a></option>");
		}
		out.println("</select><br>");
		out.println("<button type=\"submit\" class=\"btn btn-default\">Send Challenge</button>"); 
		out.println("</form>");
		out.println("</td><td><a href=\"createQuiz.html\"> <img src=\"createQuiz.jpg\"></img></a></td>");
		out.println("<td><a href=\"NewMessage.jsp?user=" + username + "\"><img src=\"Message.png\" title=\"Click to Message Friends\"></img></a></td></tr></table>");


		out.println("</div>"); // Row 2
		
		// Friends
		out.println("<div class=\"row\">");
		out.println("<h2>Messaging and Challenges:</h2>");
		

		// Messaging Column
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Messaging</div>");
		out.println("<b>Recent messages</b><br>");
		ArrayList<Message> ml = DB.getMessages(username);
		if(ml.size()!=0){
		out.println("<table class=\"table\"><tr><th>From</th><th>Subject</th><th>Time</th>\n\t<th>Note</th>\n</tr>");

		for(int i = 0; i < ml.size(); ++i){
			//make it limited size and scrolling 
			String date = new SimpleDateFormat("HH:mm MM/dd/yyyy").format( ml.get(i).getSentTime());
			if(i<5){

				out.println("<tr><td><a href=\"userPage?ID=" + ml.get(i).getTo() + "\">"+ ml.get(i).getTo() +"</a></td><td>" + ml.get(i).getSubject() + "</td><td>" + date + "</td><td>" + ml.get(i).getMessage() + "</td></tr>");

			}
		}
		out.println("</table><br>");
		}else{
			out.println("<h4>You have no recent messages. </h4>");
		}
		out.println("<b>View all messages:</b><br>");
		out.println("<a href=\"MailboxFull\"><img src=\"mailbox.png\" title=\"Click to view all messages.\"></img></a><br>");

		out.println("</div>"); // Panel
		out.println("</div>"); // Col

		// Challenges Column
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Challenges</div>");
		out.println("<b>Pending challenges:</b><br>");

		if (numChallenges == 0){
			out.println("You have no pending challenges.");
		}else{
			out.println("<table class=\"table\"><tr><th>Challenger</th><th>Challenger's Score</th><th>Quiz</th><th>Date</th><th>Take Quiz</th></tr>");
			for (int i = 0; i < pendingChallenges.size(); ++i){
				Challenge c = pendingChallenges.get(i);
				String challenger = c.getChallenger();
				int index = c.getQuizID();
				String date = new SimpleDateFormat("HH:mm MM/dd/yyyy").format(c.getTime());
				String quizName = DB.getQuizAt(index).getName();
				int highestScore = DB.getHighScoreQuizUser(username, index);
				out.println("<tr><td><a href=\"userPage?ID="+ challenger + "\">" + challenger + "</a></td><td>" + highestScore + "</td><td><a href=\"quizPage.jsp?id="+index+"\">"+quizName + "</a></td><td>" + date + "</td><td><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a></td></tr>");

			}
			out.println("</table><br>");
		}
		
		out.println("</div>"); // Panel
		out.println("</div>"); // Col
		

		out.println("</div>"); // Row

		out.println("<div class=\"row\">");

		out.println("</div>");
		out.println("</div>"); // Row
		out.println("</div>"); // Container
		}
		out.println("</body>");
		out.println("</html>");


	}

}

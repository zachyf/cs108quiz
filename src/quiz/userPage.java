package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class userPage
 */
@WebServlet("/userPage")
public class userPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userPage() {
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
		String loggedInUser = (String) ses.getAttribute("name");
		String username = (String) request.getParameter("ID");
		if(loggedInUser.equals(username)){
			RequestDispatcher dispatch = request.getRequestDispatcher("userWelcome"); 
			dispatch.forward(request, response);
		}
		String animal="";
		try {
			animal = DB.getAnimal(username);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
			info=username+" is a member of Team Cow.";
			teamWelcome = "Make Team Cow proud!";
			
		}
		if(animal.equals("Owl")){
			animalPic="Owl.png";
			info=username+" is a member of Team Owl.";
			teamWelcome = "Make Team Owl proud!";
		}
		if(animal.equals("Elephant")){
			animalPic="Elephant.png";
			info=username+" is a member of Team Elephant.";
			teamWelcome = "Make Team Elephant proud!";
		}
		if(animal.equals("Sheep")){
			animalPic="Sheep.png";
			info=username+" is a member of Team Sheep.";
			teamWelcome = "Make Team Sheep proud!";
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
		out.println("<title> Welcome to "+username+"'s Profile</title>");
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
	    out.println("<h1>Welcome to "+username+"'s Profile</h1>");
	    try {
			if(DB.alreadyFriends(username, loggedInUser)){

				out.println("<h4> You and "+username+" are friends.</h4><br>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    out.println("<div class=\"row\">");
		out.println("<div class=\"col-md-4\">");

		// Team panel
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Team "+animal+"</div>");
		out.println("<center><img src=\""+animalPic+"\" title=\"Team Crest\"></img></center>");
		out.println("<p>"+info+"</p>");
		out.println("<p>Team "+animal+" has taken "+totalTaken+" quizzes. </p>");
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
			out.println("<h4>"+username+ " has not won any awards yet.</h4>");
		}
		out.println("</div>"); // Panel
		out.println("</div>"); // Col 2
		
		// Interact panel
		out.println("<div class=\"col-md-4\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Interact with "+username+"</div>");
		out.println("<table class=\"table\"><tr><td>");
		out.println("</td><td><h4>Challenge "+username+":</h4>");
		out.println("<form action=\"SendNewChallenge\" METHOD=\"post\">");
		out.println("<input type=\"hidden\" name=\"userName\" value=\""+username+"\"><br>");
		out.println("<select name=\"quizID\">");
		ArrayList<ArrayList<Object>> allQuizzes = DB.getAllQuizzes();
		for(int i=0;i<allQuizzes.size();i++){
			out.println("<option class=\"dropdown-menu\" value=\""+allQuizzes.get(i).get(1)+"\"><a href=\"quizPage.jsp?id="+allQuizzes.get(i).get(1)+"\">"+allQuizzes.get(i).get(0)+"</a></option>");
		}
		out.println("</select><br>");
		out.println("<button type=\"submit\" class=\"btn btn-default\">Send Challenge</button><br>"); 
		out.println("</form>");
		out.println("</td><td><h4>Message "+username+":</h4>");
		out.println("<a href=\"NewMessage.jsp?user=" + loggedInUser + "&to="+username+"\"><img src=\"Message.png\" title=\"Click to Message "+username+"\"></img></a></td></tr></table>");
		
		out.println("</div>"); // Panel
		out.println("</div>"); // Col 1
		
		out.println("</div>"); // Row 1
	    out.println("</div>"); // Container
	    out.println("</div>"); // Jumbotron
		
	    out.println("<div class=\"container\">");
	       
		
		
		out.println("<h2>Interact with "+username+":</h2>");
		out.println("<table><tr><td>");
		
	  
		try {
			if(DB.alreadyFriends(username, loggedInUser)){
				out.println("<h4> You are already friends with "+username+".</h4>");
				out.println("<h4> Click to unfriend "+username+": <a href=\"Unfriend?userName="+username+"\"><img src=\"rejectButton.jpg\"></img></a></h4>");
			}else if(DB.alreadyPending(loggedInUser,username)){
				out.println("<h4> You have already requested "+username+". Wait for a response.</h4>");
			}else if(DB.alreadyPending(username,loggedInUser)){
				out.println("<h4> "+username+" has requested you. Check your requests.</h4>");
			}else{
				
				out.println("<h4> Add "+username+" as a friend <a href=\"FriendRequest?userName="+username+"\"><img src=\"acceptButton.jpg\" title=\"Add Friend\"></img></a>");
				 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		out.println("<h2>"+username+"'s Recent Quiz Activity</h2>");
		out.println("<div class=\"row\">");
		
		// Users recently taken quizzes table
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">"+username+"'s Recently Taken Quizzes</div>");
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
			out.println("<td><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a></td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</div></div>"); // Column 1

		// Users Recently created quizzes table
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">"+username+"'s Recently Created Quizzes</div>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
		for(int i=0; i<yourCreatedQuizzes.size();i++){
			int index = yourCreatedQuizzes.get(i);
			int ip=i+1;
			out.println("<tr>");
			out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a></td>");
			out.println("<td><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"></img></a></td>");
			out.println("</tr>");
		}
		out.println("</table>");

		out.println("</div></div>"); // Column 2
		out.println("</div>"); 		 // Row
	
		out.println("</div>"); // Container
		out.println("<br>");
		out.println("</body>");
		out.println("</html>");

	}

}

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
		out.println("<title> Welcome to "+username+"'s Profile</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<a href=\"logout\" align=\"right\"><img src=\"logout.jpg\" title=\"Click to Logout\" align=\"right\"></img></a>");
		out.println("<h1>Welcome to "+username+"'s Profile</h1>");
		out.println("<h2>Awards:</h2>");
		int check=0;
		try {
			if(DB.winningTeam().equals(animal)){
				out.print("<img src=\"LionAward.png\" title=\""+username+"'s Team is in 1st Place. \"><img>");
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
			out.println("<h4>"+username+" has not won any awards yet.</h4>");
		}
		out.println("<h2>Team Info:</h2>");
		out.println("<table><tr><td>");
		if(totalTaken==1){
			out.println("<img src=\""+animalPic+"\" title=\"Team Crest\"></img></td><td><h3>"+info+"<br> Team "+animal+" has taken a total of "+totalTaken+" quiz. <br> </h3></td></tr></table>");
		}else{
			out.println("<img src=\""+animalPic+"\" title=\"Team Crest\"></img></td><td><h3>"+info+"<br> Team "+animal+" has taken a total of "+totalTaken+" quizzes. <br> </h3></td></tr></table>");
		}
		out.println("<h2>Interact with "+username+":</h2>");
		out.println("<table><tr><td>");
		try {
			if(DB.alreadyFriends(username, loggedInUser)){
				out.println("<h4> You are already friends with "+username+".</h4>");
			}else{
				out.println("<h4> Add "+username+" as a friend <a href=\"FriendRequest?userName="+username+"\"><img src=\"acceptButton.jpg\" title=\"Add Friend\"></img></a>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("</td><td><h4>Challenge "+username+":</h4>");
		out.println("<form action=\"SendNewChallenge\" METHOD=\"post\">");
		out.println("<input type=\"hidden\" name=\"userName\" value=\""+username+"\"><br>");
		out.println("Enter a Quiz Name:");
		out.println("<br>");
		out.println("<input type=\"text\" name=\"quizName\"><br>");
		out.println("<input type=\"submit\" value=\"Send Challenge\"><br>");
		out.println("</td><td><h4>Message "+username+":</h4>");
		out.println("<a href=\"NewMessage.jsp?user=" + loggedInUser + "&to="+username+"\"><img src=\"Message.png\" title=\"Click to Message "+username+"\"></img></a></td></tr></table>");
	
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
			out.println("<th><h4>"+username+"'s Recently Taken Quizzes:</h4></th>");
		}
		if(yourCreatedQuizzes.size()!=0){
			out.println("<th><h4>"+username+"'s Recently Created Quizzes:</h4></th>");
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
				out.println(ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a>");
				out.println("<a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"><img></a><br>");
			}
			out.println("</td>");
		}
		out.println("</tr></table>");
	

		out.println("<br>");
		out.println("<br><a href=\"userWelcome\"><img src=\"home.jpg\" title=\"Return Home \"></img></a>");
		out.println("</body>");
		out.println("</html>");

	}

}

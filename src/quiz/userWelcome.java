package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
		if(animal.equals("Cow")){
			animalPic="Cow.png";
			teamWelcome = "Make team Cow proud.";
		}
		if(animal.equals("Owl")){
			animalPic="Owl.png";
			teamWelcome = "Make team Owl proud.";
		}
		if(animal.equals("Elephant")){
			animalPic="Elephant.png";
			teamWelcome = "Make team Elephant proud.";
		}
		if(animal.equals("Sheep")){
			animalPic="Sheep.png";
			teamWelcome = "Make team Sheep proud.";
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
		out.println("<img src=\""+animalPic+"\"><img>");
		out.println("<h1>"+teamWelcome+"</h1>");
		try {
			int requests = DB.getNumRequests(username);
			if(requests > 0){
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
			if(numUnread==1){
				out.println("You have <a href=\"Mailbox\">"+ numUnread + "</a> unread message.");
			}else{
				out.println("You have <a href=\"Mailbox\">"+ numUnread + "</a> unread messages.");
			}
		}

		out.println("<br>");

		try {
			if(DB.practiced(username)==true){
				out.println("<img src=\"Practice.jpg\" title=\"Practice Makes Perfect-- Awarded when user takes quiz in practice mode\">");
			}
			if(DB.gottenHighScore(username)==true){
				out.println("<img src=\"HighScore.jpg\" title=\"I Am the Greatest-- Awarded when user gets high score in a quiz\">");
			}
			if(DB.numQuizesCreated(username)>=1){
				out.println("<img src=\"1Quiz.jpg\" title=\"Amateur Author-- Awarded when user creates one quiz\">");
			}
			if(DB.numQuizesCreated(username)>=5){
				out.println("<img src=\"5Quiz.jpg\" title=\"Prolific Author-- Awarded when user creates five quizes\">");
			}
			if(DB.numQuizesCreated(username)>=10){
				out.println("<img src=\"10Quiz.jpg\" title=\"Prodigious Author-- Awarded when user creates ten quizes\">");
			}
			if(DB.numQuizesTaken(username)>=10){
				out.println("<img src=\"Took10.jpg\" title=\"Quiz Machine-- Awarded when user takes ten quizes\">");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(DB.isAdmin(username)){
				
				out.println("<h2>Admin Priviledges:</h2>");
				out.println();
				out.println("<p>Remove user from database:<br>");
				out.println("Enter A User Name:");
				out.println("<form action=\"RemoveUser\" METHOD=\"post\">");
				out.println("<input type=\"text\" name=\"userName\">");
				out.println("<input type=\"submit\" value=\"Remove User\"><br>"); 
				out.println("</form></p>");
				
				int totalUsers=DB.getTotalUsers();
				out.println("<p><h3>There are "+totalUsers+" users in the database.</h3></p>");
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("<br>");
		out.println("Find Friends:");
		out.println("<br>");
		out.println("Enter A User Name:");
		out.println("<form action=\"FriendRequest\" METHOD=\"post\">");
		out.println("<input type=\"text\" name=\"userName\">");
		out.println("<input type=\"submit\" value=\"Add Friend\"><br>"); 
		out.println("</form>");
		
		out.println("<br>");
		out.println("Send a Challenge:");
		out.println("<br>");
		out.println("Enter a User Name:");
		out.println("<form action=\"SendNewChallenge\" METHOD=\"post\">");
		out.println("<input type=\"text\" name=\"userName\"><br>");
		out.println("Enter a Quiz Name:");
		out.println("<br>");
		out.println("<input type=\"text\" name=\"quizName\">");
		out.println("<input type=\"submit\" value=\"Send Challenge\"><br>"); 
		out.println("</form>");

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
		            out.println("alert(\"sjf\");}}};");   
		    out.println("xmlhttp.send(null);</script>");        
		   
		out.println("Send a friend a note: <a href=\"NewMessage.jsp?user=" + username + "\"><img src=\"Message.png\" title=\"Click to Message Friends\"></img></a><br>");
		out.println("<table style=\"width:300px\"><tr><th>From</th><th>Subject</th><th>Time</th>\n\t<th>Note</th>\n</tr>");
		ArrayList<Message> ml = DB.getMessages(username);
		for(int i = 0; i < ml.size(); ++i){
			//make it limited size and scrolling 
			if(i<5){
				out.println("<tr><td>" + ml.get(i).getTo() + "</td><td>" + ml.get(i).getSubject() + "</td><td>" + ml.get(i).getSentTime() + "</td><td>" + ml.get(i).getMessage() + "</td></tr>");
			}
		}
		out.println("</body>");
		out.println("</html>");
		out.println("</table>");

	}

}

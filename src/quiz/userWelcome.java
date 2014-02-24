package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<title> Welcome "+username+"</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Welcome "+username+"</h1>");
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
				out.println("<form action=\"FriendRequest\" METHOD=\"post\">");
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
		out.println("</body>");
		out.println("</html>");
	}

}

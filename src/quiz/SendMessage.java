package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SendMessage
 */
@WebServlet("/SendMessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		String to = (String) request.getParameter("to");
		String subject = (String) request.getParameter("subject");
		String message = (String) request.getParameter("note");
		HttpSession ses = request.getSession();
		String loggedInUser = (String) ses.getAttribute("name");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"bootstrap/css/bootstrap-theme.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"css/jumbotron.css\" rel=\"stylesheet\">");
		out.println("<title>Send Message</title>");
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
		
		out.println("<div class=\"container\"><br><br>");
		try {
			if(!DB.userExists(to)){
				out.println("User "+to+" does not exist.");
			}else{
				Calendar calendar = Calendar.getInstance();
				Message m = new Message(to, loggedInUser, subject, message, new java.sql.Timestamp(calendar.getTime().getTime()), 0);
				//put it in the database
				DB.insertMessage(m);
				out.println("Message has been sent.");
				RequestDispatcher dispatch = request.getRequestDispatcher("userWelcome"); 
				dispatch.forward(request, response); 
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("</div>");
		
		out.println("</body>");
		out.println("</html>");
	}

}

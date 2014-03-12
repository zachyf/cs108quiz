package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class quizPerformanceSummary
 */
@WebServlet("/quizPerformanceSummary")
public class quizPerformanceSummary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public quizPerformanceSummary() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
	
		String username = (String) ses.getAttribute("name");
		ArrayList<ArrayList<Object>> myRecentPerformance = DB.getMyRecentPerformanceAll(username);
		Integer size= myRecentPerformance.size();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"bootstrap/css/bootstrap-theme.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"css/jumbotron.css\" rel=\"stylesheet\">");
		out.println("<title>"+username+"'s Quiz Performance</title>");
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
		
		out.println("<div class=\"container\">");
		out.println("<h2>"+username+"'s Quiz Performance</h2>");
		
		if(size>0){
			out.println("<table class=\"table\">");
			out.println("<tr>");
			out.println("<th>Quiz</th>");
			out.println("<th>Score</th>");		
			out.println("<th>Time Taken</th>");
			out.println("<th>Time of Submission</th>");
			out.println("</tr>");
			out.println("<tr>");
			for(int i = 0; i < myRecentPerformance.size(); i++){
				String quiz=DB.getQuizAt((Integer)myRecentPerformance.get(i).get(0)).getName();
				out.println("<td><a href=\"quizPage.jsp?id="+(Integer)myRecentPerformance.get(i).get(0)+"\">"+quiz+"</a></td>");
				out.println("<td>" + ((Double)(myRecentPerformance.get(i).get(1))*100) + "%</td>");
				out.println("<td>" + myRecentPerformance.get(i).get(2) + " seconds </td>");
				String date = new SimpleDateFormat("HH:mm MM/dd/yyyy").format( myRecentPerformance.get(i).get(3));
				out.println("<td>" + date + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
		}else{
			out.println("<h4>You no have quiz history. </h4>");
		}
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

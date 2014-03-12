package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchQuizzesServlet
 */
@WebServlet("/SearchQuizzesServlet")
public class SearchQuizzesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchQuizzesServlet() {
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
		String search = request.getParameter("search");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"bootstrap/css/bootstrap-theme.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"css/jumbotron.css\" rel=\"stylesheet\">");
		out.println("<title>Search Quizzes</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<script type=\"text/javascript\">");
		out.println("function handleFriend(){");
		out.println("var xmlhttp;");
	    out.println("if (window.XMLHttpRequest){");
	       out.println("xmlhttp = new XMLHttpRequest();"); //for IE7+, Firefox, Chrome, Opera, Safari
	    out.println("} else {");
	        out.println("xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");"); //for IE6, IE5
	    out.println("}");
	    out.println("xmlhttp.open(\"POST\", \"FriendRequest\", true);"); 
	    out.println("xmlhttp.onreadystatechange = function() {");
	        out.println("if (xmlhttp.readyState == 4) { ");
	            out.println("if (xmlhttp.status == 200) {");
	            out.println("document.getElementById(\"message1\").innerHTML = xmlhttp.responseText;}else{");
	            out.println("alert(\"problem\");}}};");   
	    out.println("xmlhttp.send(null);}</script>");        
	
	 // Nav bar html
		out.println("<div class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">");
		out.println("<div class=\"container\"><div class=\"navbar-header\">");
		out.println("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">");
		out.println("<span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span>");
		out.println("</button>");
		out.println("<a class=\"navbar-brand\" href=\"userWelcome\">Quiz Mania!</a>");
		out.println("</div><div class=\"navbar-collapse collapse\">");
		out.println("<ul class=\"nav navbar-nav\">");
		out.println("<li><a href=\"userWelcome\">Home</a></li>");
		out.println("<li><a href=\"quizPerformanceSummary\">My Quiz History</a></li>");
		out.println("<li><a href=\"createQuiz.html\">Create Quiz</a></li>");
		out.println("<li><a href=\"logout\">Logout</a></li>");
		out.println("</ul>");
		out.println("<form action=\"SearchQuizzesServlet\" method=\"GET\" class=\"navbar-form navbar-right\" role=\"form\">");
		out.println("<div class=\"form-group\">");
		out.println("<input name=\"search\" type=\"text\" placeholder=\"Search Quizzes...\" class=\"form-control\">");
		out.println("</div><button type=\"submit\" class=\"btn btn-success\">Search</button>");
		out.println("</form>");
		out.println("</div></div></div>");
		
		// Search Quizzes
		ArrayList<Integer> searchQuizzes = null;
		try {
			searchQuizzes = DB.getSearchedQuizzes(search);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		out.println("<br><div class=\"container\">");
		out.println("<div class=\"col-md-6\">");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\">Search Quizzes for "+ search +"</div>");
		out.println("<table class=\"table\">");
		out.println("<tr>");
		for(int i=0; i<searchQuizzes.size();i++){
			int index = searchQuizzes.get(i);
			int ip=i+1;
			out.println("<tr>");
			out.println("<td>"+ip+") <a href=\"quizPage.jsp?id="+index+"\">"+DB.getQuizAt(index).getName()+"</a></td>");
			out.println("<td><a href=\"TakeQuiz.jsp?quizID="+index+"\"><img src=\"takeQuiz.png\" title=\"Click to take quiz.\"></img></a></td>");
			out.println("</tr>");
		}
		out.println("</table>");
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

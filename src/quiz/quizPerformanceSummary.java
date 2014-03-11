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
		out.println("<title>"+username+"'s Quiz Performance</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h2>"+username+"'s Quiz Performance</h2>");
		if(size>0){
			out.println("<table>");
			out.println("<tr>");
			out.println("<th>Quiz</th>");
			out.println("<th>Score</th>");		
			out.println("<th>Time Taken</th>");
			out.println("<th>Time of Submission</th>");
			out.println("</tr>");
			out.println("<tr>");
			for(int i = 0; i < myRecentPerformance.size(); i++){
				String quiz=DB.getQuizAt((Integer)myRecentPerformance.get(i).get(0)).getName();
				out.println("<td><a href=\"quizPage.jsp?id="+(Integer)myRecentPerformance.get(i).get(0)+"\">"+quiz+"</a></td");
				out.println("<td>" + myRecentPerformance.get(i).get(2) + "</td>");
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
		out.println("<br><a href=\"userWelcome\"><img src=\"home.jpg\" title=\"Return Home \"></img></a>");
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

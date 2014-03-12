package quiz;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SendNewChallenge
 */
@WebServlet("/SendNewChallenge")
public class SendNewChallenge extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendNewChallenge() {
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
		String userName = (String) request.getParameter("userName");
		Integer quizID = Integer.parseInt(request.getParameter("quizID"));
		HttpSession ses = request.getSession();
		String loggedInUser = (String) ses.getAttribute("name");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        String quizName=DB.getQuizAt(quizID).getName();
		int v = DB.addChallenge(loggedInUser, userName, quizID);
		if (v == 0){
			out.println("You have challenged " + userName + " to quiz " + quizName);
		} else if (v == 1){
			out.println("Challenge could not be sent of an error finding your username, " + loggedInUser);
		} else if (v == 2){
			out.println("Challenge could not be sent because the challenged user " + userName + " does not exist");
		} else if (v == 3){
			out.println("Challenge could not sent because there is already a pending challenge from you to " + userName + " on quiz " + quizName);
		} else if (v ==4){
			out.println("Challenge could not be sent because of a sql error.");
		}
		
	}

}

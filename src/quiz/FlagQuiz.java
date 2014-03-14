package quiz;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FlagQuiz
 */
@WebServlet("/FlagQuiz")
public class FlagQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FlagQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		Integer quizID = Integer.parseInt(request.getParameter("quizID"));
		String username = (String)request.getParameter("username");
		try {
			DB.markAsReviewed(quizID,username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("userWelcome"); 
		dispatch.forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		Integer quizID = Integer.parseInt(request.getParameter("quizID"));
		String username = (String)request.getParameter("username");
		try {
			DB.addFlag(quizID,username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("quizPage.jsp?id="+quizID); 
		dispatch.forward(request, response); 
	}

}

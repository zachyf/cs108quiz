package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class editQuizServlet
 */
@WebServlet("/editQuizServlet")
public class editQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editQuizServlet() {
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
		String id = request.getParameter("id");
		DBConnection db = (DBConnection)request.getServletContext().getAttribute("db");
		if(request.getParameter("submit").equals("cancel")){
			RequestDispatcher dispatch = request.getRequestDispatcher("quizPage.jsp?id=" + id);
			dispatch.forward(request, response);
		}
		else if(request.getParameter("submit").equals("delete")){
			db.deleteQuiz(Integer.parseInt(id));
			RequestDispatcher dispatch = request.getRequestDispatcher("userWelcome");
			dispatch.forward(request, response);
		}
		else{
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			boolean onePage = request.getParameter("single") != null;
			boolean isRandom = request.getParameter("random") != null;
			boolean isImmediateCorrection = request.getParameter("immediate") != null;
			boolean hasPracticeMode = request.getParameter("practice") != null;
			Quiz quiz =  new Quiz(Integer.parseInt(id), name, description, onePage, isRandom, isImmediateCorrection, hasPracticeMode);
			db.updateQuiz(quiz);
			RequestDispatcher dispatch = request.getRequestDispatcher("quizPage.jsp?id=" + id);
			dispatch.forward(request, response);
		}
		
	}

}

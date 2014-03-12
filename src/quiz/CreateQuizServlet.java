package quiz;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateQuestionsServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private Quiz createQuiz(HttpServletRequest request){
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String creatorName = (String)request.getSession().getAttribute("name");
		boolean onePage = request.getParameter("single") != null;
		boolean isRandom = request.getParameter("random") != null;
		boolean isImmediateCorrection = request.getParameter("immediate") != null;
		boolean hasPracticeMode = request.getParameter("practice") != null;
		DBConnection db = (DBConnection)request.getServletContext().getAttribute("db");
		int id = db.getNextQuizID();
		Quiz quiz = new Quiz(creatorName, id, name, description, onePage, isRandom, isImmediateCorrection, hasPracticeMode);
		db.addQuizToDB(quiz, Quiz.boolToInt(onePage), Quiz.boolToInt(isRandom), Quiz.boolToInt(isImmediateCorrection), Quiz.boolToInt(hasPracticeMode));
		return quiz;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Quiz q = createQuiz(request);
		RequestDispatcher dispatch = request.getRequestDispatcher("createQuestions.jsp?num=1&quizID=" + q.getID());
		dispatch.forward(request, response);
	}

}

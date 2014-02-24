package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class CreateQuestionResponseServlet
 */
@WebServlet("/CreateQuestionResponseServlet")
public class CreateQuestionResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionResponseServlet() {
        super();
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
		response.setContentType("text/html; charset=UTF-8");
		
		ServletContext context = request.getServletContext();
		int quizID = Integer.valueOf(request.getParameter("quizID"));
		Quiz quiz  = ((QuizManager)context.getAttribute("QuizManager")).getQuizAt(quizID);

		// DEBUG: This is just for debugging
		ArrayList<Question> questions;
		if (context.getAttribute("questions") == null) {
			questions = new ArrayList<Question>();
			context.setAttribute("questions", questions);
		} else {
			questions = (ArrayList<Question>)context.getAttribute("questions");
		}
		
		// Get question and answer from the form
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		
		// num was passed in as a hidden input
		int num = Integer.valueOf(request.getParameter("num"));
		
		// Create the question
		Question q = new QuestionResponse(question, answer, num);
		quiz.addQuestion(q);
		
		// DEBUG: This is just for debugging
		questions.add(q);
		context.setAttribute("questions", questions);
		
		// Open createQuestions.jsp to create another question
		String url = "createQuestions.jsp?num=" + String.valueOf(num + 1) + "&quizID=" + quizID;
		RequestDispatcher dispatch = request.getRequestDispatcher(url);
		dispatch.forward(request, response);
	}

}

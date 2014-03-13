package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateMultiAnswerServlet
 */
@WebServlet("/CreateMultiAnswerServlet")
public class CreateMultiAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMultiAnswerServlet() {
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
		response.setContentType("text/html; charset=UTF-8");
		
		ServletContext context = request.getServletContext();
		DBConnection db = (DBConnection)context.getAttribute("db");
		int quizID = Integer.valueOf(request.getParameter("quizID"));
		Quiz quiz  = db.getQuizAt(quizID);

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
		
		// Get the answer		
		int numAnswers = Integer.valueOf(request.getParameter("numAnswers"));
		StringBuilder buff = new StringBuilder();
		buff.append(request.getParameter("answer1"));
		for (int i = 2; i <= numAnswers; i++) {
			buff.append(", ");
			buff.append(request.getParameter("answer" + String.valueOf(i)));
		}
		
		String check = (String) request.getParameter("check");
		boolean ordered = false;
		if(check!=null){
			if(check.equals("require-order")){
				buff.append(", ");
				buff.append("&=ordered");
			} 
		} else {
			buff.append(", ");
			buff.append("&=unordered");
		}
		String answer = buff.toString();
		
		
		// num was passed in as a hidden input
		int num = Integer.valueOf(request.getParameter("num"));
		
		// Create the question
		Question q = new MultiAnswerQuestion(question, answer, num);
		db.addQuestion(q, quiz);
		
		// DEBUG: This is just for debugging
		questions.add(q);
		context.setAttribute("questions", questions);
		
		// Open createQuestions.jsp to create another question
		String url = "createQuestions.jsp?num=" + String.valueOf(num + 1) + "&quizID=" + quizID;
		RequestDispatcher dispatch = request.getRequestDispatcher(url);
		dispatch.forward(request, response);
	}

}

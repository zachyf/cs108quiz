package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MultiPageQuiz
 */
@WebServlet("/MultiPageQuiz")
public class MultiPageQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultiPageQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private void printHeader(PrintWriter out, boolean isCorrect) throws IOException{
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		if(isCorrect)
			out.println("<title>Correct!</title>");
		else
			out.println("<title>Incorrect!</title>");
		out.println("</head>");
		out.println("<body>");
		if(isCorrect)
			out.println("<h1>Correct</h1>");
		else
			out.println("<h1>Incorrect</h1>");
	}
	
	/**
	 * Handles recording of answer, displaying of grade (for immediately graded quiz), and
	 * moving to the next Question for the multi-page quizzes.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get the information and record the answer
		int quizID = Integer.parseInt(request.getParameter("quizID"));
		DBConnection db = (DBConnection)request.getServletContext().getAttribute("db");
		Quiz quiz = db.getQuizAt(quizID);
		@SuppressWarnings("unchecked")
		Queue<Integer> q_order = (Queue<Integer>)request.getSession().getAttribute("questionsLeft" + quizID);
		Answer a = (Answer)request.getSession().getAttribute("answer");
		String questionNum = request.getParameter("questionNum");
		Question question = quiz.getQuestion(Integer.parseInt(questionNum));
		String answer = request.getParameter(questionNum);		
		a.setAnswer(question, answer);
		
		//If auto-grade 
		if(quiz.isImmediateCorrection()){
			boolean isCorrect = question.checkAnswer(answer);
			//then display answer and grade for question, then link to next question
			//Set-up response for output
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			printHeader(out, isCorrect);
			//TODO add image for correct or incorrect
			
			out.println(question.displayQuestion());
			out.println("<p>Your answer: " + answer + "</p>");
			
			if(q_order.isEmpty())
				out.println("<a href=\"GradeQuiz.jsp\"> Finish Quiz and Show Score</a>");
			else{
				out.println("<a href=\"printNextQuestion.jsp?quizID=" + quizID + "\"> Next Question</a>");
			}
		} //Else if end of quiz, forward to grading
		else if(q_order.isEmpty()){
			RequestDispatcher dispatch = request.getRequestDispatcher("GradeQuiz.jsp");
			dispatch.forward(request, response);
		}
		else { //Go to next question
			RequestDispatcher dispatch = request.getRequestDispatcher("printNextQuestion.jsp?quizID=" + quizID);
			dispatch.forward(request, response);			
		}
			
	}

}

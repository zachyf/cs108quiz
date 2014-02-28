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

	private void printGrade(HttpServletResponse response, Quiz quiz, Queue<Integer> q_order) throws IOException{
		//Set-up response for output
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<title>Shopping Cart</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Shopping Cart</h1>");
		
		if(q_order.isEmpty())
			out.println("Link to finish quiz");
		else{
			out.println("Link to next question");
		}
	}
	
	/**
	 * Handles recording of answer, displaying of grade (for immediately graded quiz), and
	 * moving to the next Question for the multi-page quizzes.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Get the information and record the answer
		int quizID = Integer.parseInt(request.getParameter("quizID"));
		QuizManager qm = (QuizManager)request.getServletContext().getAttribute("QuizManager");
		Quiz quiz = qm.getQuizAt(quizID);
		@SuppressWarnings("unchecked")
		Queue<Integer> q_order = (Queue<Integer>)request.getSession().getAttribute("questionsLeft" + quizID);
		
		// TODO record answer here
		
		//If auto-grade 
		if(quiz.isImmediateCorrection()){
			//then display answer and grade for question, then link to next question
			printGrade(response, quiz, q_order);
		} //Else if end of quiz, forward to grading
		else if(q_order.isEmpty()){
			RequestDispatcher dispatch = request.getRequestDispatcher("GradeQuiz");
			dispatch.forward(request, response);
		}
		else { //Go to next question
			RequestDispatcher dispatch = request.getRequestDispatcher("printNextQuestion.jsp?quizID=" + quizID);
			dispatch.forward(request, response);			
		}
			
	}

}

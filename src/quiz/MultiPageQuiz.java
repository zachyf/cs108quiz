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
		out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"bootstrap/css/bootstrap-theme.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"css/jumbotron.css\" rel=\"stylesheet\">");
		if(isCorrect)
			out.println("<title>Correct!</title>");
		else
			out.println("<title>Incorrect!</title>");
		out.println("</head>");
		out.println("<body>");
		
		// Nav bar html
		out.println("<div class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">");
		out.println("<div class=\"container\"><div class=\"navbar-header\">");
		out.println("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">");
		out.println("<span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span>");
		out.println("</button>");
		out.println("<a class=\"navbar-brand\" href=\"userWelcome\">Quiz Mania!</a>");
		out.println("</div><div class=\"navbar-collapse collapse\">");
		out.println("<ul class=\"nav navbar-nav\">");
		out.println("<li><a href=\"userWelcome\">Home <span class=\"glyphicon glyphicon-home\"></span></a></li>");
		out.println("<li><a href=\"quizPerformanceSummary\">My Quiz History <span class=\"glyphicon glyphicon-th-list\"></a></li>");
		out.println("<li><a href=\"createQuiz.html\">Create Quiz <span class=\"glyphicon glyphicon-pencil\"></a></li>");
		out.println("<li><a href=\"logout\">Logout <span class=\"glyphicon glyphicon-off\"></a></li>");
		out.println("</ul>");
		out.println("<form action=\"SearchQuizzesServlet\" method=\"GET\" class=\"navbar-form navbar-right\" role=\"form\">");
		out.println("<div class=\"form-group\">");
		out.println("<input name=\"search\" type=\"text\" placeholder=\"Search Quizzes...\" class=\"form-control\">");
		out.println("</div><button type=\"submit\" class=\"btn btn-success\">Search <span class=\"glyphicon glyphicon-search\"></button>");
		out.println("</form>");
		out.println("</div></div></div>");
		out.println("<div class=\"container\">");
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
		boolean practiceMode = (Boolean)request.getSession().getAttribute("practice");
		Quiz quiz = (Quiz)request.getSession().getAttribute("quiz");
		@SuppressWarnings("unchecked")
		Queue<Integer> q_order = (Queue<Integer>)request.getSession().getAttribute("questionsLeft" + quizID);
		Answer a = (Answer)request.getSession().getAttribute("answer");
		String questionNum = request.getParameter("questionNum");
		Question question = quiz.getQuestion(Integer.parseInt(questionNum));
		String answer = null;
		if (question.getType().equals("MultiAnswer")) {
			int numAnswers = Integer.valueOf(request.getParameter("numAnswers" + questionNum));
			StringBuilder buff = new StringBuilder();
			buff.append(request.getParameter(questionNum + " 0"));
			for (int i = 1; i < numAnswers; i++) {
				buff.append(", ");
				buff.append(request.getParameter(questionNum + " " + String.valueOf(i)));
			}
			answer = buff.toString();
		} else if (question.getType().equals("MultipleChoiceMultipleAnswer")) {
			int numChoices = Integer.valueOf(request.getParameter("numChoices" + questionNum));
			StringBuilder buff = new StringBuilder();
			for (int i = 0; i < numChoices; i++) {
				String check = request.getParameter("check" + questionNum + " " + i);
				if (check != null && check.equals("checkAnswer")) {
					if (buff.length() > 0) {
						buff.append(", ");
					}
					String choice = request.getParameter("choice" + questionNum + " " + i);
					buff.append(request.getParameter("choice" + questionNum + " " + i));
				}
			}
			answer = buff.toString();
		} else {
			answer = request.getParameter(questionNum);	
		}
		a.setAnswer(question, answer);
		
		boolean isCorrect = question.checkAnswer(answer);
		if(practiceMode && !isCorrect)
			q_order.add(Integer.parseInt(questionNum));
		
		//If auto-grade 
		if(quiz.isImmediateCorrection()){
			//then display answer and grade for question, then link to next question
			//Set-up response for output
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			printHeader(out, isCorrect);
			//TODO add image for correct or incorrect
			
			out.println(question.displayQuestion(quiz.getNumQuestions() - q_order.size()));
			out.println("<p>Your answer: " + answer + "</p>");
			
			if(q_order.isEmpty())
				out.println("<a href=\"GradeQuiz.jsp\"> Finish Quiz and Show Score</a>");
			else{
				out.println("<a href=\"printNextQuestion.jsp?quizID=" + quizID + "\"> Next Question</a>");
			}
			out.println("</div>");
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

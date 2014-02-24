<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% 
int quizID = Integer.parseInt(request.getParameter("quizID"));
QuizManager qm = (QuizManager)application.getAttribute("QuizManager");
Quiz quiz = qm.getQuizAt(quizID);
ArrayList<Question> questions = quiz.getQuestionList(); 
out.println("<h1>" + quiz.getName() + "</h1>");
//TODO Write Grad Quiz Servlet
out.println("<form action=\"GradeQuiz\" method=\"post\">");
for (int i = 0; i < questions.size(); i++) {
	out.println(questions.get(i).getQuestion());
}
out.println("<input type=\"submit\" value=\"Submit\" /></p>");
out.println("</form>");
%>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- This page is the first page of the quiz. If it is a single page quiz, then this is the only page of the quiz.
Otherwise, it tracks what questions are left in the quiz and passes to the subsequent pages -->
<html>
<% 
int quizID = Integer.parseInt(request.getParameter("quizID"));
QuizManager qm = (QuizManager)application.getAttribute("QuizManager");
Quiz quiz = qm.getQuizAt(quizID);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=quiz.getName() %></title>
</head>
<body>
<% 

Queue<Integer> q_order = quiz.getQuestionOrder();
out.println("<h1>" + quiz.getName() + "</h1>");

if(quiz.isSinglePage()){
	out.println("<form action=\"GradeQuiz\" method=\"post\">");
	while(!q_order.isEmpty())
		out.println(quiz.getQuestionAt(q_order.poll()));

}
else {
	out.println("<form action=\"MultiPageQuiz\" method=\"post\">");
	out.println(quiz.getQuestionAt(q_order.poll()));
	session.setAttribute("questionsLeft" + quizID, q_order);
	out.println("<br><input name=\"quizID\" type=\"hidden\" value=\"" + quizID + "\">");
}
out.println("<input type=\"submit\" value=\"Submit\"/>");
out.println("</form></p>");
%>
</body>
</html>
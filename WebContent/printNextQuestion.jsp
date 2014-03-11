<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 
//This page is a placeholder for the homepage. 
//It currently allows you to browse recently created quizzes or create a new quiz.
int quizID = Integer.parseInt(request.getParameter("quizID"));
Quiz quiz = (Quiz)session.getAttribute("quiz");
Queue<Integer> q_order = (Queue<Integer>)session.getAttribute("questionsLeft" + quizID);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form action="MultiPageQuiz" method="post">

<% 
int questionNum = q_order.poll();
out.println(quiz.getQuestionAt(questionNum, quiz.getNumQuestions() - q_order.size()));
session.setAttribute("questionsLeft" + quizID, q_order);
out.println("<input name=\"quizID\" type=\"hidden\" value=\"" + quizID + "\"/>");
out.println("<br><input name=\"questionNum\" type=\"hidden\" value=\"" + questionNum + "\">");
out.println("<input type=\"submit\" value=\"Submit\"/>");
out.println("</form></p>");
%>
</form>
</body>
</html>
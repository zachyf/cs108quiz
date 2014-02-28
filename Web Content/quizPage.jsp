<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--This is the page that displays all information about a quiz once it has been created. IT is a placeholder
currently. It allows you to take the quiz. -->
<html>
<% String quizID = request.getParameter("id"); 
QuizManager qm = (QuizManager)application.getAttribute("QuizManager"); 
Quiz quiz = qm.getQuizAt(Integer.parseInt(quizID));
request.setAttribute("quiz", quiz);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=quiz.getName()%></title>
</head>
<body>
<h1><%=quiz.getName() %></h1>

<h4><%=quiz.getDescription() %></h4>

<h5>Creator: Username</h5>

<p>User's Most Recent Performance</p>

<p>Highest Performers</p>

<p>Recent High Performers (Past Day)</p>

<p>Recent Test Takers</p>

<p>Summary of user performance</p>

<p><a href="TakeQuiz.jsp?quizID=<%=quizID %>"> Take this quiz </a></p>

<p>Take the quiz in practice mode</p>

<p>Edit Quiz (if user is owner)</p>


</body>
</html>
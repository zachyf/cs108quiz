<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<% QuizManager qm = (QuizManager)application.getAttribute("QuizManager"); 
System.out.println(qm + "has " + qm.getNextId() + " quizzes");%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to QuizWorld</title>
</head>
<body>
<% // Get Announcements Here 
out.print("HERE IS WHERE ANNOUNCEMENTS WOULD GO");%><br></br>

<% // PrintRecent Quizzes
out.print(qm.getRecentQuizzes());
%>
<br></br>
<a href="createQuiz-jr.html" > Click Here to Create New Quiz</a>

</body>
</html>
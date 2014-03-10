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
DBConnection db = (DBConnection)application.getAttribute("db");
if(db == null) System.out.println("HAHAHAHAHA");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=quiz.getName()%></title>
</head>
<body>
<h1><%=quiz.getName() %></h1>

<h4><%=quiz.getDescription() %></h4>

<h5>Creator: <%=quiz.getCreator() %></h5>

<p>User's Most Recent Performance</p>

<p>Highest Performers</p>
<table border="1" style="width:300px">
<tr>
  <th>Rank</th>
  <th>User Name</th>
  <th>Score</th>		
  <th>Time</th>
  </tr>
<tr>
<% ArrayList<ArrayList<Object>> leaderboard = quiz.getHighScorers(db);
for(int i = 0; i < leaderboard.size(); i++){
	out.println("<tr><td>" + (i+1) + "</td>");
	out.println("<td>" + leaderboard.get(i).get(0) + "</td>");
	out.println("<td>" + ((Double)(leaderboard.get(i).get(1))*100) + "%</td>");
	out.println("<td>" + leaderboard.get(i).get(2) + "</td>");
	out.println("</tr>");
}
%>
</table>

<p>Recent High Performers (Past Day)</p>

<p>Recent Test Takers</p>

<p>Summary of user performance</p>

<%
if(session.getAttribute("name") == null){
	out.println("<p><a href=\"Homepage.jsp?quizID=" + quizID + "\"> Login to take this quiz </a></p>");	
}
else{
	out.println("<p><a href=\"TakeQuiz.jsp?quizID=" + quizID + "\"> Take this quiz </a></p>");
	//<p>Take the quiz in practice mode</p>
	if(quiz.getCreator().equals(session.getAttribute("name")))
		out.println("<p>Edit Quiz - Since you are the owner</p>");
}
%>
</body>
</html>
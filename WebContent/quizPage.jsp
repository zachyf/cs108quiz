<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--This is the page that displays all information about a quiz once it has been created. IT is a placeholder
currently. It allows you to take the quiz. -->
<html>
<% String quizID = request.getParameter("id"); 
DBConnection db = (DBConnection)application.getAttribute("db");
Quiz quiz = db.getQuizAt(Integer.parseInt(quizID));
request.setAttribute("quiz", quiz);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=quiz.getName()%></title>
</head>
<body>
<h1><%=quiz.getName() %></h1>

<h4><%=quiz.getDescription() %></h4>

<h5>Creator: <%=quiz.getCreator() %></h5>

<% if(session.getAttribute("name") != null){
	out.println("<p>My Most Recent Performance</p>");
	out.println("<table border=\"1\" style=\"width:300px\">");
	out.println("<tr><th>Rank</th><th>User Name</th><th>Score</th><th>Time</th>");
	out.println("</tr><tr>");
	ArrayList<ArrayList<Object>> myRecentPerformance = quiz.getMyRecentPerformance((String)session.getAttribute("name"), db);
	if(myRecentPerformance == null) System.out.println("TEST");
	for(int i = 0; i < myRecentPerformance.size(); i++){
		out.println("<tr><td>" + (i+1) + "</td>");
		out.println("<td>" + myRecentPerformance.get(i).get(0) + "</td>");
		out.println("<td>" + ((Double)(myRecentPerformance.get(i).get(1))*100) + "%</td>");
		out.println("<td>" + myRecentPerformance.get(i).get(2) + "</td>");
		out.println("</tr>");
		out.println("</table>");
	}
}
%>
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
<table border="1" style="width:300px">
<tr>
  <th>Rank</th>
  <th>User Name</th>
  <th>Score</th>		
  <th>Time</th>
  </tr>
<tr>
<% ArrayList<ArrayList<Object>> recentHighScores = quiz.getRecentHighScores(db);
for(int i = 0; i < leaderboard.size(); i++){
	out.println("<tr><td>" + (i+1) + "</td>");
	out.println("<td>" + recentHighScores.get(i).get(0) + "</td>");
	out.println("<td>" + ((Double)(recentHighScores.get(i).get(1))*100) + "%</td>");
	out.println("<td>" + recentHighScores.get(i).get(2) + "</td>");
	out.println("</tr>");
}
%>
</table>

<p>Recent Test Takers</p>
<table border="1" style="width:300px">
<tr>
  <th>Rank</th>
  <th>User Name</th>
  <th>Score</th>		
  <th>Time</th>
  </tr>
<tr>
<% ArrayList<ArrayList<Object>> recentTestTakers = quiz.getRecentScores(db);
for(int i = 0; i < leaderboard.size(); i++){
	out.println("<tr><td>" + (i+1) + "</td>");
	out.println("<td>" + recentTestTakers.get(i).get(0) + "</td>");
	out.println("<td>" + ((Double)(recentTestTakers.get(i).get(1))*100) + "%</td>");
	out.println("<td>" + recentTestTakers.get(i).get(2) + "</td>");
	out.println("</tr>");
}
%>
</table>

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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*" %>
<%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% String quizID = request.getParameter("id"); 
DBConnection db = (DBConnection)application.getAttribute("db");
Quiz quiz = db.getQuizAt(Integer.parseInt(quizID));
request.setAttribute("quiz", quiz);
String username = (String)session.getAttribute("name");
%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%=quiz.getName()%></title>

    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
    
    <!-- Custom styles for this template -->
    <link href="css/jumbotron.css" rel="stylesheet">
    <link href="css/justifiednav.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>

<!-- Navigation Bar -->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="HomepageBootstrap.jsp">Quiz Mania!</a>
    </div>
    <div class="navbar-collapse collapse">
      <form class="navbar-form navbar-right" role="form">
        <div class="form-group">
          <input type="text" placeholder="Search Quizzes..." class="form-control">
        </div>
        <button type="submit" class="btn btn-success">Search</button>
      </form>
    </div><!--/.navbar-collapse -->
  </div>
</div><br>

<div class="container">
	<h1><%=quiz.getName() %></h1>

	<h4><%=quiz.getDescription() %></h4>


	<h5>Creator:<a href="userPage?ID=<%=quiz.getCreator()%>"><%=quiz.getCreator()%></a></h5>
	
	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-default">
				<%
				if(session.getAttribute("name") != null){
					ArrayList<ArrayList<Object>> myRecentPerformance = db.getMyRecentPerformance(username, quiz.getID());
					if(myRecentPerformance.size() > 0){
						out.println("<div class=\"panel-heading\">My Recent Scores</div>");
						out.println("<table class=\"table\">");
						out.println("<tr>");
						out.println("<th>Rank</th>");
						out.println("<th>User Name</th>");
						out.println("<th>Score</th>");		
						out.println("<th>Time</th>");
						out.println("</tr>");
						out.println("<tr>");
						for(int i = 0; i < myRecentPerformance.size(); i++){
							out.println("<tr><td>" + (i+1) + "</td>");

							out.println("<td><a href=userWelcome>" + myRecentPerformance.get(i).get(0) + "</a></td>");
							out.println("<td>" + Math.round((Double)(myRecentPerformance.get(i).get(1))*100) + "%</td>");
							out.println("<td>" + myRecentPerformance.get(i).get(2) + " seconds</td>");
							out.println("</tr>");
						}
						out.println("</table>");
					}
				}
				%>
				
			</div>
		</div>
		
		<div class="col-md-6">
			<!-- Highest performers table -->
			<div class="panel panel-default">
			<% ArrayList<ArrayList<Object>> leaderboard = db.getHighScorers(quiz.getID());
				if(leaderboard.size() > 0){
					out.println("<div class=\"panel-heading\">Highest Scores</div>");
					out.println("<table class=\"table\">");
					out.println("<tr>");
					out.println("<th>Rank</th>");
					out.println("<th>User Name</th>");
					out.println("<th>Score</th>");		
					out.println("<th>Time</th>");
					out.println("</tr>");
					out.println("<tr>");
					for(int i = 0; i < leaderboard.size(); i++){
						out.println("<tr><td>" + (i+1) + "</td>");
						if(username != null && username.equals(leaderboard.get(i).get(0)))
							out.println("<td><a href=userWelcome>" + leaderboard.get(i).get(0) + "</a></td>");
						else out.println("<td><a href=userPage?ID=" + leaderboard.get(i).get(0) + ">" + leaderboard.get(i).get(0) + "</a></td>");
						out.println("<td>" + Math.round((Double)(leaderboard.get(i).get(1))*100) + "%</td>");
						out.println("<td>" + leaderboard.get(i).get(2) + " seconds</td>");

						out.println("</tr>");
					}
					out.println("</table>");
				}
				else {
					if(session.getAttribute("name") == null){
						out.println("<p><a href=\"Homepage.jsp?quizID=" + quizID + "\"> No one has taken this quiz - login and be the first</a></p>");	
					}
					else{
						out.println("<p><a href=\"TakeQuiz.jsp?quizID=" + quizID + "\"> No one has taken this quiz - Be the First! </a></p>");
					}
				}
				%>
			</div>		
		</div>
		
		<div class="col-md-6">
			<!-- Recent high scores table -->
			<div class="panel panel-default">
				<% ArrayList<ArrayList<Object>> recentHighScores = db.getRecentHighScores(quiz.getID());
				if(recentHighScores.size() > 0){
					out.println("<div class=\"panel-heading\">Today's Top Scores</div>");
					out.println("<table class=\"table\">");
					out.println("<tr>");
					out.println("<th>Rank</th>");
					out.println("<th>User Name</th>");
					out.println("<th>Score</th>");		
					out.println("<th>Time</th>");
					out.println("</tr>");
					out.println("<tr>");
					for(int i = 0; i < recentHighScores.size(); i++){
						out.println("<tr><td>" + (i+1) + "</td>");

						if(username != null && username.equals(leaderboard.get(i).get(0)))
							out.println("<td><a href=userWelcome>" + recentHighScores.get(i).get(0) + "</a></td>");
						else out.println("<td><a href=userPage?ID=" + recentHighScores.get(i).get(0) + ">" + recentHighScores.get(i).get(0)  + "</a></td>");
						out.println("<td>" + Math.round((Double)(recentHighScores.get(i).get(1))*100) + "%</td>");
						out.println("<td>" + recentHighScores.get(i).get(2) + " seconds</td>");

						out.println("</tr>");
					}
					out.println("</table>");
				}
				%>
			</div>
		</div>
		
		<div class="col-md-6">
			<!-- Recent test takers table -->
			<div class="panel panel-default">
				<% ArrayList<ArrayList<Object>> recentTestTakers = db.getRecentScores(quiz.getID());
				if(recentTestTakers.size() > 0){
					out.println("<div class=\"panel-heading\">Recent Scores</div>");
					out.println("<table class=\"table\">");
					out.println("<tr>");
					out.println("<th>Rank</th>");
					out.println("<th>User Name</th>");
					out.println("<th>Score</th>");		
					out.println("<th>Time</th>");
					out.println("</tr>");
					out.println("<tr>");
					for(int i = 0; i < recentTestTakers.size(); i++){
						out.println("<tr><td>" + (i+1) + "</td>");
						if(username != null && username.equals(leaderboard.get(i).get(0)))
							out.println("<td><a href=userWelcome>" + recentTestTakers.get(i).get(0) + "</a></td>");
						else out.println("<td><a href=userPage?ID=" + recentTestTakers.get(i).get(0) + ">" + recentTestTakers.get(i).get(0)  + "</a></td>");
						out.println("<td>" + Math.round((Double)(recentTestTakers.get(i).get(1))*100) + "%</td>");
						out.println("<td>" + recentTestTakers.get(i).get(2) + " seconds</td>");
						out.println("</tr>");
					}
					out.println("</table>");
				}
				%>
			</div>
		</div>			
	</div>
	<div class="row">
		

		<%
		if(leaderboard.size() > 0){
			out.println("<p>Summary of user performance</p>");
			if(session.getAttribute("name") == null){
				out.println("<p><a href=\"Homepage.jsp?quizID=" + quizID + "\"> Login to take this quiz </a></p>");	
			}
			else{
				out.println("<p><a href=\"TakeQuiz.jsp?quizID=" + quizID + "\"> Take this quiz </a></p>");
				//<p>Take the quiz in practice mode</p>
				if(quiz.getCreator().equals(session.getAttribute("name")))
					out.println("<p>Edit Quiz - Since you are the owner</p>");
				}
		}
		%>	
	</div>
</div>


</body>
</html>
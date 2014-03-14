<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*" %>
<%@ page import="java.util.*, quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% String quizID = request.getParameter("id");
String receive = request.getParameter("received");
DBConnection db = (DBConnection)application.getAttribute("db");
Double rating = db.averageRating(Integer.parseInt(quizID));
musicManager mm= (musicManager) application.getAttribute("mm");
Integer numUsersRating = db.numUsersRating(Integer.parseInt(quizID));
Quiz quiz = db.getQuizAt(Integer.parseInt(quizID));
request.setAttribute("quiz", quiz);
String username = (String)session.getAttribute("name");
String randomSong = mm.getRandomSong();
ArrayList<ArrayList<Object>> leaderboard = db.getHighScorers(quiz.getID());

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
    <link href="css/star.css" rel="stylesheet">

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
      <a class="navbar-brand" href="userWelcome">Quiz Mania!</a>
    </div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
      <li><a href="userWelcome">Home <span class="glyphicon glyphicon-home"></a></li>
      <li><a href="quizPerformanceSummary">My Quiz History <span class="glyphicon glyphicon-th-list"></a></li>
      <li><a href="createQuiz.html">Create Quiz <span class="glyphicon glyphicon-pencil"></a></li>
      <li><a href="logout">Logout <span class="glyphicon glyphicon-off"></a></li>
      	
      </ul>
      <form action="SearchQuizzesServlet" method="GET" class="navbar-form navbar-right" role="form">
        <div class="form-group">
          <input name="search" type="text" placeholder="Search Quizzes..." class="form-control">
        </div>
        <button type="submit" class="btn btn-success">Search <span class="glyphicon glyphicon-search"></button>
      </form>
    </div><!--/.navbar-collapse -->
  </div>
</div><br>


<div class="container">
	
	
<audio   align="right" controls>
  <source  align="right" src="<%=randomSong%>" type="audio/mpeg">
  
  <embed height="50" width="100" src="horse.mp3">
</audio>
	
	<h1><%=quiz.getName() %></h1>

	<h4><%=quiz.getDescription() %></h4>


	<h4>Creator:<a href="userPage?ID=<%=quiz.getCreator()%>"><%=quiz.getCreator()%></a></h4>
	<p><h4>Rate this Quiz:</h4></p>
	
	
	<div class="rating">
    <span><input type="radio" name="rating" id="str5" value="5"><label for="str5"></label></span>
    <span><input type="radio" name="rating" id="str4" value="4"><label for="str4"></label></span>
    <span><input type="radio" name="rating" id="str3" value="3"><label for="str3"></label></span>
    <span><input type="radio" name="rating" id="str2" value="2"><label for="str2"></label></span>
    <span><input type="radio" name="rating" id="str1" value="1"><label for="str1"></label></span>
	</div>
    
    <%if(receive != null){if(receive.equals("true")){ %>
    <h4>Rating Received</h4>
    <%}} %>
    
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
//  Check Radio-box
    $(".rating input:radio").attr("checked", false);
    $('.rating input').click(function () {
        $(".rating span").removeClass('checked');
        $(this).parent().addClass('checked');
    });

    $('input:radio').change(
    function(){
        var userRating = this.value;
        <% String str= request.getParameter("id");%>
        var quiz="<%=str%>";
        var str1 = "rate?quiz=".concat(quiz);
        var str2 = "&stars=".concat(userRating);
        window.location = str1.concat(str2);
    }); 
});
</script>
 
 
    
    
    
	<h5><%if(leaderboard.size() > 0)
			out.println( db.getQuizStats(quiz) +"<td>"+rating+"</td><td>"+numUsersRating+"</td></tr></table>"); 
			%></h5>
	
	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-default">
				<%
				if(session.getAttribute("name") != null){
					ArrayList<ArrayList<Object>> myRecentPerformance = db.getMyRecentPerformance(username, quiz.getID());
					out.println("<div class=\"panel-heading\">My Recent Scores</div>");
					if(myRecentPerformance.size() > 0){
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
					} else {
						out.println("You have not taken this quiz yet");
					}
				}
				%>
				
			</div>
		</div>
		
		<div class="col-md-6">
			<!-- Highest performers table -->
			<div class="panel panel-default">
			<div class="panel-heading">Highest Scores</div>
			<% 
				if(leaderboard.size() > 0){
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
				<div class="panel-heading">Today's Top Scores</div>
				<% ArrayList<ArrayList<Object>> recentHighScores = db.getRecentHighScores(quiz.getID());
				if(recentHighScores.size() > 0){
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
				} else {
					out.println("<p>No scores today</p>");
				}
				%>
			</div>
		</div>
		
		<div class="col-md-6">
			<!-- Recent test takers table -->
			<div class="panel panel-default">
				<div class="panel-heading">Recent Scores</div>
				<% ArrayList<ArrayList<Object>> recentTestTakers = db.getRecentScores(quiz.getID());
				if(recentTestTakers.size() > 0){
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
				} else {
					out.println("<p>No recent scores</p>");
				}
				%>
			</div>
		</div>			
	</div>
	<div class="row">
		

		<%
		if(leaderboard.size() > 0){
			if(session.getAttribute("name") == null){
				out.println("<h2>Login to take the quiz: <a href=\"Homepage.jsp\"><img src=\"Login.jpg\"></img></a></h2>");
				
			}
			else{
				out.println("<p><a href=\"TakeQuiz.jsp?quizID=" + quizID + "\"><img src=\"takeQuiz2.png\"></img></a></p>");
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
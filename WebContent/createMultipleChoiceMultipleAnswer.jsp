<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@ page import="quiz.*" %>
    <%@ page import="java.text.*" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create Multiple Choice</title>

    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Custom styles for this template -->
    <link href="css/jumbotron.css" rel="stylesheet">
    

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
<h2 class="createQuestion">Create a Multiple Choice Multiple Answer Question</h2>

<!-- Select number of choices -->
<div class="btn-group">
	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
	  Select Number of Choices <span class="caret"></span>
	</button>
	<ul class="dropdown-menu" role="menu">	
		<%
		String num = request.getParameter("num");
		String quizID = request.getParameter("quizID");
		for (int i = 2; i <= 10; i++) {
			String choice = String.valueOf(i);
			String url = "createMultipleChoiceMultipleAnswer.jsp?num=" + num + "&quizID=" + quizID + "&choices=" + choice;
			out.println("<li><a href=" + url + ">" + choice + "</a></li>");
		}
		%>
	</ul>
</div><br><br>

<!-- Create multiple choice question form -->
<form action="CreateMultipleChoiceMultipleAnswerServlet" method="post">

	<div class="input-group input-group-lg">
		<span class="input-group-addon">Question: </span>
		<input type="text" name="question" class="form-control">
	</div><br>
	
	<%
	int numChoices = Integer.valueOf(request.getParameter("choices"));
	for (int i = 1; i <= numChoices; i++) {
		out.println("<div class=input-group input-group-lg>");
		out.println("<span class=input-group-addon><input type=\"checkbox\" value=\"choice\" name=\"check" + i + "\"> </span>");
		out.println("<input type=text name=\"choice" + i + "\" class=form-control>");
		out.println("</div>");
	}
	%><br>
	
	<input name="num" type="hidden" value="<%= request.getParameter("num") %>"/>
	<input name="quizID" type="hidden" value="<%= request.getParameter("quizID") %>"/>
	<input name="choices" type="hidden" value="<%= request.getParameter("choices") %>"/>
	
	<button type="submit" class="btn btn-default">Create</button>

</form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
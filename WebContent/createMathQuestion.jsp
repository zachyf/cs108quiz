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
    <title>Create Math Question</title>

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
<h2 class="createQuestion">Create a Math Question Type</h2>


<!-- Create multiple choice question form -->
<form action="CreateMathQuestionServlet" method="post">
		
		<label class="radio">
		  <input type="radio" name="mathType" value="Addition"> Addition
		</label>
		
		<label class="radio">
		  <input type="radio" name="mathType" value="Subtraction"> Subtraction
		</label>
		
		<label class="radio">
		  <input type="radio" name="mathType" value="Multiplication"> Multiplication
		</label>
		
		<label class="radio">
		  <input type="radio" name="mathType" value="Division"> Division
		</label>
	
	<input name="num" type="hidden" value="<%= request.getParameter("num") %>"/>
	<input name="quizID" type="hidden" value="<%= request.getParameter("quizID") %>"/>
	
	<button type="submit" class="btn btn-default">Create</button>

</form>

<!-- Question type dropdown menu -->
	<br><br>
	<div class="btn-group">
	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
	  Change Question Type <span class="caret"></span>
	</button>
	<ul class="dropdown-menu" role="menu">	
		
		<%			
		String num = request.getParameter("num");
		String quizID = request.getParameter("quizID");
		
		String url = "createQuestionResponse.jsp?num=" + num + "&quizID=" + quizID +"&numAnswers=1";
		out.println("<li><a href=" + url + " class=\"btn btn-large\" >Question Response</a></li><br>");

		url = "createFillInTheBlank.jsp?num=" + num + "&quizID=" + quizID + "&numAnswers=1";
		out.println("<li><a href=" + url + " class=\"btn btn-large\">Fill-In-The-Blank</a></li><br>");

		url = "createPictureResponse.jsp?num=" + num + "&quizID=" + quizID + "&numAnswers=1";
		out.println("<li><a href=" + url + " class=\"btn btn-large\">Picture Response</a></li><br>");

		url = "createMultipleChoice.jsp?num=" + num + "&quizID=" + quizID + "&choices=4";
		out.println("<li><a href=" + url + " class=\"btn btn-large\">Multiple Choice</a></li><br>");
		
		url = "createMultiAnswer.jsp?num=" + num + "&quizID=" + quizID + "&numAnswers=2";
		out.println("<li><a href=" + url + " class=\"btn btn-large\">Multiple Answer</a></li><br>");
		
		url = "createMultipleChoiceMultipleAnswer.jsp?num=" + num + "&quizID=" + quizID + "&choices=4";
		out.println("<li><a href=" + url + " class=\"btn btn-large\">Multiple Choice Multiple Answer</a></li><br>");
		
		url = "createMathQuestion.jsp?num=" + num + "&quizID=" + quizID;
		out.println("<li><a href=" + url + " class=\"btn btn-large\">Math Question</a></li><br>");
		%>
	</ul>
</div><br>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
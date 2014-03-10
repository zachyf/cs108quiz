<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create Questions</title>

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

<!-- Question type dropdown menu -->
<div class="btn-group">
	<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
	  Create Question #<%= request.getParameter("num") %> <span class="caret"></span>
	</button>
	<ul class="dropdown-menu" role="menu">	
		<%	
		
		String num = request.getParameter("num");
		String quizID = request.getParameter("quizID");
		
		String url = "createQuestionResponse.jsp?num=" + num + "&quizID=" + quizID;
		out.println("<li><a href=" + url + " class=\"btn btn-large\" >Question Response</a></li><br>");

		url = "createFillInTheBlank.jsp?num=" + num + "&quizID=" + quizID;
		out.println("<li><a href=" + url + " class=\"btn btn-large\">Fill-In-The-Blank</a></li><br>");

		url = "createPictureResponse.jsp?num=" + num + "&quizID=" + quizID;
		out.println("<li><a href=" + url + " class=\"btn btn-large\">Picture Response</a></li><br>");

		url = "createMultipleChoice.jsp?num=" + num + "&quizID=" + quizID + "&choices=4";
		out.println("<li><a href=" + url + " class=\"btn btn-large\">Multiple Choice</a></li><br>");

		%>
	</ul>
</div><br>

<br><br>
<form action="viewQuizTest.jsp">
	<input type="submit" value="Finish Quiz and View">
</form>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>

</body>
</html>
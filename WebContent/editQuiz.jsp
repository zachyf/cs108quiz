<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*" %>
<%@ page import="java.util.* %>
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

    <form class="form-signin" action="editQuizServlet" method="post" role="form">
    	<h1 class="form-signin-heading">Edit Quiz: <%=quiz.getName() %></h1>
    	
    	<div class="input-group input-group-lg">
			<span class="input-group-addon">Quiz Name: </span>
			<input type="text" name="name" class="form-control" value="<%=quiz.getName()%>">
		</div><br>

		<div class="input-group input-group-lg">
			<span class="input-group-addon">Description: </span>
			<input type="text" name="description" class="form-control" value="<%=quiz.getDescription()%>">
		</div>

		<label class="checkbox">
		  <input type="checkbox" name="random" value=random 
		  <%if(quiz.isRandomOrder()) out.println("checked"); %>> Random Order:
		</label>
		
		<label class="checkbox">
		  <input type="checkbox" name="single" <%if(quiz.isSinglePage()) out.println("checked"); %>> Single Page Quiz:
		</label>
		
		<label class="checkbox">
		  <input type="checkbox" name="immediate" <%if(quiz.isImmediateCorrection()) out.println("checked"); %>> Grade Immediately:
		</label>
		
		<label class="checkbox">
		  <input type="checkbox" name="practice" <%if(quiz.hasPracticeMode()) out.println("checked"); %>> Allow Practice Mode:
		</label>
		<input type=hidden name="id" value=<%=quiz.getID()%> >
      
      	<button class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="update">Submit Edits</button> 
      	<button class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="delete">Delete</button> 
      	<button class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="Cancel">Cancel</button> 
      	
    </form><br>
    

  </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>New Message</title>

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
      <a class="navbar-brand" href="HomepageBootstrap.jsp">Quiz Mania!</a>
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

<h2 class="sendMessage">Send a New Message</h2>

<!-- Send message form -->
<form action="SendMessage" method="post">	
	<div class="input-group input-group-lg">
		<span class="input-group-addon">To: </span>
		<%String s=request.getParameter("to"); %>
		<%if(s==null) s=""; %>
		<input type="text" name="to"  value="<%=s %>" class="form-control">
	</div><br>
	
	<div class="input-group input-group-lg">
		<span class="input-group-addon">Subject: </span>
		<input type="text" name="subject" class="form-control">
	</div><br>
	
	<div class="input-group input-group-lg">
		<span class="input-group-addon">Note: </span>
		<input type="text" name="note" class="form-control">
	</div><br>
	
	<input name="userName" type="hidden" value="<%=request.getParameter("user")%>"/>
	
	<button type="submit" class="btn btn-default">Send</button>	
</form>
<%String id=request.getParameter("to"); String url=""; %>
<%if(id!=null) url="userPage?ID="+id; %>
<%if(id!=null){%>
<a href="<%=url %>"><img src="userProfile.png" title="Return to <%=s%>'s Profile"></img></a>
<%}%>
<a href="userWelcome"><img src="home.jpg" title="Return Home "></img></a>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 
int quizID = Integer.parseInt(request.getParameter("quizID"));
DBConnection db = (DBConnection)application.getAttribute("db");
Quiz quiz = db.getQuizAt(quizID);
HttpSession ses = request.getSession();
String username = (String) ses.getAttribute("name");
Answer answer = new Answer(username, quiz);
quiz.addAnswer(answer);
session.setAttribute("answer", answer);
session.setAttribute("quiz", quiz);
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
	<% 
	db.getQuestions(quiz);
	Queue<Integer> q_order = quiz.getQuestionOrder();
	out.println("<h1>" + quiz.getName() + "</h1>");
	
	if(quiz.isSinglePage()){
		out.println("<form action=\"GradeQuiz.jsp\" method=\"post\">");
		while(!q_order.isEmpty())
			out.println(quiz.getQuestionAt(q_order.poll()));
	
	}
	else {
		out.println("<form action=\"MultiPageQuiz\" method=\"post\">");
		int questionNum = q_order.poll();
		out.println(quiz.getQuestionAt(questionNum));
		session.setAttribute("questionsLeft" + quizID, q_order);
		out.println("<br><input name=\"quizID\" type=\"hidden\" value=\"" + quizID + "\">");
		out.println("<br><input name=\"questionNum\" type=\"hidden\" value=\"" + questionNum + "\">");
	}
	out.println("<button type=\"submit\" class=\"btn btn-default\">Submit</button>");
	out.println("</form></p>");
	%>
</div>

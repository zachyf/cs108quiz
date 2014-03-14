<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
String curUser = (String)session.getAttribute("name");

Answer a = (Answer)session.getAttribute("answer");
a.endTimer();
Quiz quiz = (Quiz)session.getAttribute("quiz");

if(quiz.isSinglePage()){
	for(int i = 0; i < quiz.getNumQuestions(); i++) {
		Question question = quiz.getQuestion(i);
		String answer = null;
		String questionNum = String.valueOf(i);
		if (question.getType().equals("MultiAnswer")) {
			int numAnswers = Integer.valueOf(request.getParameter("numAnswers" + questionNum));
			StringBuilder buff = new StringBuilder();
			buff.append(request.getParameter(questionNum + " 0"));
			for (int j = 1; j < numAnswers; j++) {
				buff.append(", ");
				buff.append(request.getParameter(questionNum + " " + String.valueOf(j)));
			}
			answer = buff.toString();
		} else if (question.getType().equals("MultipleChoiceMultipleAnswer")){
			int numChoices = Integer.valueOf(request.getParameter("numChoices" + questionNum));
			StringBuilder buff = new StringBuilder();
			for (int j = 0; j < numChoices; j++) {
				String check = request.getParameter("check" + questionNum + " " + j);
				if (check != null && check.equals("checkAnswer")) {
					if (buff.length() > 0) {
						buff.append(", ");
					}
					String choice = request.getParameter("choice" + questionNum + " " + j);
					buff.append(request.getParameter("choice" + questionNum + " " + j));
				}
			}
			answer = buff.toString();
		} else {
			answer = request.getParameter(questionNum);	
		}
		a.setAnswer(question, answer);
	}
		//a.setAnswer(quiz.getQuestion(i), request.getParameter("" + i));
}
DBConnection db = (DBConnection)request.getServletContext().getAttribute("db");
db.addAnswerToDB(a);
db.bumpNumQuizesTaken(curUser);
session.removeAttribute("answer");
%>
<html>
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
	<h3>Your Score is: <%=Math.round((a.getScore() * 100))%>%</h3>
	<h4>You took <%=(a.getTimeToComplete()/1000) %> seconds to complete.</h4>
	<%
	for(int i = 0; i < quiz.getNumQuestions(); i++){
        Question question = quiz.getQuestion(i);
        out.println(question.displayQuestion(i + 1));
        out.println("Your answer: " + a.getAnswerToQuestion(question));
    }
	%>
	
	<%
	String url = "quizPage.jsp?id=" + quiz.getID();
	out.println("<br><br><a href=" + url + " class=\"btn btn-default\">Back to quiz page</a>");
	%>
</div>

</body>
</html>
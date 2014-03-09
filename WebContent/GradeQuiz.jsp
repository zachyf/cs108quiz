<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
String curUser = (String)session.getAttribute("name");

Answer a = (Answer)session.getAttribute("answer");
a.endTimer();
Quiz q = a.getQuiz();
if(q.isSinglePage()){
	for(int i = 0; i < q.getNumQuestions(); i++)
		a.setAnswer(q.getQuestion(i), request.getParameter("" + i));
}
DBConnection db = (DBConnection)request.getServletContext().getAttribute("db");
if(db == null){
	db = new DBConnection();
}
//a.addToDB(db);
db.bumpNumQuizesTaken(curUser);
session.removeAttribute("answer");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=q.getName()%></title>
</head>
<body>
<h3>Your Score is: <%=(a.getScore() * 100)%>%</h3>
<h4>You took <%=(a.getTimeToComplete()/1000) %> seconds to complete.</h4>
<%
for(int i = 0; i < q.getNumQuestions(); i++){
	Question question = q.getQuestion(i);
	out.println(question.displayQuestion());
	out.println("Your answer: " + a.getAnswerToQuestion(question));
}
%>

</body>
</html>
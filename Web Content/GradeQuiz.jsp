<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, quiz.*" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
Answer a = (Answer)request.getSession().getAttribute("answer");
Quiz q = a.getQuiz();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=q.getName()%></title>
</head>
<body>
<h3>Your Score is: <%=(a.getScore() * 100)%>%</h3>
<%
for(int i = 0; i < q.getNumQuestions(); i++){
	Question question = q.getQuestion(i);
	out.println(question.displayQuestion());
	out.println("Your answer: " + a.getAnswerToQuestion(question));
}
%>

</body>
</html>
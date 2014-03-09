<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@ page import="quiz.*" %>
    <%@ page import="java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Quiz Test</title>
</head>
<body>

<h1>View Quiz Test</h1>
<%
ArrayList<Question> questions = (ArrayList<Question>)application.getAttribute("questions");

for (int i = 0; i < questions.size(); i++) {
	out.println(questions.get(i).getQuestion());
	out.println(questions.get(i).getAnswer());
}
out.println("<p><a href=\"index.jsp\"> Go to Homepage </a></p>");
%>

</body>
</html>
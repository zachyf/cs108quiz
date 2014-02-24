<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@ page import="quiz.*" %>
    <%@ page import="java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1 class="createQuestion">Create a Question Response</h1>

<form action="CreateQuestionResponseServlet" method="post">
Question: <input type="text" name="question" /><br>
Answer: <input type="text" name="answer" /><br>

<input name="num" type="hidden" value="<%= request.getParameter("num") %>"/>
<input name="quizID" type="hidden" value="<%= request.getParameter("quizID") %>"/>
<input type="submit" value="Create"/><br>
</form>

</body>
</html>
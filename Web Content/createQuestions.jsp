<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
String num = request.getParameter("num");
String quizID = request.getParameter("quizID");
out.println("Create question #" + num + "<br>");
String url = "createQuestionResponse.jsp?num=" + num + "&quizID=" + quizID;
out.println("<a href=" + url + ">Create a Question-Response</a><br>");

url = "createFillInTheBlank.jsp?num=" + num + "&quizID=" + quizID;
out.println("<a href=" + url + ">Create a Fill-In-The-Blank question</a><br>");

url = "createPictureResponse.jsp?num=" + num + "&quizID=" + quizID;
out.println("<a href=" + url + ">Create a Picture-Response question</a><br>");

out.println("<br><br>");
out.println("<a href=viewQuizTest.jsp>Finish Quiz and View</a>");

%>

</body>
</html>
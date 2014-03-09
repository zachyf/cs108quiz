<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Message</title>
</head>
<body>
<h3>Send a new note:</h3>

<form action="SendMessage" method="post">
	To:<input type="text" name="to"><br>
	Subject:<input type="text" name="subject"><br>	
	Note: <textarea rows="4" cols="50" name="note"></textarea><br>
	<input name="userName" type="hidden" value="<%=request.getParameter("user")%>"/>
	<input type="submit" value="Send">
</form>
<br><a href="userWelcome"><img src="home.jpg" title="Return Home "></img></a>
</body>
</html>

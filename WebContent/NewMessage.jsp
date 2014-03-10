<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Message</title>
</head>
<body>
<a href="logout" align="right"><img src="logout.jpg" title="Click to Logout" align="right"></img></a>
<h3>Send a new note:</h3>

<form action="SendMessage" method="post">
	To:<input type="text" name="to"><br>
	Subject:<input type="text" name="subject"><br>	
	Note: <input type="text" name="note"size="50"><br>
	<input name="userName" type="hidden" value="<%=request.getParameter("user")%>"/>
	<input type="submit" value="Send">
</form>
<br><a href="userWelcome"><img src="home.jpg" title="Return Home "></img></a>
</body>
</html>

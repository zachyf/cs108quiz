<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%HttpSession ses = request.getSession();%>
<%String username = (String) ses.getAttribute("name");%>
<title>Create Account</title>
</head>
<body>
<h1>Your Message has been sent</h1>
	<form action="MessageSent" method="post">
		<input type="submit" value="Return to user home"><br>
	</form>
</body>
</html>
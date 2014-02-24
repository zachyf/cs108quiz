<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
<h1>Welcome to Quiz Mania</h1>
<p>Please log in to begin the fun!</p>
<form action="Login" method="post">
	User Name: <input type="text" name="userName"><br>
	Password: <input type="text" name="password">
	<input type="submit" value="Submit"><br>
</form>
<p><a href="CreateAccount.jsp">Create New Account</a></p>
</body>
</html>
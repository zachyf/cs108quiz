<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
</head>
<body>
<h1>Create New Account</h1>
<p>Please enter proposed name and password.</p>
	<form action="accountCheck" method="post">
		User Name: <input type="text" name="userName"><br>
		Password: <input type="password" name="password"><br>
		Re-Enter Password: <input type="password" name="reenterpassword">
		<input type="submit" value="Submit"><br>
	</form>
</body>
</html>
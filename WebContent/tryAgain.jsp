<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

    <title>Information Incorrect</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">
<body>

	<!-- Create Quiz Form -->
    <div class="container">
      <form class="form-signin" action="Login" method="post" role="form">
      	<h2 class="form-signin-heading">Please Try Again</h2>
        <p><font color="FF0000">Invalid username or password</font></p>
        <input type="text" class="form-control" name="userName" placeholder="Username" required autofocus>
        <input type="password" class="form-control" name="password" placeholder="Password" required>

        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      	<br><a href="CreateAccount.jsp">Create an account</a>
      </form><br>
    </div> <!-- /container -->

</html>
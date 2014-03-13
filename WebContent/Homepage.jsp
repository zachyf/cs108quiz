<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

    <title>Sign In</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

  </head>  

  <body>
	
	<!-- Create Quiz Form -->
    <div class="container">
      <form class="form-signin" action="Login" method="post" role="form">
      	<h2 class="form-signin-heading">Welcome to Quiz Mania!</h2>
        <p>Please sign in</p>
        <%
        String cookieName = "userName";
    	String cookieName2 = "password";
        Cookie[] cookies = request.getCookies();
        int check=0;
        if (cookies != null) 
        {
            for(int i=0; i<cookies.length; i++) 
            {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) 
                { check+=1;%>
                	<input type="text" class="form-control" name="userName" value=<%=cookie.getValue() %> required autofocus>
                    
               <% }
                if(cookieName2.equals(cookie.getName())){check+=1;%>
                <input type="password" class="form-control" name="password" value=<%=cookie.getValue() %>  required>
                <%}
            }
        }
        if(check==0){%>
            <input type="text" class="form-control" name="userName" placeholder="Username" required autofocus>
        	<input type="password" class="form-control" name="password" placeholder="Password" required>
       <% }  
      
	    String id = request.getParameter("quizID");
		if(id != null)
			out.println("<input name=\"quizID\" type=\"hidden\" value=\"" + id + "\">");
		%>
        
        <label class="checkbox">
          <input type="checkbox" name="check" value="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      	<br><a href="CreateAccount.jsp">Create an account</a>
      </form><br>
    </div> <!-- /container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  </body>
</html>


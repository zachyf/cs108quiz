package quiz;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		String userName = (String) request.getParameter("userName");
		String password = (String) request.getParameter("password");
		String check = (String) request.getParameter("check");
		System.out.println(check);
		if(check!=null){
		if(check.equals("remember-me")){
			Cookie userCookie = new Cookie("userName", userName);
			Cookie userCookie2 = new Cookie("password", password);
			userCookie.setMaxAge(60*60*24); //Store cookie for 1 day
			response.addCookie(userCookie);
			response.addCookie(userCookie2);
		}
		}
		try {
			if (!DB.userExists(userName) || !DB.passwordCheck(password,userName)){
				RequestDispatcher dispatch = request.getRequestDispatcher("tryAgain.jsp"); 
				dispatch.forward(request, response); 
			}else{
				HttpSession session = request.getSession();
				session.setAttribute("name",userName);
				String animal=DB.getAnimal(userName);
				session.setAttribute("animal",animal);
				String id = request.getParameter("quizID");
				RequestDispatcher dispatch;
				if(id != null)
					dispatch = request.getRequestDispatcher("TakeQuiz.jsp?quizID=" + id); 
				else
					dispatch = request.getRequestDispatcher("userWelcome"); 
				dispatch.forward(request, response); 

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	
	}

}

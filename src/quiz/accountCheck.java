package quiz;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class accountCheck
 */
@WebServlet("/accountCheck")
public class accountCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public accountCheck() {
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
		String reenter = (String) request.getParameter("reenterpassword");
		HttpSession session = request.getSession();
		session.setAttribute("name",userName);
		if(DB.accountExists(userName)){
			RequestDispatcher dispatch = request.getRequestDispatcher("nameInUse.jsp"); 
			dispatch.forward(request, response); 
		}else if(!password.equals(reenter)){
			RequestDispatcher dispatch = request.getRequestDispatcher("passwordsDontMatch.jsp"); 
			dispatch.forward(request, response); 
		}else{
		
			try {
				AnimalManager AM = (AnimalManager) context.getAttribute("am");
				String animal=AM.getAnimal();
				DB.createAccount(userName, password,animal);
				AM.nextAnimal();
				session.setAttribute("animal",animal);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("userWelcome"); 
			dispatch.forward(request, response); 

		}

	}
}
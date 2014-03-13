package quiz;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Unfriend
 */
@WebServlet("/Unfriend")
public class Unfriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Unfriend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		HttpSession ses = request.getSession();
		String loggedInUser = (String) ses.getAttribute("name");
		String userName = (String) request.getParameter("userName");
		PrintWriter out = response.getWriter();
		try{
		if (!DB.userExists(userName)){
			System.out.println("Can't unfriend " + userName + " because " + userName + " isn't a user in the quizapp.");
		}else{
			DB.unfriend(userName, loggedInUser);
			out.println("User " + userName + " has been unfriended");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("userPage?ID="+userName); 
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		HttpSession ses = request.getSession();
		String loggedInUser = (String) ses.getAttribute("name");
		String userName = (String) request.getParameter("userName");
		PrintWriter out = response.getWriter();
		try{
		if (!DB.userExists(userName)){
			System.out.println("Can't unfriend " + userName + " because " + userName + " isn't a user in the quizapp.");
		}else{
			DB.unfriend(userName, loggedInUser);
			out.println("User " + userName + " has been unfriended");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("userWelcome"); 
		dispatch.forward(request, response); 
	}

}

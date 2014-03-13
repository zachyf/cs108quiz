package quiz;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class FriendRequest
 */
@WebServlet("/FriendRequest")
public class FriendRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		String userName = (String) request.getParameter("userName");
		HttpSession ses = request.getSession();
		String loggedInUser = (String) ses.getAttribute("name");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
		try {
			if(DB.userExists(userName)==false){
				out.print("User "+userName+" does not exist.");
		
			}else{
				if(DB.alreadyFriends(userName,loggedInUser) || DB.alreadyFriends(loggedInUser,userName)){
					out.println("You are already friends with "+userName+".");
				}else if(DB.alreadyPending(userName,loggedInUser)){
					out.print("User "+userName+" has already sent you a request. Check your requests.");
				}else if(DB.alreadyPending(loggedInUser,userName)){
					out.print("You have already requested "+userName+" .Wait for a response.");
				}else{
					DB.addRequest(loggedInUser,userName);
					out.print("A friend request has just been sent to "+userName+".");

				}
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("userPage?ID="+userName); 

			dispatch.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		String userName = (String) request.getParameter("userName");
		HttpSession ses = request.getSession();
		String loggedInUser = (String) ses.getAttribute("name");
		//response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
		try {
			if(DB.userExists(userName)==false){
				out.print("User "+userName+" does not exist.");
		
			}else{
				if(DB.alreadyFriends(userName,loggedInUser) || DB.alreadyFriends(loggedInUser,userName)){
					out.println("You are already friends with "+userName+".");
				}else if(DB.alreadyPending(userName,loggedInUser)){
					out.print("User "+userName+" has already sent you a request. Check your requests.");
				}else if(DB.alreadyPending(loggedInUser,userName)){
					out.print("You have already requested "+userName+" .Wait for a response.");
				}else{
					DB.addRequest(loggedInUser,userName);
					out.print("A friend request has just been sent to "+userName+".");

				}
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("userWelcome"); 

			dispatch.forward(request, response); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

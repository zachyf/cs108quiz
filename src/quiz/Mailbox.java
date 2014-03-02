package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Mailbox
 */
@WebServlet("/Mailbox")
public class Mailbox extends HttpServlet {
	private DBConnection DB;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mailbox() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ID =request.getParameter("ID");
		String accept =request.getParameter("accept");
		ServletContext context = request.getServletContext();
		DB = (DBConnection) context.getAttribute("DBConnection");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		String username = (String) ses.getAttribute("name");

		response.setContentType("text/html; charset=UTF-8");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<title> Welcome "+username+"</title>");
		out.println("</head>");
		out.println("<body>");
		if(ID!=null && accept!=null){
			if(accept.equals("true")){
				try {
					DB.setRequestAsViewed(ID,username);
					DB.addFriend(ID,username);
					out.println("Congrats! You have just accepted "+ID+"'s friend request!");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					DB.setRequestAsViewed(ID,username);
					out.println("You have just rejected "+ID+"'s friend request.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			int numRequests=DB.getNumRequests(username);
			out.println("<br>");
			if(numRequests==0){
				out.println("No new friend requests :(");
			}else{
				if(numRequests==1){
					out.println("You have "+DB.getNumRequests(username)+" friend request:");
				}else{
					out.println("You have "+DB.getNumRequests(username)+" friend requests:");
				}
				out.println("<br>");
				ArrayList<String> friends = DB.usersWhoSentRequests(username);
				for(int i=0;i<numRequests;i++){
					out.println(friends.get(i)+" would like to be your friend on Quiz Mania.");
					out.println("<a href=\"Mailbox?ID="+friends.get(i)+"&accept=true\"><img src=\"acceptButton.jpg\" title=\"Accept Friend Request\"></img></a>");
					out.println("<a href=\"Mailbox?ID="+friends.get(i)+"&accept=false\"><img src=\"rejectButton.jpg\" title=\"Reject Friend Request\"></img></a>");
					out.println("<br>");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//same as requests but for messages
		try {
			int numRequests=DB.getNumRequests(username);
			out.println("<br>");
			if(numRequests==0){
				out.println("No new messages :(");
			}else{
				if(numRequests==1){
					out.println("You have "+DB.getNumRequests(username)+" friend request:");
				}else{
					out.println("You have "+DB.getNumRequests(username)+" friend requests:");
				}
				out.println("<br>");
				ArrayList<String> friends = DB.usersWhoSentRequests(username);
				for(int i=0;i<numRequests;i++){
					out.println(friends.get(i)+" would like to be your friend on Quiz Mania.");
					out.println("<a href=\"Mailbox?ID="+friends.get(i)+"&accept=true\"><img src=\"acceptButton.jpg\" title=\"Accept Friend Request\"></img></a>");
					out.println("<a href=\"Mailbox?ID="+friends.get(i)+"&accept=false\"><img src=\"rejectButton.jpg\" title=\"Reject Friend Request\"></img></a>");
					out.println("<br>");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

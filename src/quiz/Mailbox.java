package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
		ServletContext context = request.getServletContext();
		DB = (DBConnection) context.getAttribute("DBConnection");
		HttpSession session = request.getSession();
		if(request.getParameter("Time")!=null && request.getParameter("From")!=null){
			try {
				Message m =DB.findMessage(request.getParameter("Time"),session.getAttribute("name").toString(),request.getParameter("From"));
				DB.readMessage(m);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String ID =request.getParameter("ID");
		String accept =request.getParameter("accept");
		
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
		out.println("<a href=\"logout\" align=\"right\"><img src=\"logout.jpg\" title=\"Click to Logout\" align=\"right\"></img></a>");
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

				out.println("<h3>No new friend requests :(</h3>");
			}else{
				if(numRequests==1){
					out.println("<h3>You have "+DB.getNumRequests(username)+" friend request:</h3>");
				}else{
					out.println("<h3>You have "+DB.getNumRequests(username)+" friend requests:</h3>");

				}
				out.println("<br>");
				ArrayList<String> friends = DB.usersWhoSentRequests(username);
				for(int i=0;i<numRequests;i++){
					out.println("<a href=\"userPage?ID="+friends.get(i)+"\">"+friends.get(i)+"</a> would like to be your friend on Quiz Mania.");
					out.println("<a href=\"Mailbox?ID="+friends.get(i)+"&accept=true\"><img src=\"acceptButton.jpg\" title=\"Accept Friend Request\"></img></a>");
					out.println("<a href=\"Mailbox?ID="+friends.get(i)+"&accept=false\"><img src=\"rejectButton.jpg\" title=\"Reject Friend Request\"></img></a>");
					out.println("<br>");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int numRequests=DB.getNumUnread(username);
		out.println("<br>");
		if(numRequests==0){
			out.println("<h3>No new messages :(</h3>");
		}else{
			if(numRequests==1){
				out.println("<h3>You have "+numRequests+" message:</h3>");
			}else{
				out.println("<h3>You have "+numRequests+" messages:</h3>");
			}
			out.println("<br>");
			ArrayList<Message> unreadMessages = new ArrayList<Message>();
			ArrayList<Message> ml = DB.getMessages(username);
			for (int i = 0; i < ml.size(); ++i){
				if (!ml.get(i).getRead()){
					unreadMessages.add(ml.get(i));
				}
			}
			ml = unreadMessages;
			out.println("<table style=\"width:300px\"><tr><th>From</th><th>Subject</th><th>Time</th>\n\t<th>Note</th><th>Mark As Read</th>\n</tr>");
			for(int i=0;i<ml.size();i++){
				String date = new SimpleDateFormat("HH:mm MM/dd/yyyy").format( ml.get(i).getSentTime());
				out.println("<tr><td><a href=\"userPage?ID=" + ml.get(i).getTo() + "\">"+ ml.get(i).getTo() +"</a></td><td>" + ml.get(i).getSubject() + "</td><td>" + date + "</td><td>" + ml.get(i).getMessage() + "</td><td><a href=\"Mailbox?From="+ ml.get(i).getTo()+"&Time="+ ml.get(i).getSentTime()+"\"><img src=\"markAsRead.jpg\"title=\"Mark As Read \"></img></a></td></tr>");
			}
			out.println("</table>");
		}
		
		out.println("<br><a href=\"userWelcome\"><img src=\"home.jpg\" title=\"Return Home \"></img></a>");
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

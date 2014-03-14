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
		out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("<link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"bootstrap/css/bootstrap-theme.min.css\" rel=\"stylesheet\">");
		out.println("<link href=\"css/jumbotron.css\" rel=\"stylesheet\">");
		out.println("<title>Mailbox</title>");
		out.println("</head>");
		out.println("<body>");
		
		// Nav bar html
		out.println("<div class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">");
		out.println("<div class=\"container\"><div class=\"navbar-header\">");
		out.println("<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">");
		out.println("<span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span>");
		out.println("</button>");
		out.println("<a class=\"navbar-brand\" href=\"userWelcome\">Quiz Mania!</a>");
		out.println("</div><div class=\"navbar-collapse collapse\">");
		out.println("<ul class=\"nav navbar-nav\">");
		out.println("<li><a href=\"userWelcome\">Home <span class=\"glyphicon glyphicon-home\"></span></a></li>");
		out.println("<li><a href=\"quizPerformanceSummary\">My Quiz History <span class=\"glyphicon glyphicon-th-list\"></a></li>");
		out.println("<li><a href=\"createQuiz.html\">Create Quiz <span class=\"glyphicon glyphicon-pencil\"></a></li>");
		out.println("<li><a href=\"logout\">Logout <span class=\"glyphicon glyphicon-off\"></a></li>");
		out.println("</ul>");
		out.println("<form action=\"SearchQuizzesServlet\" method=\"GET\" class=\"navbar-form navbar-right\" role=\"form\">");
		out.println("<div class=\"form-group\">");
		out.println("<input name=\"search\" type=\"text\" placeholder=\"Search Quizzes...\" class=\"form-control\">");
		out.println("</div><button type=\"submit\" class=\"btn btn-success\">Search <span class=\"glyphicon glyphicon-search\"></button>");
		out.println("</form>");
		out.println("</div></div></div>");
		
		out.println("<div class=\"container\"><div class=\"navbar-header\">");
		if(username!=null){
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

				out.println("<h3>No new friend requests</h3>");
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
			out.println("<h3>No new messages</h3>");
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
			out.println("<table class=\"table\"><tr><th>From</th><th>Subject</th><th>Time</th>\n\t<th>Note</th><th>Mark As Read</th>\n</tr>");
			for(int i=0;i<ml.size();i++){
				String date = new SimpleDateFormat("HH:mm MM/dd/yyyy").format( ml.get(i).getSentTime());
				out.println("<tr><td><a href=\"userPage?ID=" + ml.get(i).getTo() + "\">"+ ml.get(i).getTo() +"</a></td><td>" + ml.get(i).getSubject() + "</td><td>" + date + "</td><td>" + ml.get(i).getMessage() + "</td><td><a href=\"Mailbox?From="+ ml.get(i).getTo()+"&Time="+ ml.get(i).getSentTime()+"\"><img src=\"markAsRead.jpg\"title=\"Mark As Read \"></img></a></td></tr>");
			}
			out.println("</table>");
		}
		
		out.println("</div>");
		}else{
			out.println("<h2>Please Login: <a href=\"Homepage.jsp\"><img src=\"Login.jpg\"></img></a></h2>");
		}
		out.println("</body></html>");
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

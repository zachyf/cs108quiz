package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MailboxFull
 */
@WebServlet("/MailboxFull")
public class MailboxFull extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MailboxFull() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		DBConnection DB = (DBConnection) context.getAttribute("DBConnection");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession ses = request.getSession();
		String username = (String) ses.getAttribute("name");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("<title>"+username+"'s Mailbox</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h2>"+username+"'s Mailbox</h2>");
		ArrayList<Message> ml = DB.getMessages(username);
		if(ml.size()!=0){
		out.println("<table style=\"width:500px\"><tr><th>From</th><th>Subject</th><th>Time</th>\n\t<th>Note</th>\n</tr>");
		
		for(int i = 0; i < ml.size(); ++i){
			//make it limited size and scrolling 
			String date = new SimpleDateFormat("HH:mm MM/dd/yyyy").format( ml.get(i).getSentTime());
			out.println("<tr><td><a href=\"userPage?ID=" + ml.get(i).getTo() + "\">"+ ml.get(i).getTo() +"</a></td><td>" + ml.get(i).getSubject() + "</td><td>" + date + "</td><td>" + ml.get(i).getMessage() + "</td></tr>");
			
		}
		out.println("</table>");
		}else{
			out.println("<h4>You have no recent messages. </h4>");
		}
		out.println("<br><a href=\"userWelcome\"><img src=\"home.jpg\" title=\"Return Home \"></img></a>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

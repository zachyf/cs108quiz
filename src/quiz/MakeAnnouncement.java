package quiz;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
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
 * Servlet implementation class MakeAnnouncement
 */
@WebServlet("/MakeAnnouncement")
public class MakeAnnouncement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeAnnouncement() {
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
		String note = (String) request.getParameter("note");
		HttpSession ses = request.getSession();
		String loggedInUser = (String) ses.getAttribute("name");
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
		DB.addAnnouncement(loggedInUser, note);
		ArrayList<String> users = DB.getAllUsers();
		for (int i = 0; i < users.size(); ++i){
			java.util.Date date = new java.util.Date();
			Timestamp t = new Timestamp(date.getTime());
			Message m  = new Message(users.get(i), loggedInUser, "ANNOUNCEMENT", note, t, 0);
			DB.insertMessage(m);
		}
		out.print("You announced " + note);
		RequestDispatcher dispatch = request.getRequestDispatcher("userWelcome"); 
		dispatch.forward(request, response); 

	}

}

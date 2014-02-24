package ListenerPackage;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import quiz.DBConnection;
import quiz.QuizManager;


/**
 * Application Lifecycle Listener implementation class quizListener
 *
 */
@WebListener
public class quizListener implements ServletContextListener, HttpSessionListener {

    /**
     * Default constructor. 
     */
    public quizListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	ServletContext sc = arg0.getServletContext();
    	DBConnection db = new DBConnection();
        sc.setAttribute("db", db);
    	QuizManager qm = new QuizManager(db);
        sc.setAttribute("QuizManager", qm);
     
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        DBConnection db = (DBConnection)arg0.getServletContext().getAttribute("db");
        db.closeConnection();
    }
	
}

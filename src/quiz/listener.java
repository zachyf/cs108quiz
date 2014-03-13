package quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;





/**
 * Application Lifecycle Listener implementation class listener
 *
 */
@WebListener
public class listener implements ServletContextListener {
	DBConnection DB;
    /**
     * Default constructor. 
     */
    public listener() {
        // TODO Auto-generated constructor stub
    }

   
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	try {
			DB = new DBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        ServletContext context=arg0.getServletContext();
        AnimalManager am = new AnimalManager();
        adsManager adsManager = new adsManager();
        musicManager musicManager = new musicManager();
        context.setAttribute("mm",musicManager);
        context.setAttribute("adsManager",adsManager); 
        context.setAttribute("am",am); 
        context.setAttribute("DBConnection",DB); 
        context.setAttribute("db", DB);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    		DB.close();
    }
	
}

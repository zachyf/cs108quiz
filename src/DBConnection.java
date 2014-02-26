package quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBConnection Class
 * @author jrfutrell
*/
public class DBConnection {
	private Connection con;
	
	public DBConnection(){
		try {
			//Open connection to database with information from MyDBInfo class
			Class.forName("com.mysql.jdbc.Driver"); 
			this.con = DriverManager.getConnection
			( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
		} catch (SQLException e) {
	         e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String query){
		try {
			Statement stmt = this.con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			return stmt.executeQuery(query);
		} catch (SQLException e) {
	         e.printStackTrace();
		} 
		return null;
	}
	
	public void updateDB(String insertion){
		try {
			Statement stmt = this.con.createStatement();
			stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);
			stmt.executeUpdate(insertion);
		} catch (SQLException e) {
	         e.printStackTrace();
		} 
	}
	
	public void closeConnection(){
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

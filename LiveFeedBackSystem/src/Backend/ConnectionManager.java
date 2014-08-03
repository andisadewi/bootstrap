package Backend;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingWorker.StateValue;

public class ConnectionManager {
	//TODO change the path to be relative
	//private static String dbURL = "jdbc:derby:../database";
	private static String dbURL = "jdbc:derby://localhost:1527/database;create=true";
	
    private static Connection conn = null;
	public static ConnectionManager Instance;
	
	public static void Initialize() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Instance = new ConnectionManager();
		Instance.openConnection();
		
	}
	
	static {
			Instance = new ConnectionManager();
	}
	
	public void openConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
//		System.out.println("current working directory = " + System.getProperty("user.dir"));
		File f = new File("../database");
		if (!f.exists() || !f.isDirectory()) {
		   System.out.println("database is not found!");
		   return;
		}
		f = null;
		createConnection();
	}
	
	public static void closeConnection() throws SQLException{
		conn.close();
	}
	public  Statement createStatement() throws SQLException{
		if(conn == null)
			try {
				createConnection();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return  conn.createStatement(); 
		
	}
	
	
	private static void createConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
    {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            conn = DriverManager.getConnection(dbURL); 
    }
    


}

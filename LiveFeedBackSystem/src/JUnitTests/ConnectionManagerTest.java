package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;

import Backend.ConnectionManager;

public class ConnectionManagerTest {

	@Test
	public void connectionTest() {
		// test that database connection can be established
		try {
			ConnectionManager.Initialize() ;
			
			ConnectionManager.closeConnection() ;
			
		} catch(Exception e) {
			fail("Database Connection could not be established:\n" + e.getMessage());
		}
	}
	
	@Test
	public void databaseTest() {
		// test that database contains all tables
		// test that all tables have the right default data expected by the query tests

		try {
			ConnectionManager.Initialize() ;
			
			Statement statement = ConnectionManager.Instance.createStatement();
			ResultSet r;
			
			// there are only 3 roles
			statement.execute("SELECT COUNT(*) FROM roles");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 3);
			r.close();
			
			// there are 10 rights
			statement.execute("SELECT COUNT(*) FROM rights");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 10);
			r.close();
		
			// there are 18 roles-rights combinations
			statement.execute("SELECT COUNT(*) FROM rights_roles");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 18);
			r.close();
			
			// there are 2 default users (phillip and jakob)
			statement.execute("SELECT COUNT(*) FROM \"user\"");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 2);
			r.close();
			
			// lecture is empty
			statement.execute("SELECT COUNT(*) FROM lecture");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 0);
			r.close();
			
			// question is empty
			statement.execute("SELECT COUNT(*) FROM question");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 0);
			r.close();
			
			// voting is empty
			statement.execute("SELECT COUNT(*) FROM voting");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 0);
			r.close();
					
			// survey is empty
			statement.execute("SELECT COUNT(*) FROM survey");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 0);
			r.close();
			
			// response is empty
			statement.execute("SELECT COUNT(*) FROM response");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 0);
			r.close();
			
			// studentResponse is empty
			statement.execute("SELECT COUNT(*) FROM studentResponse");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 0);
			r.close();
			
			// speedButton is empty
			statement.execute("SELECT COUNT(*) FROM speedButton");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 0);
			r.close();
			
			// volumeButton is empty
			statement.execute("SELECT COUNT(*) FROM volumeButton");
			r = statement.getResultSet();
			r.next();
			assertTrue("Falsche Datenbankverbindung konfiguriert.", r.getInt(1) == 0);
			r.close();
			
			statement.close() ;
			ConnectionManager.closeConnection() ;
			
		} catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
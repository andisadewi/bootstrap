package Backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Roles implements IEntity {

	private int roleID;
	private String roleName;
	
	public Roles(int roleID, String roleName){
		this.roleID = roleID;
		this.roleName = roleName;
	}
	
//	CREATE TABLE APP.roles(
//			roleID int PRIMARY KEY,
//			roleName VARCHAR(32) NOT NULL
//		);
	
	
	
	public static ArrayList<Roles> select(Statement stm) throws SQLException{
		ResultSet results = stm.executeQuery("select * from roles");
        ArrayList<Roles> arr = new ArrayList<Roles>();
        while(results.next())
        {
        	int roleID = results.getInt(1);
            String roleName = results.getString(2);
            
            arr.add(new Roles(roleID,roleName));
        }
        results.close();
        return arr;
	}
	
	public void insert(Statement stm) throws SQLException  {
		String cmd = "INSERT INTO roles (roleID, roleName) VALUES ("+roleID+",'"+ roleName +"')"; 
	    System.out.println(cmd);
		stm.execute(cmd);
	}
	
	public void delete(Statement stm) throws SQLException {
		String cmd = "Delete from roles where roleID=" + roleID + ""; 
		stm.execute(cmd);
	}
	
	
	public static String selectroleName(Statement stm, int roleID) throws SQLException {
		String roleName = "" ;
		String cmd="select * from roles where roleID="+roleID+"";
		System.out.println(cmd);
		ResultSet result = stm.executeQuery(cmd);
		
		while(result.next()){
			roleName = result.getString(2);
		}
        return roleName;

	}
	public static Roles select(Statement stm, String loginName) throws SQLException{
		String cmd="select * from \"user\" where loginName='"+loginName+"'";
		System.out.println(cmd);
		ResultSet result = stm.executeQuery(cmd);
		Roles rol = null;
		result.next();
		rol = new Roles(result.getInt(2), result.getString(1));
		
		result.close();
		
		return rol;
	}
	
	
	
	public int getroleID() {
		return roleID;
	}

	public void setroleID(int roleID) {
		this.roleID = roleID;
	}

	public String getroleName() {
		return roleName;
	}

	public void setroleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public void update(Statement stm) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	
}

package Backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Backend.Roles;

public class User implements IEntity {
	private String loginName;
	private int roleID;

	// CREATE table APP."user"(
	// loginName VARCHAR(40) PRIMARY KEY ,
	// -- password VARCHAR(256),
	// role int --begrenzung check auf 0/1/2
	// FOREIGN KEY(roleID) REFERENCES APP.roles(roleID)
	// );

	private Statement stm;

	public User(String loginName, int roleID) {
		this.loginName = loginName;
		this.roleID = roleID;
	}  

	public static ArrayList<User> select(Statement stm) throws SQLException {
		ResultSet results = stm.executeQuery("select * from \"user\"");
		ArrayList<User> arr = new ArrayList<User>();
		while (results.next()) {
			String loginName = results.getString(1);
			int roleID = results.getInt(2);

			arr.add(new User(loginName, roleID));
		}
		results.close();
		return arr;
	}

	public static User select(Statement stm, String loginName)
			throws SQLException {
		String cmd = "select * from \"user\" where loginName='" + loginName	+ "'";
		System.out.println(cmd);
		ResultSet result = stm.executeQuery(cmd);
		User res;
		result.next();
		res = new User(result.getString(1), result.getInt(2));

		result.close();

		return res;
	}

	public static Roles selectRolle(Statement stm, String loginName) throws SQLException {
		ResultSet results = stm.executeQuery("select * from \"user\" where loginName='"	+ loginName + "'");
		results.next();
		int roleID = results.getInt(2);
		String roleName = Roles.selectroleName(stm, roleID);

		Roles roles = new Roles(roleID, roleName);

		return roles;
	}

	public void update(Statement stm) throws SQLException {
		String cmd = "update \"user\" set role=" + roleID + " where loginName='" + loginName + "'";
		System.out.println(cmd);
		stm.execute(cmd);
	}

	public void updateRolle(Statement stm, int userRole) throws SQLException {

		int rolle = userRole;

		String cmd = "update \"user\" set role=" + rolle + " where loginName='"	+ loginName + "' ";
		System.out.println(cmd);
		stm.execute(cmd);
	}
	

	public void insert(Statement stm) throws SQLException {
		String cmd = "INSERT INTO \"user\" (loginName, role) VALUES ('"	+ loginName + "'," + roleID + ")";
		System.out.println(cmd);
		stm.execute(cmd);
	}

	public void delete(Statement stm) throws SQLException {
		String cmd = "Delete from \"user\" where loginName='" + loginName + "'";
		stm.execute(cmd);
	}

	public static boolean login(String loginName, String password, Statement stm) throws SQLException {
		String cmd = "select count(*) from \"user\" where loginName='"	+ loginName + "'";
		ResultSet results = stm.executeQuery(cmd);
		if (results.next())
			return false;
		return results.getInt(0) != 0;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public int getroleID() {
		return roleID;
	}

	public void setroleID(int roleID) {
		this.roleID = roleID;
	}

}

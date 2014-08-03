package Backend;

import java.text.ParseException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Rights_Roles implements IEntity {

	private int roleID;
	private int rightID;	
	
//	CREATE TABLE APP.rights_roles(
//			roleID int,
//			rightID int,
//			PRIMARY KEY(roleID,rightID)
//	);
	
	public Rights_Roles(int roleID, int rightID){
		this.setRightID(rightID);
		this.setRoleID(roleID);
	}
	
	private Statement stm;
	@Override
	public void update(Statement stm) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(Statement stm) throws SQLException, ParseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Statement stm) throws SQLException {
		// TODO Auto-generated method stub
		
	}	
	
	
	public int getRightID() {
		return rightID;
	}

	public void setRightID(int rightID) {
		this.rightID = rightID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	
}

package Backend;

import java.text.ParseException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Rights implements IEntity {

	private int rightID; //ich denke, right muss ein Primarykey sein
	private String rightDescription;  

//	CREATE TABLE APP.rights(
//			rightID int PRIMARY KEY,
//			rightDescription VARCHAR(64) NOT NULL
//	);
	
	public Rights(int rightID, String rightDescription){
		this.rightID = rightID;
		this.rightDescription = rightDescription;
	}
	
	private Statement stm;
	
	//Setzen Rechte von Rolle
	public void update(Statement stm, int roleID) throws SQLException {
		String cmd = "update r.rights set rightDescription='" + rightDescription + "'  where rightID=" + rightID +" "; 
		stm.execute(cmd);
	}
	
	public static ArrayList<Rights> selectRights(Statement stm, int roleID) throws SQLException{
		String cmd ="SELECT r.rightsID, r.rightDescription FROM rights_roles rr LEFT JOIN rights r ON rr.rightID = r.rightID WHERE roleID = " + roleID + "";
		System.out.println(cmd);
		ResultSet results = stm.executeQuery(cmd);
        
        ArrayList<Rights> arr = new ArrayList<Rights>();
        while(results.next())
        {
            int rightID = results.getInt(1);
            String rightDescription = results.getString(2);
            arr.add(new Rights(rightID,rightDescription));
        }
        results.close();
        return arr;
	}

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

	
	
	
	
}

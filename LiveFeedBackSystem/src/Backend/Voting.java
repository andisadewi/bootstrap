package Backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Voting implements IEntity {
	//	CREATE table APP.voting(
//			--ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
//			loginName VARCHAR(40),
//			questionID int,
//			/*da question eventID hat brauchen wir es nicht hier nochmal , koennen direkt darauf zugreifen*/
//			PRIMARY KEY(loginName,questionID),
//			FOREIGN KEY(loginName) references APP."user"(loginName),
//			FOREIGN KEY(questionID) references APP.question(ID)
//		);
//	
	
	
	private int ID;
	private String loginName;
	private int questionID;
/**
 * Constructor
 * @param ID  
 * @param loginName
 * @param questionID
 */
	private Voting(int ID ,String loginName, int questionID){
		this.ID=ID;
		this.loginName=loginName;
		this.questionID=questionID;
	}
	/**
	 * Contructor ohne ID
	 * @param loginName
	 * @param questionID
	 */
	public Voting(String loginName, int questionID) {
		this.loginName=loginName;
		this.questionID=questionID;
	}
/**
 *   gibt eine Liste  aller  Abstimmungen von einer Frage an, also falls sie existieren  
 * @param stm
 * @return
 * @throws SQLException
 */
	public static ArrayList<Voting> select(Statement stm) throws SQLException{
		ResultSet results = stm.executeQuery("select * from voting");
        ArrayList<Voting> arr = new ArrayList<Voting>();
        while(results.next())
        {
        //	int ID= results.getInt(1);
            String loginName = results.getString(2);
            int questionID = results.getInt(3);
            arr.add(new Voting(loginName,questionID));
        }
        results.close();
        return arr;
	}
	
	public static ArrayList<Voting> selectFromID(Statement stm,int questionId) throws SQLException{
		ResultSet results = stm.executeQuery("select * from voting where questionID = "+questionId+"");
        ArrayList<Voting> arr = new ArrayList<Voting>();
        while(results.next())
        {
        //	int ID= results.getInt(1);
            String loginName = results.getString(1);
            int questionID = results.getInt(2);
            arr.add(new Voting(loginName,questionID));

        }
        results.close();
        return arr;
	}
/**
 * update den Loginname und die Frage von einer Abgestimmten Frage
 */
	public void update(Statement stm) throws SQLException {
		String cmd = "update voting set loginName='"+ loginName +"', questionID="+questionID+" where questionID = " + questionID + "AND loginName = '" + loginName + "'"; 
		stm.execute(cmd);
	}

	public void updateLoginname(Statement stm) throws SQLException{
		String cmd  = "update voting set loginName='"+loginName+"',questionID="+questionID+"where questionID = " + questionID + "AND loginName = '" + loginName + "'";
		stm.execute(cmd);
	}
	/**
 * fuegt  einer Frage eine Abstimmung in der Dantenbank hinzu.
 */
	public void insert(Statement stm) throws SQLException {
		String cmd = "INSERT INTO VOTING (loginName, questionID) VALUES ('"+loginName+"',"+questionID+")";
		stm.execute(cmd);
		
	}
 /**
  * loescht eine Abstimmung einer Frage in der Dantenbank.
  */
	public void delete(Statement stm) throws SQLException {
		String cmd = "Delete from VOTING where questionID = " + questionID + "AND loginName = '" + loginName + "'"; 
		stm.execute(cmd);
	}
//-------------------------------------------------SETTER--------------------------------------------------------------------------
	/**
	 * update die ID der Abstimmung an.
	 * @param id
	 */
	public void setId(int id) {
		this.ID = id;
	}
	/**
	 * update den Login name vom Student der die frage gestellt hat.
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
/**
 * update die ID der Frage, die Abgestimmt wurde.
 * @param questionID
 */
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
	
	//--------------------------------------------------GETTER----------------------------------------------------------------------------
/**
 * gibt die ID  der Abstimmung an.
 * @return
 */
	public int getId() {
		return this.ID;
	}

	/**
	 * gibt den Loginname vom Student der die frage gestellt hat.
	 * @return
	 */

	public String getLoginName() {
		return this.loginName;
	}

	/**
	 * gibt die ID der Frage, die Abgestimmt wurde
	 * @return
	 */

	public int getQuestionID() {
		return questionID;
	}
//-----------------------------------------------OPTIONAL--------------------------------------------------------------------------------------
	//falls was noch fehlt dann bitte  zuegig  hier schreiben!!!!!!!!
	
	/**
	 * ueberpruefe ob ein User bereits eine Frage gevoted hat.
	 * @param loginName
	 * @return
	 */
	public static boolean isVoted (Statement stm, String loginName, int questionID){
		boolean voted = false ;
		try {
			ResultSet results = stm.executeQuery("select * from voting where questionID = " + questionID + "AND loginName = '" + loginName + "'");

			int count = 0;

			while (results.next()) {
				++count ;
			}
			
			if (count != 0) {
				voted = true;
			}
			
			results.close();
		} catch (Exception e) {
			voted = false;
			System.out.println("Voting.java: isVoted(): " + e.getMessage()) ;
		}
		
		return voted ;
	}
	
	
	
	
	

}
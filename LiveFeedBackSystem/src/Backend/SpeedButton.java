package Backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SpeedButton implements IEntity {
	
//CREATE table APP.speedButton(
//	ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
//	loginName VARCHAR(40),
//	lectureID int,
//	faster smallint,
//	FOREIGN KEY(loginName) references APP."user"(loginName),
//	FOREIGN KEY(lectureID) references APP.lecture(ID)
//);
//	
//	
	
	private int ID;
	private int lectureID;
	private String loginName;
	boolean faster;
	
	/**
	 * Constructor von SpeedButton mit ID
	 * @param ID ID vom SpeedButton
	 * @param loginName Pseudo bzw Login Name von derjeniegem der den Button gerückt hat
	 * @param lectureID ID der Vernastaltung zu der den Speedbutton gehört.
	 * @param faster teilt mit ob die Veranstaltung schneller oder langsamer laufen soll
	 */
	private SpeedButton(int id, String loginName,int lectureID,boolean faster){
		this.ID = id;
		this.loginName=loginName;
		this.lectureID=lectureID;
     	this.faster=faster;	
	}
	/**
	 * Constructor von SpeedButton ohne ID
	* @param loginName Pseudo bzw Login Name von derjeniegem der den Button gerückt hat
	 * @param lectureID ID der Vernastaltung zu der den Speedbutton gehört.
	 * @param faster teilt mit ob die Veranstaltung schneller oder langsamer laufen soll
	 */
	public SpeedButton(String loginName, int lectureID, boolean faster) {
		this.loginName=loginName;
		this.lectureID=lectureID;
     	this.faster=faster;
	}
/**
 *  Liest  alle SpeedButtons von der Datenbank aus. 
 * @param stm Statement mit dem man die query bzw eine anfrage durchführen kann
 * @return Liste aller SpeedButton
 * @throws SQLException
 */
	public static ArrayList<SpeedButton> select(Statement stm) throws SQLException{
		ResultSet results = stm.executeQuery("select * from speedButton");
        
        ArrayList<SpeedButton> arr = new ArrayList<SpeedButton>();
        while(results.next())
        {
       	  
            int ID = results.getInt(1);
            String loginName= results.getString(2);
            int lectureID = results.getInt(3);
            int faster = results.getInt(4);
            arr.add(new SpeedButton(ID,loginName,lectureID,faster!=0));
        }
        results.close(); 
        return arr;
	}
	/**
	 * Liest die Speedbuttons von der Datenbank von einer gegebenen Veranstaltung aus.
	 * @param stm Statement mit dem man die query bzw eine anfrage durchführen kann
	 * @param lectureId Veranstaltung ID
	 * @return speedbutton von einem Speedbutton.
	 * @throws SQLException
	 */
	public static ArrayList<SpeedButton> selectFromId(Statement stm,int lectureId) throws SQLException{
		ResultSet results = stm.executeQuery("select * from speedButton where lectureID="+lectureId+"");
        
        ArrayList<SpeedButton> arr = new ArrayList<SpeedButton>();
        while(results.next())
        {
       	 
            int ID = results.getInt(1);
            String loginName= results.getString(2);
            int lectureID = results.getInt(3);
            int faster = results.getInt(4);
            arr.add(new SpeedButton(ID,loginName,lectureID,faster!=0));
        }
        results.close();
        return arr;
	}
/**
 * udpdate das Tempo von der Veranstaltung
 */
	public void update(Statement stm) throws SQLException {
		int inc;
		if (faster)
			inc=1;
		else
			inc=0;
		String cmd = "update speedButton set loginName='"+ loginName +"', lectureID="+lectureID+", faster="+inc+" where ID="+ID+" "; 
		stm.execute(cmd);
	}
/**
 * fuegt einen SpeedButton der DatenBank bzw der Tabelle SpeedButton  hinzu.
 */
	public void insert(Statement stm) throws SQLException {
		int inc;
		if (faster)
			inc=1;
		else
			inc=0;
		if(!checkIfSpeedExists(stm)){
			String cmd = "INSERT INTO speedButton ( loginName,lectureID ,faster) VALUES ('" +loginName+ "'," + lectureID + ","+inc+")";
			stm.execute(cmd);
		}
		else{
			throw new SQLException() ;
		}
	}
/**
 * loescht einen Speedbutton von der Datenbank bzw aus der Tabelle SpeedButton.
 */
	public void delete(Statement stm) throws SQLException {
		String cmd = "Delete from speedButton where ID="+ID+" "; 
		stm.execute(cmd);		
	}
 //---------------------------SETTER-----------------------------------------------------------------------------------
/**
 * update oder erstellt die Id eines SpeedButton hinzu.	
 * @param id 
 */
	public void setId(int id) {
		this.ID = id;
	}
	
/**	
 * update oder weis eine Veranstaltung eines SpeedButton zu.
 * @param lectureID
 */
	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}
/**
 * update oder weis   einen  Login name eines Speedbutton zu	
 * @param loginName
 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	/**
	 * update das Tempo eines Veranstaltung
	 */
	public void setIncremented(boolean faster) {
		this.faster= faster;
	}
		
//------------------------------------------------------GETTER---------------------------------------------------------	
/**
 *  gibt die Id eines SpeedButton an.
 * @return ID des Speedbuttons
 */
	public int getId() {
		return ID;
	}

	/**
	 * gibt die Veranstaltung ID eines SpeedButton an.
	 * @return ID der Veranstaltung
	 */

	public int getLectureID() {
		return lectureID;
	}

	/**
	 * gibt den Loginname an. 
	 * @return Loginname derjeniege der den speedbutton gedrückt hat.
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * prueft ob das Tempo geandert wurde. 
	 * @return true wenn die Veranstaltung schneller laufen gehen soll und false wenn sie langsammer gehen soll
	 */

	public boolean isIncremented() {
		return faster;
	}
//--------------------------------------------------------OPTIONAL---------------------------------------------------------------------------------------
	
	
		//falls was noch fehlt dann bitte  zuegig  hier schreiben!!!!!!!!
	
	
	
	/**
	 * button ,der Die Wunsch  von einem hoehen Tempo einer Veranstaltung darstellt
	 */
	public void increaseSpeed(){
		
	}
   /**
    * button ,der die Wunsch  von einem niedriegen Tempo einer Veranstaltung darstellt
    */
	
	public void decreaseSpeed(){
	  
	
	  }
  
	public void Notification(){
	
	}

	public boolean checkIfSpeedExists(Statement stm){
		boolean exist = false ;
		try{
			stm.executeQuery("SELECT * FROM speedButton WHERE loginName = '" + loginName + "' AND " +
					"lectureID = " + lectureID) ;
			ResultSet rs = stm.getResultSet() ;
			int count = 0;
	
			while (rs.next()) {
				++count;
			}
			if (count != 0) {
				exist = true;
			}
			rs.close();
		} catch (SQLException e) {
			exist = false ;
		}
		return exist ;
	}
	
}

		
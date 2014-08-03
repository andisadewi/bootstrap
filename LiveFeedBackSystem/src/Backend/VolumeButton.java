package Backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VolumeButton implements IEntity{

//CREATE table APP.volumeButton(
//		ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
//		loginName VARCHAR(40),
//		lectureID int,
//		louder smallint,
//		FOREIGN KEY(loginName) references APP."user"(loginName),
//		FOREIGN KEY(lectureID) references APP.lecture(ID)
//	);
//	
	 
	
	
	private int ID; 
	private int lectureID;
	private String loginName;
	boolean louder;
	
/**
	 * Constructor mit ID
	 * @param ID id der Volume Button 
	 * @param loginName Pseudo bzw Login Name von derjeniegem der den Button gerdrueckt hat
	 * @param lectureID ID der Vernastaltung zu der den Speedbutton gehoert.
	 * @param louder teilt mit ob die Lautstaerke der  Veranstaltung erhoeht sein soll   oder verringert sein soll 
	 */
	 
	public VolumeButton(int id, String loginName,int lectureID,boolean incremented){
		this.ID = id;
		this.loginName=loginName;
		this.lectureID=lectureID;
     	this.louder=incremented;	
	}
	
/**
	 * Conntructor
	 * @param loginName Pseudo bzw Login Name von derjeniegem der den Button gerdrueckt hat
	 * @param lectureID ID der Vernastaltung zu der den Speedbutton gehoert.
	 * @param louder teilt mit ob die Lautstaerke der  Veranstaltung erhoeht sein soll   oder verringert sein soll .
	 */
	public VolumeButton(String loginName, int lectureID, boolean incremented) {
		this.loginName=loginName;
		this.lectureID=lectureID;
     	this.louder=incremented;	
	}
/**
 * Liest   aller Lautstaerke   von der Datenbank aus. 
 * @param stm Statement mit dem man die query bzw eine anfrage durchfuehren kann.
 * @return Liste aller Volume Button 
 * @throws SQLException
 */
	public static ArrayList<VolumeButton> select(Statement stm) throws SQLException{
		ResultSet results = stm.executeQuery("select * from volumeButton");
        
        ArrayList<VolumeButton> arr = new ArrayList<VolumeButton>();
        while(results.next())
        {
       	 
            int id = results.getInt(1);
            String loginName= results.getString(2);
            int lectureID = results.getInt(3);
            int louder = results.getInt(4);
            arr.add(new VolumeButton(id,loginName,lectureID,louder!=0));
        }
        results.close();
        return arr;
	}
	/**
	 * Liest die Lautstaerke von der Datenbank von einer gegebenen Veranstaltung aus.
	 * @param stm stm Statement mit dem man die query bzw eine anfrage durchfuehren kann.
	 * @param lectureId
	 * @return  Volume Button von einer spezifischen Veranstaltung.
	 * @throws SQLException
	 */
	public static ArrayList<VolumeButton> selectFromId(Statement stm,int lectureId) throws SQLException{
		ResultSet results = stm.executeQuery("select * from volumeButton where lectureID="+lectureId+"");
        
        ArrayList<VolumeButton> arr = new ArrayList<VolumeButton>();
        while(results.next())
        {
       	 
            int id = results.getInt(1);
            String loginName= results.getString(2);
            int lectureID = results.getInt(3);
            int louder = results.getInt(4);
            arr.add(new VolumeButton(id,loginName,lectureID,louder!=0));
        }
        results.close();
        return arr;
	}
/**
 * update die Lautstaerke -Zaehler, login name und die Veranstaltung Id von einer Veranstaltung 
 */
	public void update(Statement stm) throws SQLException {
		int inc;
		if (louder)
			inc=1;
		else
			inc=0;
		String cmd = "update volumeButton set loginName='"+ loginName +"', lectureID="+lectureID+", louder="+inc+" where ID="+ID+" "; 
		stm.execute(cmd);
	}
/**
 * fuegt eine Lautstaerke Zahler  in der datenbank hinzu.
 */
	public void insert(Statement stm) throws SQLException {
		int inc;
		if (louder)
			inc=1;
		else
			inc=0;
		if(!checkIfVolumeExists(stm)){
			String cmd = "INSERT INTO volumeButton ( loginName,lectureID ,louder) VALUES ('" +loginName+ "'," + lectureID + ","+inc+")"; 
			stm.execute(cmd);
		}
		else{
			throw new SQLException() ;
		}
	}
	
/**
 * loescht ein Lautstaerke Zaehler aus  der Datenbank. 
 */
	public void delete(Statement stm) throws SQLException {
		
		String cmd = "Delete from volumeButton where id="+ID+" "; 
		stm.execute(cmd);		
	}
//----------------------------------------------------------SETTER---------------------------------------------------------------------
	/**
	 * aendert die Id von der Lautstaerke -Zaehler.
	 * @param id
	 */
	public void setId(int id) {
		this.ID = id;
	}
	/**
	 *  update die ID von der Veranstaltung, in der der Lautstaerke Zaehler geaendert wurde.
	 * @param lectureID
	 */
	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}

	/**
	 * update den Loginname 
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * update den Lautstaerke -Zahler von einer Veranstaltung an.
	 * @param incremented
	 */
	public void setIncremented(boolean incremented) {
		this.louder = incremented;
	}
	
	//----------------------------------------------------------GETTER-----------------------------------------------------------------------	
	/**
	 * gibt die Id von der Lautstaerke -Zaehler.
	 * @return ID  as Integer.
	 */
	public int getId() {
		return ID;
	}

	
/**
 * gibt die ID von der Veranstaltung, in der den Lautstaerke Zaehler geaendert wurde.
 * @return ID von der Veranstaltung .
 */
	public int getLectureID() {
		return lectureID;
	}

	
/**
 * gibt den Loginnme an
 * @return Loginname as String.
 */
	public String getLoginName() {
		return loginName;
	}

	
/**
 * 
 * @return
 */
	public boolean isIncremented() {
		return louder;
	}
//-----------------------------------------------------------------OPTIONAL--------------------------------------------------------------
	
	/**
	 * button ,den den LautstaerkeZaehler erh??	 */
	public void increaseVolume(){
		
	}
   /**
    * Button, der die LautstaerkeZaehler verringert.
    */
	
	public void decreaseVolume(){
	 
	  }
  
	public void Notification(){

	}	

	public boolean checkIfVolumeExists(Statement stm){
		boolean exist = false ;
		try{
			stm.executeQuery("SELECT * FROM volumeButton WHERE loginName = '" + loginName + "' AND " +
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

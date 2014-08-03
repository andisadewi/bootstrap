package Backend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Lecture implements IEntity{
	
	/**
	 * Legt eine Veranstaltung an.
	 */
	
	private int id;
	private Date date;
	private String professorID;
	private String room;
	private String name;
	private String password;
	private int speedCounter;
	private int volumeCounter;
	private int speedLimit;
	private int volumeLimit;
	private int votingLimit;

//	CREATE table APP.lecture(
//			ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
//			"date" DATE,
//			professorID VARCHAR(40),/*entspricht Dozent loginName*/
//			room VARCHAR(10),
//			name VARCHAR(100),
//			password VARCHAR(20),
//			speedCounter int,
//			volumeCounter int,
//			speedLimit int NOT NULL,	/* threshold for the speed button, default value is  100 */
//			volumeLimit int NOT NULL,	/* threshold for the speed button, default value is  100 */
//			votingLimit int NOT NULL	/* threshold for the speed button, default value is  100 */
//		);


	/**
	 * Constructor mit ID
	 * @param id := id von der Veranstaltung
	 * @param date := datum von der Veranstaltung
	 * @param professorID ID der Dozent  von der Veranstaltung
	 * @param room raum wo der Veranstaltung stattfindet
	 * @param name Name der Veransataltung
	 * 
	 * @param password Password der Veranstaltung
	 * @param speedCounter Speed Counter bzw Button der Verannstaltung 
	 * @param volumeCounter Volume Counter bzw Button der Veranstaltung
	 * @param speedLimit  Speed Grenze ,die den Speed-Counter nicht ueberschreiten darf
	 * @param volumeLimit Volume gremze ,die  den Volume-Counter nicht �berschreiten darf
	 * @param votingLimit Votimg grenze, die den Voting -Counter nict ueberschreiten darf
	 */
	

	private Lecture(int id, Date date, String professorID,String room,String name,String password,int speedCounter,int volumeCounter,int speedLimit,int volumeLimit, int votingLimit){
		this(date,professorID,room,name,password,speedCounter,volumeCounter,speedLimit,volumeLimit,votingLimit);
		this.id = id;
	}
	
	public Lecture(Date date, String ProfessorID,String room,String name,String password,int speedCounter,int volumeCounter,int speedLimit,int volumeLimit, int votingLimit){
		this.date = date;
		this.professorID = ProfessorID;
		this.room=room;
		this.name=name;
		this.password = password;
		this.speedCounter=speedCounter;
		this.volumeCounter=volumeCounter;
		this.setSpeedLimit(speedLimit);
		this.setVolumeLimit(volumeLimit);
		this.setVotingLimit(votingLimit);
	}


	/**
	 * Constructor ohne
	 * @param id := id von der Veranstaltung
	 * @param date := datum von der Veranstaltung
	 * @param professorID ID der Dozent  von der Veranstaltung
	 * @param room raum wo der Veranstaltung stattfindet
	 * @param name Name der Veransataltung
	 * 
	 * @param password Password der Veranstaltung
	 * @param speedCounter Speed Counter bzw Button der Verannstaltung 
	 * @param volumeCounter Volume Counter bzw Button der Veranstaltung
	 * @param speedLimit  Speed Grenze ,die den Speed-Counter nicht ueberschreiten darf
	 * @param volumeLimit Volume gremze ,die  den Volume-Counter nicht �berschreiten darf
	 * @param votingLimit Votimg grenze, die den Voting -Counter nict ueberschreiten darf
	 */
	
	private Statement stm;
	
	/**
	 *  gibt eine Liste aller Veranstaltung mit ihrer ID von der Datenbank.
	 * @param stm 
	 * @return Liste alle Veranstaltung.
	 * @throws SQLException
	 */
	public static ArrayList<Lecture> select(Statement stm) throws SQLException{
		ResultSet results = stm.executeQuery("select * from lecture");
	    ArrayList<Lecture> arr = new ArrayList<Lecture>();
	    while(results.next())
	    {
	    	
	        int id = results.getInt(1);
	        Date date = results.getDate(2);
	        String professorID = results.getString(3);
	        String room= results.getString(4);
	        String name= results.getString(5);
	        String password= results.getString(6);
	        int speedCounter=results.getInt(7);
	        int volumeCounter=results.getInt(8);
	        int speedLimit=results.getInt(9);
	        int volumeLimit=results.getInt(10);
	        int votingLimit=results.getInt(11);
	        arr.add(new Lecture(id,date,professorID,room,name,password,speedCounter,volumeCounter,speedLimit,volumeLimit,votingLimit));
	    }
	    results.close();
	    return arr;
	}
	
	/**
	 *  gibt eine Liste aller Veranstaltung  von der Datenbank.
	 * @param stm
	 * @param lectureID
	 * @return Liste alle Veranstaltung.
	 * @throws SQLException
	 */
	public static Lecture selectLecture(Statement stm, int lectureID) throws SQLException{
		ResultSet results = stm.executeQuery("select * from lecture where id="+lectureID+"");
        
        while(results.next()) 
        {
	        Date date = results.getDate(2);
	        String professorID = results.getString(3);
	        String room= results.getString(4);
	        String name= results.getString(5);
	        String password= results.getString(6);
	        int speedCounter=results.getInt(7);
	        int volumeCounter=results.getInt(8);
	        int speedLimit=results.getInt(9);
	        int volumeLimit=results.getInt(10);
	        int votingLimit=results.getInt(11);
	        
            Lecture lec =new Lecture(lectureID, date, professorID, room, name, password, speedCounter,volumeCounter,speedLimit,volumeLimit,votingLimit);
            return lec;
        }
        
        results.close();
        return null;
	}
	
	
	/**
	 * update eine bzw alle Attribute von Veranstaltung. 
	 */
	public void update(Statement stm) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cmd = "update lecture set \"date\"='" + sdf.format(date) + "',professorID='"+professorID+"',room='"+room+"',name='"+name+"',password='"+password+"',speedCounter="+speedCounter+",volumeCounter="+volumeCounter+", speedLimit="+speedLimit+",volumeLimit="+volumeLimit+",votingLimit="+votingLimit+" where id="+id+" ";
		stm.execute(cmd);
	}
	
	/**
	 * f�gt eine Veranstaltung in der Dantenbank hinzu.
	 */
	public void insert(Statement stm) throws SQLException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cmd = "INSERT INTO lecture (\"date\",professorID,room,name,password,speedCounter,volumeCounter,speedLimit,volumeLimit,votingLimit) VALUES ('"+sdf.format(date)+"','"+professorID+"','"+room+"','"+name+"','"+password+"',"+speedCounter+","+volumeCounter+","+speedLimit+","+volumeLimit+","+votingLimit+")";
//		System.out.println(cmd);
		stm.execute(cmd);
			
	}
	
	/**
	 * loescht eine Veranstaltung aus  der Datenbank.
	 */
	public void delete(Statement stm) throws SQLException {
		String cmd = "Delete from lecture where id="+id+" "; 
		stm.execute(cmd);	
	}

///////////////////////////getter and setter Methods///////////////////////////
	
	/**
	 * gib den Raum von der Veranstaltung an.
	 * @return
	 */
	public String getRoom() {
		return room;
	}
	
	/**
	 * update den Raum von einer Veranstaltung mit der gegebenen Attribute.
	 * @param room
	 */
	public void setRoom(String room) {
		this.room = room;
	}
	/**
	 * gib die ID von dem Dozent der  fuer die Veranstaltung bzw Vorlesung haelt. 
	 * @return die ID des Dozent
	 */
	public String getProfessorID() {
		return professorID;
	}
	
	/**
	 * update die ID von dem Dozent der  fuer die Veranstaltung bzw Vorlesung haelt. 
	 * @param professorID
	 */
	public void setProfessorID(String professorID) {
		this.professorID = professorID;
	}
	
	/**
	 * gib die das Datum von der Veranstaltung an.
	 * @return das Datum
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * update das Datum von der Veranstaltung.
	 * @param date 
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * gib die ID von einer Veranstaltug an.
	 * @return id der Veranstaltung.
	 */
	public int getId() {
		return id;
	}
	/**
	 * update die ID von einer Veranstaltug mit dem gegebenen Parameter.
	 * @param id as integer.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * gib das Password von der Veranstaltung an.
	 * @return das Paasword der Veranstaltung.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * update das Password von der Veranstaltung mit den gegebenen Parameter.
	 * @param password as String.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * gib den Name von einer Veranstaltun an.
	 * @return Name as String.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * updtate den Name von einer Veranstaltung mit dem gegebenen Parameter.
	 * @param name as String
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * gib den aktuell Wert den Speedcounter an. 
	 * @return speedCounter as Integer
	 */
	public int getSpeedCounter() {
		return speedCounter;
	}
	/**
	 * update  den aktuel Wert des Speedcounter an mit dem gegebenen Parameter.
	 * @param speedCounter
	 */
	public void setSpeedCounter(int speedCounter) {
		this.speedCounter = speedCounter;
	}
	/**
	 * gibt den aktuel  Wert des VolumeCounter. 
	 * @return
	 */
	public int getVolumeCounter() {
		return volumeCounter;
	}
	
	/**
	 * update den aktuel  Wert des VolumeCounter mit dem gegebenen Parameter. 
	 * @param volumeCounter
	 */
	public void setVolumeCounter(int volumeCounter) {
		this.volumeCounter = volumeCounter;
	}
	/**
	 * gib den VolumeLimit von einer Veranstaltung  an.
	 * @return
	 */
	public int getVolumeLimit() {
		return volumeLimit;
	}
	
	/**
	 * update den VolumeLimit von einer Veranstaltung mit dem gegebenen Parameter.
	 * @param volumeLimit
	 */
	public void setVolumeLimit(int volumeLimit) {
		this.volumeLimit = volumeLimit;
	}
	
	public int getSpeedLimit() {
		return speedLimit;
	}
	/**
	 *  gib den SpeedLimit von einer Veranstaltung an.
	 * @return
	 */
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}
	/**
	 * gib den VotingLimit von einer Veranstaltung an
	 * @return VotingLimit as interger
	 */
	public int getVotingLimit() {
		return votingLimit;
	}
	/**
	 * update den VotingLimit von einer Veranstaltung mit dem gegebenen Parameter.
	 * @param votingLimit
	 */
	public void setVotingLimit(int votingLimit) {
		this.votingLimit = votingLimit;
	}

	public void increasedVolume(){
		if(this.volumeCounter<this.volumeLimit)
		 this.volumeCounter= this.volumeCounter++;
	}
	public void increasedSpeed(){
		if(this.speedCounter<= this.speedLimit)
			this.speedCounter = this.speedCounter++;
		
	}


}

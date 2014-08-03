package Backend;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Event {
	private String room;
	private int professorID;
	private Date datum;
	private int id;

	public Event(Statement stm, int id, Date datum, int ProfessorID,String room ){
	this.id = id;
	this.datum = datum;
	this.professorID = ProfessorID;
	this.stm = stm;
	this.room=room;
	
}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Statement getStm() {
		return stm;
	}

	public void setStm(Statement stm) {
		this.stm = stm;
	}
private Statement stm;

public static ArrayList<Event> select(Statement stm) throws SQLException{
	ResultSet results = stm.executeQuery("select * from event");
    
    ArrayList<Event> arr = new ArrayList<Event>();
    while(results.next())
    {
   	 
        int id = results.getInt(1);
        Date datum = results.getDate(2);
        int professorID = results.getInt(3);
        String room= results.getString(4);
        arr.add(new Event(stm, id,datum, professorID,room));
    }
    results.close();
    return arr;
}

public String getRoom() {
	return room;
}

public void setRoom(String room) {
	this.room = room;
}

public int getProfessorID() {
	return professorID;
}

public void setProfessorID(int professorID) {
	this.professorID = professorID;
}

public Date getDatum() {
	return datum;
}

public void setDatum(Date datum) {
	this.datum = datum;
}



}

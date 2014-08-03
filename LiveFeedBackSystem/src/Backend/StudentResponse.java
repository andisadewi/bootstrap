package Backend;

import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class StudentResponse implements IEntity{
	
//	CREATE table APP.studentResponse(
//			loginName VARCHAR(40),
//			surveyID int,
//			responseNr int NOT NUL
			
	private String loginName;
	private int surveyID;
	private int responseNr;
	
/**
 * Constructor 
 * @param ID
 * @param responseID
 * @param loginName 
 */
	public StudentResponse(String loginName, int surveyID, int responseNr){
		this.surveyID = surveyID ;
		this.responseNr = responseNr ;
		this.loginName = loginName;
	}


/**
 * gib die Liste aller Response von der Datenbank
 * @param stm
 * @return
 * @throws SQLException
 */

	public static ArrayList<StudentResponse> select(Statement stm) throws SQLException{
		ResultSet results = stm.executeQuery("select * from studentResponse");
        
        ArrayList<StudentResponse> arr = new ArrayList<StudentResponse>();
        while(results.next())
        {
        	String loginName= results.getString(1);
            int ID = results.getInt(2);
            int responseNr = results.getInt(3);
    
            arr.add(new StudentResponse(loginName,ID,responseNr));
        }
        results.close();
        return arr;
	}
	
	/**
	 * update eine bzw alle Attribut von einer Student response
	 */
	public void update(Statement stm) throws SQLException {
	
		String cmd = "update studentResponse set responseNr="+ responseNr +
				"where surveyID="+surveyID+" and loginName = '" + loginName + "'"; 
		stm.execute(cmd);
	}
	
/**
 * fuegt eine Student- response in der Datenbank. 
 * 
 */
	public void insert(Statement stm) throws SQLException {

		String cmd = "INSERT INTO studentResponse (loginName,surveyID,responseNr) VALUES ('" + loginName + "'," + surveyID + ", " + responseNr + ")"; 
		stm.execute(cmd);		
	}
	
/**
 * loescht eine Student-response aus der Datenbank.
 */
	public void delete(Statement stm) throws SQLException {
		String cmd = "Delete from studentResponse where surveyID="+surveyID+" and loginName = '" + loginName + "'"; 
		stm.execute(cmd);
	}


//------------------------------------------------------------GETTER-----------------------------------------------------------------------
	
	/**
	 * gib die ID von der Stundent response an.
	 * @return
	 */
	public int getSurveyId() {
		return surveyID;
	}
	/**
	 * gib die ID der Response, die der Student gewaehlt hat.
	 * @return
	 */
	public int responseNr() {
		return responseNr;
	}

/**
 * gib die den Loginname von der Student, den die Response geweahlt hat.
 * @return
 */
	public String getLoginName() {
		return loginName;
	}
//----------------------------------------------------------SETTER--------------------------------------------------------------------------
/**
 * update die ID von der Stundent response
 * @param id
 */
	public void setId(int id) {
		this.surveyID = id;
	}

/**
 * update die ID der Response, die der Student gewaehlt hat.
 * @param responseID
 */

  public void setResponseNr(int responseNr) {
		this.responseNr = responseNr;
	}

/**
 *  update die den Loginname von der Student, den die Response gewaehlt hat.
 * @param loginName
 */
  public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
//-----------------------------------------------OPTIONAL--------------------------------------------------------------------------------------
	//falls was noch fehlt dann bitte  zuegig  hier schreiben!!!!!!!! 
  
  public StudentResponse selectStudentResponse(Statement stm){
		StudentResponse studRes = null ;
		try {
			stm.executeQuery("SELECT * FROM studentResponse WHERE surveyID="+surveyID+" and loginName = '" + loginName + "'"); 
			ResultSet results = stm.getResultSet() ;

			String loginName= results.getString(1);
            int ID = results.getInt(2);
            int responseNr = results.getInt(3);
    
            studRes = new StudentResponse(loginName,ID,responseNr);
			
			results.close() ;
			stm.close() ;
			
		} catch (Exception e) {
			System.out.println("StudentResponse.java: selectStudentResponse(): " + e.getMessage()) ;
		}
			
		return studRes ;
	}
	
  /**
	 * return how many students have answered which choice in a survey
	 * @param stm
	 * @param choiceNr choice number
	 * @return counter
	 */
	public int surveyChoiceCounter(Statement stm){
		int counter = 0 ;
		try{
			ResultSet results = stm.executeQuery("select count(DISTINCT loginName) from studentResponse WHERE " +
					"surveyID = " + surveyID + " AND responseNr = " + responseNr);
			while(results.next()){
				counter = results.getInt(1) ;
			}
			
		} catch (Exception e) {
			System.out.println("StudentResponse.java: surveyChoiceCounter(): " + e.getMessage()) ;
		}
		
		return counter ;
	}
	
	/**
	 * gibt die anzahl der Studenten zurück, die eine Umfrage geantwortet haben.
	 * @param stm
	 * @return
	 */
	public int surveyCounter(Statement stm, int surveyID){
		int counter = 0 ;
		try{
			ResultSet results = stm.executeQuery("select count(DISTINCT loginName) from studentResponse WHERE " +
					"surveyID = " + surveyID);
			while(results.next()){
				counter = results.getInt(1) ;
			}
			
		} catch (Exception e) {
			System.out.println("StudentResponse.java: surveyCounter(): " + e.getMessage()) ;
		}
		
		return counter ;
	}
	
	/**
	 * ueberpruefe ob ein User bereits eine Survey geantwortet hat.
	 * @param loginName
	 * @return
	 */
	public static boolean isAnswered (Statement stm, String loginName, int surveyID){
		boolean answered = false ;
		try {
			ResultSet results = stm.executeQuery("select * from studentResponse where surveyID = " + surveyID + "AND loginName = '" + loginName + "'");

			int count = 0;

			while (results.next()) {
				++count ;
			}
			
			if (count != 0) {
				answered = true;
			}
			
			results.close();
		} catch (Exception e) {
			answered = false;
			System.out.println("studentResponse.java: isAnswered(): " + e.getMessage()) ;
		}
		
		return answered ;
	}
	
	/**
	 * gibt eine Liste der Studenten zurück, die eine Umfrage geantwortet haben
	 */
	
	public ArrayList<String> listStudent(Statement stm, int surveyID){
		ArrayList<String> list = new ArrayList<String>() ;
		try{
			ResultSet results = stm.executeQuery("select DISTINCT loginName from studentResponse WHERE " +
					"surveyID = " + surveyID);
			while(results.next()){
				String loginName= results.getString(1);
	    
	            list.add(loginName) ;
			}
			results.close();
			
		} catch (Exception e) {
			System.out.println("StudentResponse.java: listStudent(): " + e.getMessage()) ;
		}
		
		return list ;
	}

}

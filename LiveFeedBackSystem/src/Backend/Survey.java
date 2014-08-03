package Backend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

public class Survey implements IEntity{
	private int id;
	private String text;
	private boolean visible;	
	private Date time;
	private int lectureID;
	
	//============================================================================================
	// CONSTRUCTORS

    public Survey(String text, boolean visible, Date time, int lectureID){
    	this.text = text;
    	this.visible = visible;
    	this.time = time;
    	this.lectureID = lectureID;
	}
    
    public Survey(int id, String text, boolean visible, Date time, int lectureID){
    	this.id = id;
    	this.text = text;
    	this.visible = visible;
    	this.time = time;
    	this.lectureID = lectureID;
	}
        
    //============================================================================================
    // METHODES
    
    /**
     * Select all surveys in database
     * @param stm Statement
     * @return List of all surveys
     */
	public ArrayList<Survey> selectAllSurveys(Statement stm){
		ArrayList<Survey> arr = new ArrayList<Survey>();
		try {
			ResultSet results = stm.executeQuery("select * from survey");
	     
	        while(results.next())
	        {
	        	int id = results.getInt(1);
	            String text = results.getString(2);
	            boolean v = results.getBoolean(3);
	            Date time = results.getDate(4);
	            int lectureID = results.getInt(5);
	            
	            arr.add(new Survey(id,text,v,time,lectureID));
	        }
	        results.close();
	        stm.close();
        
		} catch (Exception e) {
			System.out.println("Survey.java: selectAllSurveys(): " + e.getMessage()) ;
		}
        
        return arr;
	}
	
	/**
	 * Select a survey
	 * @param stm Statement
	 * @return the selected survey
	 */
	public Survey selectSurvey(Statement stm){
		Survey survey = null ;
		try {
			stm.executeQuery("SELECT * FROM survey WHERE ID = " + id) ;
			ResultSet rs = stm.getResultSet() ;

			while (rs.next()){
				int id = rs.getInt(1);
	            String text = rs.getString(2);
	            boolean v = rs.getBoolean(3);
	            Date time = rs.getDate(4);
	            int lectureID = rs.getInt(5);
	            
				survey = new Survey(id,text,v,time,lectureID) ;
			}
			rs.close() ;
			stm.close() ;
			
		} catch (Exception e) {
			System.out.println("Survey.java: selectSurvey(): " + e.getMessage()) ;
		}
			
		return survey ;
	}
	

	/**
	 * Return a list of surveys in a particular lecture
	 * @param stm
	 * @param lectureId
	 * @return a list of surveys
	 */
	public static ArrayList<Survey> selectSurveysFromLecture(Statement stm, int lectureId){
		ArrayList<Survey> arr = new ArrayList<Survey>();
		
		try {
			ResultSet results = stm.executeQuery("select * from survey where lectureID = " + lectureId);
	        
	        while(results.next())
	        {
	        	int id = results.getInt(1);
	            String text = results.getString(2);
	            boolean v = results.getBoolean(3);
	            Date time = results.getDate(4);
	            int lectureID = results.getInt(5);
	            
	            arr.add(new Survey(id,text,v,time,lectureID)) ;
	        }
	        results.close();
	        stm.close();

		} catch (Exception e) {
			System.out.println("Survey.java: selectSurveysFromLecture(): " + e.getMessage()) ;
		}
		
		return arr ;
	};

	/**
	 * Update a survey in database
	 * @param stm Statement
	 * @return true if the update is successful
	 */
	public boolean updateSurvey(Statement stm){
		boolean success = false ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			

			String cmd = "update survey set text='"+ text +"', visible="+visible+",\"time\"='"+sdf.format(time)+"',lectureID="+lectureID+" where id="+id ; 

			stm.executeUpdate(cmd);
			success = true;
			
		} catch (Exception e) {
			System.out.println("Survey.java: updateSurvey(): " + e.getMessage()) ;
		}
		return success;
	}
	
	public void update(Statement stm) throws SQLException{

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			

			String cmd = "update survey set text='"+ text +"', visible="+visible+",\"time\"='"+sdf.format(time)+"',lectureID="+lectureID+" where id="+id ; 

			stm.executeUpdate(cmd);
			
	}

	/**
	 * Insert a new survey to database
	 * @param stm Statement
	 * @return true if the insertion is successful
	 */
	public void insert(Statement stm) throws SQLException{

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String cmd = "INSERT INTO survey (text,visible,\"time\",lectureID) VALUES ('"+text+"',"+visible+",'"+sdf.format(time)+"',"+lectureID+")"; 
			stm.executeUpdate(cmd);
	
	}

	/**
	 * Delete an existing survey from database
	 * @param stm Statement
	 * @return true if it's successful
	 */
	public boolean deleteSurvey(Statement stm) {
		boolean success = false ;
		try {
			String cmd = "Delete from survey where id="+id ; 
			stm.executeUpdate(cmd);
			success = true;
		
		} catch (Exception e) {
			System.out.println("Survey.java: deleteSurvey(): " + e.getMessage()) ;
		}
		return success;
	}
	
	public void delete(Statement stm) throws SQLException {
			String cmd = "Delete from survey where id="+id ; 
			stm.executeUpdate(cmd);
	}
	

	public static boolean deleteID(Statement stm, int id3) {
		boolean success = false ;
		try {
			String cmd = "Delete from survey where id="+id3 ; 
			stm.executeUpdate(cmd);
			success = true;
		
		} catch (Exception e) {
			System.out.println("Survey.java: deleteSurvey(): " + e.getMessage()) ;
		}
		return success;
	}

	/**
	 * Read the timer of a survey
	 */
	public long readTimer(Statement stm){
		Date timeStart = null ;
		long timeEnd = System.currentTimeMillis() ;
		long timer = 0 ;
		
		try {
			stm.executeQuery("SELECT time FROM survey WHERE ID = " + id) ;
			ResultSet rs = stm.getResultSet() ;
	
			while (rs.next()){
				timeStart = rs.getDate("time");
			}
			
			timer = timeEnd - timeStart.getTime() ;	// result in milliseconds
			
		} catch (Exception e) {
			System.out.println("Survey.java: readTimer(): " + e.getMessage()) ;
		}
		
		return timer ;
	}
	
	/**
	 * Check if a survey exists in database
	 * @param stm Statement
	 * @return true if the survey exists
	 */
	public boolean checkIfSurveyExists(Statement stm){
		boolean exist = false ;
		try {
			stm.executeQuery("SELECT * FROM survey WHERE ID = " + id) ;
			ResultSet rs = stm.getResultSet() ;
			int count = 0;

			while (rs.next()) {
				++count;
			}
			if (count != 0) {
				exist = true;
			}
			rs.close();
			stm.close();
			
		}catch (Exception e) {
			exist = false ;
			System.out.println("Survey.java: checkIfSurveyExists(): " + e.getMessage()) ;
		}
		
		return exist ;
	}
	public static Survey select(Statement stm, int id3){
		Survey survey = null ;
		try {
			stm.executeQuery("SELECT * FROM survey WHERE ID = " + id3) ;
			ResultSet rs = stm.getResultSet() ;

			while (rs.next()){
				int id = rs.getInt(1);
	            String text = rs.getString(2);
	            boolean v = rs.getBoolean(3);
	            Date time = rs.getDate(4);
	            int lectureID = rs.getInt(5);
	            
				survey = new Survey(id,text,v,time,lectureID) ;
			}
			rs.close() ;
			stm.close() ;
			
		} catch (Exception e) {
			System.out.println("Survey.java: select(): " + e.getMessage()) ;
		}
			
		return survey ;
	}
	
	//============================================================================================
	// GETTER/SETTER
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Date getDate() {
		return time;
	}

	public void setDate(Date time) {
		this.time = time;
	}

	public int getLectureID() {
		return lectureID;
	}

	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}
	
	
}


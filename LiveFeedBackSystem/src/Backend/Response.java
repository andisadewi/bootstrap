package Backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Response implements IEntity {
	
//	CREATE table APP.response(
//			surveyID int,
//			text varchar(200) NOT NULL,
//			responseNr int CHECK (responseNr > 0 AND responseNr < 5), /* choice number, max is 4 */
//			isRightRes smallint NOT NULL, /* true if it's the right choice, else false */
//			PRIMARY KEY(surveyID, responseNr),
//			FOREIGN KEY(surveyID) references APP.survey(ID) ON DELETE CASCADE
//		); 
	 
	private int surveyID;
	private String text;
	private int responseNr; 
	private boolean isRightRes;
/**
 * Constructor
 * @param text
 * @param responseNr
 * @param surveyID
 * @param isRightRes
 */
	public Response(int surveyID,String text,int responseNr,boolean isRightRes){
		//this(surveyID,text,responseNr,isRightRes);
		this.surveyID=surveyID;
		this.text= text;
		this.responseNr=responseNr;
		this.isRightRes=isRightRes;
	}

/**
 * gibt eine Liste aller Response von der Datenbank.
 * @param stm
 * @return
 * @throws SQLException
 */
	
	public ArrayList<Response> select(Statement stm) throws SQLException{
		ResultSet results = stm.executeQuery("select * from response");
        
        ArrayList<Response> arr = new ArrayList<Response>();
        while(results.next())
        {
       	 
            int surveyID = results.getInt(1);
            String text = results.getString(2);
            int responseNr = results.getInt(3);
            int isRight = results.getInt(4);
            arr.add(new Response(surveyID,text,responseNr,isRight!=0));
        }
        results.close();
        return arr;
	}
	
	public static ArrayList<Response> selectFromSurvey(Statement stm,int surveyID) throws SQLException{
		ResultSet results = stm.executeQuery("select * from response where surveyID="+surveyID+"");
        
		ArrayList<Response> arr = new ArrayList<Response>();
        while(results.next())
        {
       	 
            int id = results.getInt(1);
            String text = results.getString(2);
            int responseNr = results.getInt(3);
            int isRight = results.getInt(4);
            arr.add(new Response(id,text,responseNr,isRight!=0));
        }
        results.close();
        return arr;
	}
	
	
	public static ArrayList<String> selectResponsesText (Statement stm, int surveyID) throws SQLException{
		ResultSet results = stm.executeQuery("select responseNr, text from response where surveyID = "+surveyID+" order by responseNr ASC");
        
        ArrayList<String> arr = new ArrayList<String>();
        while(results.next())
        {
            String text = results.getString(2);
            arr.add(text);
        }
        results.close();
        return arr;
	}
	
	
	/**
	 * update eine bzw alle Attribute von Response.
	 */
	public void update(Statement stm) throws SQLException {

		int isRight;
		if(isRightRes)
			isRight=1;
		else
			isRight=0;
		String cmd = "update response set text='"+text+"' ,isRightRes="+isRight+" " +
				"where surveyID="+surveyID+" and responseNr = " + responseNr; 
		stm.execute(cmd);
	}
	
	/**
	 * f�gt eine Response in der Datenbank hinzu.
	 */
	public void insert(Statement stm) throws SQLException {
		
		int isRight;
		if(isRightRes)
			isRight=1;
		else
			isRight=0;
		String cmd = "INSERT INTO response (surveyID,text,responseNr,isRightRes) VALUES ("+surveyID+", '" + text+ "' ," +responseNr+ ", "+isRight+")";  
		stm.execute(cmd);
	}
	
	/**
	 * l�scht eine Response einer Survey.
	 */
	public void delete(Statement stm) throws SQLException {
		String cmd = "Delete from response where surveyID="+surveyID+" and responseNr = " + responseNr;
		stm.execute(cmd);
	}
	
	//-----------------------------------------------------------GETTER----------------------------------------------------------

/**
 * gib die ID von der Survey bzw Umfrage  an , zu der die Response gehoert  	
 * @return
 */
	public int getSurveyID() {
		return surveyID;
	}
/**
 * gibt den Text von der Response an
 * @return
 */
	public String getText(){
		return text;
	}
	/**
	 * gibt die an, an welchen Paltz die Response gespeichert wurde.
	 * @return
	 */
	public int getResponseNr(){
		return responseNr;
	}
	/**
	 * teilt  ob die Response richtig ist oder nicht mit.
	 * @return
	 */
	public Boolean getIsRightRes() {
		return isRightRes;
	}

	
	//----------------------------------------------------------SETTER------------------------------------------------------------------

	
	/**
 * update die ID von der Survey bzw Umfrage  an , zu der die Response gehoert.
 * @param surveyID
 */
	public void setSurveyID(int surveyID) {
		this.surveyID = surveyID;
	}


/**
 * legt fest ob die response richtig oder nicht.
 * @param isRightRes
 */
	public void setIsRightRes(Boolean isRightRes) {
		this.isRightRes = isRightRes;
	}
/**
 * update die response bzw den Text .
 * @param text
 */
public void setText(String text){
	this.text= text;
}
/**
 * update die ResponserNr mit der gegebenen parameter
 * @param responseNr
 */
public void setResponsenNr(int responseNr){
	 this.responseNr= responseNr;
 }
	
//-----------------------------------------------OPTIONAL--------------------------------------------------------------------------------------
	//falls was noch fehlt dann bitte  zuegig  hier schreiben!!!!!!!!

	
}




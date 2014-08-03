package Backend;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Question implements IEntity{
	
	/**
	 * legt eine Frage von einer Veranstaltung an.
	 */
	
	private int id;
	private int lectureID;
	private String text;
	private boolean answered;
	
//	CREATE table APP.question(
//			ID int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
//			lectureID int,
//			Text VARCHAR(160) not null,
//			/*zaehler weggelassen*/
//			answered smallint not null,	/* 0 if not yet checked(answered), 1 if already */
//		  	FOREIGN KEY(lectureID) references APP.lecture(ID)
//		);
	
	
	public Question(int id,int lectureID, String text,boolean answered ){
		this(lectureID,text,answered);
		this.id = id;
		
	}
	public Question(int lectureID, String text, boolean answered){
		this.lectureID=lectureID;
		this.text = text;
		this.answered = answered;
	}
	private Statement stm;
	
	public static ArrayList<Question> select(Statement stm) throws SQLException{
		ResultSet results = stm.executeQuery("select * from question");
        
        ArrayList<Question> arr = new ArrayList<Question>();
        while(results.next())
        {
       	 
            int id = results.getInt(1);
            int lectureID = results.getInt(2);
            String text = results.getString(3);
            int answered = results.getInt(4);
            arr.add(new Question(id,lectureID, text,answered!=0));
        }
        results.close();
        return arr;
	}
	
	public static ArrayList<Question> selectAll(Statement stm,int lectureId) throws SQLException{
		String cmd ="select * from question where lectureID="+lectureId+"";
		System.out.println(cmd);
		ResultSet results = stm.executeQuery(cmd);
        
        ArrayList<Question> arr = new ArrayList<Question>();
        while(results.next())
        {
       	 
            int id = results.getInt(1);
            int lectureID = results.getInt(2);
            String text = results.getString(3);
            int answered = results.getInt(4);
            arr.add(new Question(id,lectureID, text,answered!=0));
        }
        results.close();
        return arr;
	}
	
	public static Question selectQuestion(Statement stm, int QuestionID) throws SQLException{
		ResultSet results = stm.executeQuery("select * from question where id="+QuestionID+"");
        
        while(results.next())
        {
        	int id = results.getInt(1);
            String text = results.getString(3);
            int answered = results.getInt(4);
	        
            Question ans =new Question(id,QuestionID,text,answered!=0);
            return ans; 
        }
        
        results.close();
        return null;
	}
	
	public static ArrayList<Question> selectAnswered(Statement stm, boolean answered) throws SQLException{
		int answ;
		if(answered)
			answ=1;
		else
			answ=0;		
		
		ResultSet results = stm.executeQuery("select * from question where answered="+ answ +"");
        
        ArrayList<Question> arr = new ArrayList<Question>();
        while(results.next())
        {
       	 
            int id = results.getInt(1);
            int lectureID = results.getInt(2);
            String text = results.getString(3);
            arr.add(new Question(id,lectureID, text,answ!=0));
        }
        results.close();
        return arr;
	}
	
	/*public void incrementVote() throws SQLException{
		vote++;
		stm.execute("update QUESTION set vote="+ vote +" where id = " + id);
	}*/
	

	public void update(Statement stm) throws SQLException {
		String cmd = "update question set text='" + text + "',lectureID=" +lectureID+ " where id="+id+" "; 
		stm.execute(cmd);
	}
	
	public void updateAnswered(Statement stm, boolean answered) throws SQLException {
		int answ;
		if(answered)
			answ=1;
		else
			answ=0;
		
		String cmd = "update question set answered=" + answ + " where id="+id+" "; 
		stm.execute(cmd);
	}

	public void insert(Statement stm) throws SQLException {
		int ans;
		if(answered)
			ans=1;
		else
			ans=0;
		
		String cmd = "INSERT INTO question (text, lectureID,answered) VALUES ('" + text + "'," + lectureID +","+ ans + ")"; 
		stm.execute(cmd);	  
	}

	public void delete(Statement stm) throws SQLException {
		String cmd = "Delete from voting where questionID="+id+"";
		
		stm.execute(cmd);
		 cmd = "Delete from question where ID="+id+" "; 
		stm.execute(cmd);
	}

	public int getLectureID() {
		return lectureID;
	}

	public void setLectureID(int lectureID) {
		this.lectureID = lectureID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getAnswered(){
		return answered;
	}
	public void setAnswered(boolean ans){
		answered=ans;
	}
}

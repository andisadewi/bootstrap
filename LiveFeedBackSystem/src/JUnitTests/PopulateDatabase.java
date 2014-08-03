package JUnitTests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import Backend.*;

public class PopulateDatabase {
	
	private static Statement statement ;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void init(){
		try{
			// connect to database
			ConnectionManager.Initialize() ;
			
			// create statement
			statement = ConnectionManager.Instance.createStatement() ;
					
		} catch(Exception e){
			System.out.println("Error creating connection to database.") ;
		}
	}
	
	public static void populateUser(Statement stm) {
    	
        // The name of the file to open.
        String fileName = "userList.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String[] lines = line.split("\\|") ;
               User user = new User(lines[0], Integer.parseInt(lines[1])) ;
               user.insert(stm) ;
            }	
                        
//            System.out.println("...populate user ok") ;
            // Always close files.
            bufferedReader.close();			
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
        }
        catch(SQLException e){
        	System.out.println("Error inserting User's data to database.") ;
        }
    }

	public static void populateLecture(Statement stm) {
    	
        // The name of the file to open.
        String fileName = "lectureList.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String[] lines = line.split("\\|") ;
               Lecture lecture = new Lecture(sdf.parse(lines[0]), lines[1], lines[2], lines[3], 
            		   lines[4], Integer.parseInt(lines[5]), Integer.parseInt(lines[6]),
            		   Integer.parseInt(lines[7]), Integer.parseInt(lines[8]), Integer.parseInt(lines[9])) ;
               lecture.insert(stm) ;
            }	
                    
//            System.out.println("...populate lecture ok") ;
            // Always close files.
            bufferedReader.close();			
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
        }
        catch(SQLException e){
        	System.out.println("Error inserting Lectures to database.") ;
        }
        catch(ParseException e){
        	System.out.println("Error parsing date.") ;
        }
    }
	
	public static void populateQuestion(Statement stm) {
    	
        // The name of the file to open.
        String fileName = "questionList.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String[] lines = line.split("\\|") ;
               Question question = new Question(Integer.parseInt(lines[0]), lines[1], 
            		   Integer.parseInt(lines[2]) != 0) ;
               question.insert(stm) ;
            }	
                        
//            System.out.println("...populate question ok") ;
            // Always close files.
            bufferedReader.close();			
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
        }
        catch(SQLException e){
        	System.out.println("Error inserting Questions to database. " + e.getMessage()) ;
        }
    }

	public static void populateVoting(Statement stm) {
    	
        // The name of the file to open.
        String fileName = "votingList.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String[] lines = line.split("\\|") ;
               Voting voting = new Voting(lines[0], Integer.parseInt(lines[1])) ;
               voting.insert(stm) ;
            }	
                        
//            System.out.println("...populate voting ok") ;
            // Always close files.
            bufferedReader.close();			
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
        }
        catch(SQLException e){
        	System.out.println("Error inserting Votings to database. " + e.getMessage()) ;
        }
    }

	public static void populateSurvey(Statement stm) {
    	
        // The name of the file to open.
        String fileName = "surveyList.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String[] lines = line.split("\\|") ;
               Survey survey = new Survey(lines[0], Integer.parseInt(lines[1]) != 0, 
            		   sdf.parse(lines[2]), Integer.parseInt(lines[3])) ;
               survey.insert(stm) ;
            }	
                        
//            System.out.println("...populate survey ok") ;
            // Always close files.
            bufferedReader.close();			
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
        }
        catch(ParseException e){
        	System.out.println("Error parsing date.") ;
        }
        catch(SQLException e){
        	System.out.println("Error inserting Surveys to database. " + e.getMessage()) ;
        }
    }

	public static void populateResponse(Statement stm) {
    	
        // The name of the file to open.
        String fileName = "responseList.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String[] lines = line.split("\\|") ;
               Response res = new Response(Integer.parseInt(lines[0]), lines[1], 
            		   Integer.parseInt(lines[2]), Integer.parseInt(lines[3]) != 0) ;
               res.insert(stm) ;
            }	
                        
//            System.out.println("...populate response ok") ;
            // Always close files.
            bufferedReader.close();			
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
        }
        catch(SQLException e){
        	System.out.println("Error inserting Responses to database. " + e.getMessage()) ;
        }
    }

	public static void populateStudentResponse(Statement stm) {
    	
        // The name of the file to open.
        String fileName = "studentResponseList.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String[] lines = line.split("\\|") ;
               StudentResponse res = new StudentResponse(lines[0], Integer.parseInt(lines[1]), 
            		   Integer.parseInt(lines[2])) ;
               res.insert(stm) ;
            }	
                        
//            System.out.println("...populate student response ok") ;
            // Always close files.
            bufferedReader.close();			
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
        }
        catch(SQLException e){
        	System.out.println("Error inserting Student Responses to database. " + e.getMessage()) ;
        }
    }
	
	public static void populateVolumeButton(Statement stm) {
    	
        // The name of the file to open.
        String fileName = "volumeList.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String[] lines = line.split("\\|") ;
               VolumeButton vol = new VolumeButton(lines[0], Integer.parseInt(lines[1]), 
            		   Integer.parseInt(lines[2]) != 0) ;
               vol.insert(stm) ;
            }	
                        
//            System.out.println("...populate volume button ok") ;
            // Always close files.
            bufferedReader.close();			
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
        }
        catch(SQLException e){
        	System.out.println("Error inserting Volume Buttons to database. " + e.getMessage()) ;
        }
    }

	public static void populateSpeedButton(Statement stm) {
    	
        // The name of the file to open.
        String fileName = "speedList.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String[] lines = line.split("\\|") ;
               SpeedButton vol = new SpeedButton(lines[0], Integer.parseInt(lines[1]), 
            		   Integer.parseInt(lines[2]) != 0) ;
               vol.insert(stm) ;
            }	
                        
//            System.out.println("...populate speed button ok") ;
            // Always close files.
            bufferedReader.close();			
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");					
        }
        catch(SQLException e){
        	System.out.println("Error inserting Speed Buttons to database. " + e.getMessage()) ;
        }
    }
	
	public static void main(String [] args) {
		
//		init() ;			
//		populateUser(statement) ;
//		populateLecture(statement) ;
//		populateQuestion(statement) ;
//		populateVoting(statement) ;
//		populateSurvey(statement) ;
//		populateResponse(statement) ;
//		populateStudentResponse(statement) ;
//		populateVolumeButton(statement) ;
//		populateSpeedButton(statement) ;
		
    }
}

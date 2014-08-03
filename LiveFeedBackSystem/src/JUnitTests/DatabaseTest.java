package JUnitTests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.text.ParseException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Backend.* ;
import JUnitTests.PopulateDatabase;

public class DatabaseTest {

	private Statement statement ;
	
	@Before
	public void setUp() throws Exception {
					
		// connect to database
		ConnectionManager.Initialize() ;
		
		// create statement
		statement = ConnectionManager.Instance.createStatement() ;

	}
	
	@After
	public void cleanUp() throws Exception {
		
		ConnectionManager.closeConnection() ;
	}
	
	@Test
	public void testInsertUser(){ 
		try {
			testInsert(new User("abc",2), true) ;
			testInsert(new User("abcd",2), true) ;
			testInsert(new User("abcd",1), false) ;
			testInsert(new User("abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde",2), false) ;
			testInsert(new User("ab",4), false) ;
			testInsert(new User("abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde",2), true) ;
			testInsert(new User("abcd124@/",1), true) ;
			testInsert(new User("abc d",1), true) ;
			
			System.out.println("Passed testInsertUser");

		} catch (SQLException e) {
			fail ("testInsertUser: SQL Exception: Error inserting users");
		}
	}
	
	@Test
	public void testInsertLecture() {
		try {
			testInsert(new Lecture(new Date(), "David", "EN600", "mpgi3", "1234", 0, 0, 100, 100, 100), true) ;
			testInsert(new Lecture(new Date(), "abcdeabcdea", "EN600", "mpgi1", "1234", 0, 0, 100, 100, 100), true) ;
			testInsert(new Lecture(new Date(), "David", "EN600", "mpgi0", "1234", 0, 0, 100, 100, 100), true) ;
			testInsert(new Lecture(new Date(), "David", "EN600", "mpgi5", "1234", 0, 0, 100, 100, 100), true) ;
			testInsert(new Lecture(new Date(), "David", "EN600", "mpgi4", "1234", 0, 0, 100, 100, 100), true) ;
//			testInsert(new Lecture(new Date(), "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde", "EN601", "mpgi2", "1234", 0, 0, 100, 100, 100), false) ;

			System.out.println("Passed testInsertLecture");

		} catch (SQLException e) {
			fail ("testInsertLecture: SQL Exception: Error inserting lectures");
		}
	}
	
	@Test
	public void testInsertQuestion() {
		try {
			 testInsert(new Question(1, "who are you", true), true) ;
			 testInsert(new Question(1, "who is it", false), true) ;
			 testInsert(new Question(3, "who are you", true), true) ;
			 testInsert(new Question(4, "who are you", false), true) ;
//			 testInsert(new Question(7, "who are you", true), false) ;
//			 testInsert(new Question(8, "who are you", false), false) ;
			 
			System.out.println("Passed testInsertQuestion");

		} catch (SQLException e) {
			fail ("testInsertQuestion: SQL Exception: Error inserting questions");
		}
	}
	
	@Test
	public void testInsertVoting() {
		try {
			 testInsert(new Voting("abc",1), true) ;
			 testInsert(new Voting("abc",1), false) ;
			 testInsert(new Voting("abc",2), true) ;
			 testInsert(new Voting("abc d",3), true) ;
			 testInsert(new Voting("baa",4), false) ;
			 testInsert(new Voting("abc d",4), true) ;
			 testInsert(new Voting("abc",5), false) ;
			 
			System.out.println("Passed testInsertVoting");

		} catch (SQLException e) {
			fail ("testInsertVoting: SQL Exception: Error inserting votings");
		}
	}

	@Test
	public void testInsertSurvey() {
		try {
			testInsert(new Survey("who is sisto", true, new Date(), 1),true) ;
			testInsert(new Survey("who is sisto", false, new Date(), 2),true) ;
			testInsert(new Survey("who is sisto", true, new Date(), 1),true) ;
			testInsert(new Survey("who is sisto", true, new Date(), 1),true) ;
			testInsert(new Survey("who is sisto", true, new Date(), 3),true) ;
//			testInsert(new Survey("who is sisto", true, new Date(), 10),false) ;

			System.out.println("Passed testInsertSurvey");

		} catch (SQLException e) {
			fail ("testInsertSurvey: SQL Exception: Error inserting surveys");
		}
	}
	
	@Test
	public void testInsertResponse() {
		try {
			 testInsert(new Response(1,"jhlkknk",1,true), true) ;
			 testInsert(new Response(2,"jhlkknk",1,true), true) ;
			 testInsert(new Response(10,"jhlkknk",1,true), false) ;
			 testInsert(new Response(2,"jhlkknk",1,true), false) ;
			 testInsert(new Response(1,"jhlkknk",5,true), false) ;
			 testInsert(new Response(1,"jhlkknk",2,false), true) ;
			 testInsert(new Response(1,"jhlkknk",3,true), true) ;
			 
			System.out.println("Passed testInsertResponse");
			
		} catch (SQLException e) {
			fail ("testInsertResponse: SQL Exception: Error inserting responses");
		}
	}
	
	@Test
	public void testInsertStudResponse() {
		try {
			 testInsert(new StudentResponse("abc d", 1, 1), true) ;
			 testInsert(new StudentResponse("abc d", 1, 1), false) ;
			 testInsert(new StudentResponse("abc", 1, 2), true) ;
			 testInsert(new StudentResponse("baa", 1, 1), false) ;
			 testInsert(new StudentResponse("abc d", 2, 5), false) ;
			 testInsert(new StudentResponse("abc d", 10, 1), false) ;
			 testInsert(new StudentResponse("abcd", 2, 1), true) ;
			 testInsert(new StudentResponse("abcd", 2, 2), true) ;
			
			System.out.println("Passed testInsertStudResponse");
			
		} catch (SQLException e) {
			fail ("testInsertStudResponse: SQL Exception: Error inserting student responses");
		}
	}
	
	
	@Test
	public void testInsertVolumeButton() {
		try {

			testInsert(new VolumeButton("abc d", 1, true), true) ;
			testInsert(new VolumeButton("abc d", 2, false), true) ;
			testInsert(new VolumeButton("abc", 2, true), true) ;
			testInsert(new VolumeButton("abcd", 4, true), true) ;
//			testInsert(new VolumeButton("abcd", 4, true), false) ; 
//			testInsert(new VolumeButton("abc d", 1, true), false) ;
//			testInsert(new VolumeButton("ba", 1, true), false) ;
//			testInsert(new VolumeButton("abc d", 10, true), false) ;

			System.out.println("Passed testInsertVolumeButton");

		} catch (SQLException e) {
			fail ("testInsertVolumeButton: SQL Exception: Error inserting volume buttons");
		}
	}
	
	
	@Test
	public void testInsertSpeedButton() {
		try {
			testInsert(new SpeedButton("abc d", 1, true), true) ;
			testInsert(new SpeedButton("abc d", 2, false), true) ;
			testInsert(new SpeedButton("abc", 2, true), true) ;
			testInsert(new SpeedButton("abcd", 4, true), true) ;
//			testInsert(new SpeedButton("abc d", 1, true), false) ;
//			testInsert(new SpeedButton("ba", 1, true), false) ;
//			testInsert(new SpeedButton("abc d", 10, true), false) ;

			statement.execute("delete from response") ;
			System.out.println("Passed testInsertSpeedButton");

		} catch (SQLException e) {
			fail ("testInsertSpeedButton: SQL Exception: Error inserting speed buttons");
		}
	}
	
	
	// ===============================================================================================

	@Test
	public void testPopulateUser() {
		try {
			PopulateDatabase.populateUser(statement) ;
			
			statement.executeQuery("SELECT COUNT(*) FROM \"user\"");
			ResultSet result = statement.getResultSet();
			
			if (result.next()) {
				if (result.getInt(1) == 5007) {
					System.out.println("Passed testPopulateUser");
					result.close();
					return;
				}
			}
			fail ("testPopulateUser: Definitely incorrect result");
		} catch (SQLException e) {
			fail ("testPopulateUser: SQL Exception: Error inserting users");
		}
	}
	
	
	@Test
	public void testPopulateLecture() {
		try {
			PopulateDatabase.populateLecture(statement) ;
			
			statement.executeQuery("SELECT COUNT(*) FROM lecture");
			ResultSet result = statement.getResultSet();
			
			if (result.next()) {
				if (result.getInt(1) == 505) {
					System.out.println("Passed testPopulateLecture");
					result.close();
					return;
				}
			}
			fail ("testPopulateLecture: Definitely incorrect result");
		} catch (SQLException e) {
			fail ("testPopulateLecture: SQL Exception: Error inserting lectures");
		}
	}
	
	
	@Test
	public void testPopulateQuestion() {
		try {
			PopulateDatabase.populateQuestion(statement) ;
			
			statement.executeQuery("SELECT COUNT(*) FROM question");
			ResultSet result = statement.getResultSet();
			
			if (result.next()) {
				if (result.getInt(1) == 504) {
					System.out.println("Passed testPopulateQuestion");
					result.close();
					return;
				}
			}
			fail ("testPopulateQuestion: Definitely incorrect result");
		} catch (SQLException e) {
			fail ("testPopulateQuestion: SQL Exception: Error inserting questions");
		}
	}
	

	@Test
	public void testPopulateVoting() {
		try {
			PopulateDatabase.populateVoting(statement) ;
			
			statement.executeQuery("SELECT COUNT(*) FROM voting");
			ResultSet result = statement.getResultSet();
			
			if (result.next()) {
				if (result.getInt(1) == 1004) {
					System.out.println("Passed testPopulateVoting");
					result.close();
					return;
				}
			}
			fail ("testPopulateVoting: Definitely incorrect result");
		} catch (SQLException e) {
			fail ("testPopulateVoting: SQL Exception: Error inserting votings");
		}
	}
	
	
	@Test
	public void testPopulateSurvey() {
		try {
			PopulateDatabase.populateSurvey(statement) ;
			
			statement.executeQuery("SELECT COUNT(*) FROM survey");
			ResultSet result = statement.getResultSet();
			
			if (result.next()) {
				if (result.getInt(1) == 405) {
					System.out.println("Passed testPopulateSurvey");
					result.close();
					return;
				}
			}
			fail ("testPopulateSurvey: Definitely incorrect result");
		} catch (SQLException e) {
			fail ("testPopulateSurvey: SQL Exception: Error inserting surveys");
		}
	}
	

	@Test
	public void testPopulateResponse() {
		try {
			PopulateDatabase.populateResponse(statement) ;
			
			statement.executeQuery("SELECT COUNT(*) FROM response");
			ResultSet result = statement.getResultSet();
			
			if (result.next()) {
				if (result.getInt(1) == 1600) {
					System.out.println("Passed testPopulateResponse");
					result.close();
					return;
				}
			}
			fail ("testPopulateResponse: Definitely incorrect result");
		} catch (SQLException e) {
			fail ("testPopulateResponse: SQL Exception: Error inserting responses");
		}
	}
	
	
	@Test
	public void testPopulateStudResponse() {
		try {
			PopulateDatabase.populateStudentResponse(statement) ;
			
			statement.executeQuery("SELECT COUNT(*) FROM studentResponse");
			ResultSet result = statement.getResultSet();
			
			if (result.next()) {
				if (result.getInt(1) == 1004) {
					System.out.println("Passed testPopulateStudResponse");
					result.close();
					return;
				}
			}
			fail ("testPopulateStudResponse: Definitely incorrect result");
		} catch (SQLException e) {
			fail ("testPopulateStudResponse: SQL Exception: Error inserting student responses");
		}
	}
	

	@Test
	public void testPopulateVolumeButton() {
		try {
			PopulateDatabase.populateVolumeButton(statement) ;
			
			statement.executeQuery("SELECT COUNT(*) FROM volumeButton");
			ResultSet result = statement.getResultSet();
			
			if (result.next()) {
				if (result.getInt(1) == 1004) {
					System.out.println("Passed testPopulateVolumeButton");
					result.close();
					return;
				}
			}
			fail ("testPopulateVolumeButton: Definitely incorrect result");
		} catch (SQLException e) {
			fail ("testPopulateVolumeButton: SQL Exception: Error inserting volume buttons");
		}
	}
	
	
	@Test
	public void testPopulateSpeedButton() {
		try {
			PopulateDatabase.populateSpeedButton(statement) ;
			
			statement.executeQuery("SELECT COUNT(*) FROM speedButton");
			ResultSet result = statement.getResultSet();
			
			if (result.next()) {
				if (result.getInt(1) == 1004) {
					System.out.println("Passed testPopulateSpeedButton");
					result.close();
					return;
				}
			}
			fail ("testPopulateSpeedButton: Definitely incorrect result");
		} catch (SQLException e) {
			fail ("testPopulateSpeedButton: SQL Exception: Error inserting speed buttons");
		}
	}
	
	
	// ===============================================================================================

	@Test
	public void testUpdateUser(){ 
		try {
			testUpdate(new User("abc",1), true) ;
			testUpdate(new User("abcd",1), true) ;
//			testUpdate(new User("abcdeeeee",1), false) ;
//			testUpdate(new User("abc",4), false) ;
			testUpdate(new User("abcd124@/",2), true) ;
			testUpdate(new User("abc d",2), true) ;
			
			System.out.println("Passed testUpdateUser");

		} catch (SQLException e) {
			fail ("testUpdateUser: SQL Exception: Error updating users. " + e.getMessage());
		}
	}
	
	@Test
	public void testUpdateLecture() {
		try {
			testUpdate(new Lecture(new Date(), "Nate", "EN600", "mpgi3", "1234", 0, 0, 100, 100, 100), true) ;
			testUpdate(new Lecture(new Date(), "abcdeabcdea", "EN600", "mpgi1", "1234", 0, 0, 100, 100, 100), true) ;
			testUpdate(new Lecture(new Date(), "Ron", "EN600", "mpgi0", "1234", 0, 0, 100, 100, 100), true) ;
			testUpdate(new Lecture(new Date(), "Alan", "EN600", "mpgi5", "1234", 0, 0, 100, 100, 100), true) ;
			testUpdate(new Lecture(new Date(), "Adam", "EN600", "mpgi4", "1234", 0, 0, 100, 100, 100), true) ;

			System.out.println("Passed testUpdateLecture");

		} catch (SQLException e) {
			fail ("testUpdateLecture: SQL Exception: Error updating lectures. " + e.getMessage());
		}
	}
	
	@Test
	public void testUpdateQuestion() {
		try {
			testUpdate(new Question(1, "why", true), true) ;
			testUpdate(new Question(1, "much", false), true) ;
			testUpdate(new Question(3, "ado", true), true) ;
			testUpdate(new Question(4, "about nothing", false), true) ;
			 
			System.out.println("Passed testUpdateQuestion");

		} catch (SQLException e) {
			fail ("testUpdateQuestion: SQL Exception: Error updating questions. " + e.getMessage());
		}
	}
	
	@Test
	public void testUpdateVoting() {
		try {
			testUpdate(new Voting("abc",1), true) ;
			testUpdate(new Voting("abc",2), true) ;
			testUpdate(new Voting("abc d",3), true) ;
//			testUpdate(new Voting("baaaaaaaaaaaa",4), false) ;
			testUpdate(new Voting("abc d",4), true) ;
			 
			System.out.println("Passed testUpdateVoting");

		} catch (SQLException e) {
			fail ("testUpdateVoting: SQL Exception: Error updating votings. " + e.getMessage());
		}
	}

	@Test
	public void testUpdateSurvey() {
		try {
			testUpdate(new Survey("why", true, new Date(), 1),true) ;
			testUpdate(new Survey("what", false, new Date(), 2),true) ;
			testUpdate(new Survey("who", true, new Date(), 1),true) ;
			testUpdate(new Survey("where", false, new Date(), 1),true) ;
			testUpdate(new Survey("when", true, new Date(), 3),true) ;

			System.out.println("Passed testUpdateSurvey");

		} catch (SQLException e) {
			fail ("testUpdateSurvey: SQL Exception: Error updating surveys. " + e.getMessage());
		}
	}
	
	@Test
	public void testUpdateResponse() {
		try {
			 testUpdate(new Response(1,"jhlkknk",1,false), true) ;
			 testUpdate(new Response(2,"jhlkknkk",1,false), true) ;
			 testUpdate(new Response(1,"jhlkknkkk",2,true), true) ;
			 testUpdate(new Response(1,"jhlkknkkkk",3,false), true) ;
			 
			System.out.println("Passed testUpdateResponse");
			
		} catch (SQLException e) {
			fail ("testUpdateResponse: SQL Exception: Error updating responses. " + e.getMessage());
		}
	}
	
	@Test
	public void testUpdateVolumeButton() {
		try {

			testUpdate(new VolumeButton("abc d", 1, false), true) ;
			testUpdate(new VolumeButton("abc d", 2, true), true) ;
			testUpdate(new VolumeButton("abc", 2, false), true) ;
			testUpdate(new VolumeButton("abcd", 4, false), true) ;
//			testUpdate(new VolumeButton("malrey", 4, false), false) ;
			
			System.out.println("Passed testUpdateVolumeButton");

		} catch (SQLException e) {
			fail ("testUpdateVolumeButton: SQL Exception: Error updating volume buttons. " + e.getMessage());
		}
	}
	
	@Test
	public void testUpdateSpeedButton() {
		try {
			testUpdate(new SpeedButton("abc d", 2, false), true) ;
			testUpdate(new SpeedButton("abc d", 2, true), true) ;
			testUpdate(new SpeedButton("abc", 2, false), true) ;
			testUpdate(new SpeedButton("abcd", 4, false), true) ;
//			testUpdate(new VolumeButton("malrey", 4, false), false) ;
			
			System.out.println("Passed testUpdateSpeedButton");

		} catch (SQLException e) {
			fail ("testUpdateSpeedButton: SQL Exception: Error updating speed buttons. " + e.getMessage());
		}
	}
	
	// ===============================================================================================
	@Test
	public void testSurveyChoiceCounter() {
		
			StudentResponse sr1 = new StudentResponse("a",1,1) ;
			int count1 = sr1.surveyChoiceCounter(statement) ;
			assertTrue("Number incorrect, it should be 1", count1 == 1) ;
			
			StudentResponse sr2 = new StudentResponse("a",1,2) ;
			int count2 = sr2.surveyChoiceCounter(statement) ;
			assertTrue("Number incorrect, it should be 1", count2 == 1) ;
			
			StudentResponse sr3 = new StudentResponse("a",1,3) ;
			int count3 = sr3.surveyChoiceCounter(statement) ;
			assertTrue("Number incorrect, it should be 0", count3 == 0) ;
			
			StudentResponse sr4 = new StudentResponse("a",1,4) ;
			int count4 = sr4.surveyChoiceCounter(statement) ;
			assertTrue("Number incorrect, it should be 1", count4 == 1) ;

			System.out.println("Passed testSurveyChoiceCounter");
	}
	
	@Test
	public void testSurveyCounter() {
		
			StudentResponse sr = new StudentResponse("a",1,2) ;
			int count1 = sr.surveyCounter(statement, 1) ;
			assertTrue("Number incorrect, it should be 3", count1 == 3) ;

			int count2 = sr.surveyCounter(statement, 2) ;
			assertTrue("Number incorrect, it should be 2", count2 == 2) ;

			int count3 = sr.surveyCounter(statement, 3) ;
			assertTrue("Number incorrect, it should be 3", count3 == 3) ;

			System.out.println("Passed testSurveyCounter");
		
	}
	
	@Test
	public void testIsAnswered() {
		
			StudentResponse sr = new StudentResponse("a",1,2) ;
			boolean bool1 = sr.isAnswered(statement,"abc",1) ;
			assertTrue("Boolean incorrect, it should be true", bool1) ;
			
			boolean bool2 = sr.isAnswered(statement,"pmgmod",1) ;
			assertTrue("Boolean incorrect, it should be false", !bool2) ;

			boolean bool3 = sr.isAnswered(statement,"abc d",1) ;
			assertTrue("Boolean incorrect, it should be true", bool3) ;

			System.out.println("Passed testIsAnswered");
		
	}
	
	@Test
	public void testIsVoted() {
		
			boolean bool1 = Voting.isVoted(statement,"hhvhmu",319) ;
			assertTrue("Boolean incorrect, it should be true", bool1) ;
			
			boolean bool2 = Voting.isVoted(statement,"ihgjdj",1) ;
			assertTrue("Boolean incorrect, it should be false", !bool2) ;

			boolean bool3 = Voting.isVoted(statement,"abc d",3) ;
			assertTrue("Boolean incorrect, it should be true", bool3) ;

			System.out.println("Passed testIsVoted");
		
	}
	
	
	// ===============================================================================================
	
	public void testInsert(IEntity object, boolean korrekt) throws SQLException{
		boolean bool = false;;
		try{
			object.insert(statement) ;
		} catch (SQLException e){
			if(!korrekt){
				bool=true;
				//immer wenn eine Anweisung gegeben wird, die den Constraint verletzt, wird eine SQLException geworfen
			} else{		//es gab eine SQLException obwohl das Ergebnis eigentlich korrekt sein sollte
				throw new SQLException(e);
			}
		} catch(ParseException p){
			System.out.println("Parse error") ;
		}
		if (!korrekt && !bool){
			throw new SQLException();
		}
	}
	
	public void testUpdate(IEntity object, boolean korrekt) throws SQLException{
		boolean bool = false;;
		try{
			object.update(statement) ;
		} catch (SQLException e){
			if(!korrekt){
				bool=true;
				//immer wenn eine Anweisung gegeben wird, die den Constraint verletzt, wird eine SQLException geworfen
			} else{		//es gab eine SQLException obwohl das Ergebnis eigentlich korrekt sein sollte
				throw new SQLException(e);
			}
		} 
		if (!korrekt && !bool){
			throw new SQLException();
		}
	}

}

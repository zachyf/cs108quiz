package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.PriorityQueue;


/**
 * Class that manages list of quizzes
 * @author jrfutrell
 * Class that maintains list of quizzes, and manages connection to database.
 */
public class QuizManager {
	//The list of all quizzes created, where the index = the quizID for an invdividual quiz
	private ArrayList<Quiz> quizzes;
	private PriorityQueue<Quiz> popularQuizzes;

	/**
	 * Constructor for QuizManager Class
	 * Initializes data structures, and populates data structures with
	 * dumb of data from Quiz DB. 
	 * @param db active connection for access to database. 
	 */
	public QuizManager(DBConnection db){
		this.quizzes = new ArrayList<Quiz>();
		this.popularQuizzes = new PriorityQueue<Quiz>();
		populateQuizzesList(db);
	}
	
	/**
	 * Queries database to get list of quizzes, and then inserts list of
	 * quizzes into QuizManager class
	 * @param db
	 */
	private void populateQuizzesList(DBConnection db){
		try {
			ResultSet rs = db.executeQuery("SELECT * FROM quizzes order by id;");
			while(rs.next()) {
				Quiz quiz = new Quiz(rs.getInt("id"), rs.getString("name"), rs.getString("description"), 
						Quiz.intToBoolean(rs.getInt("onePage")), Quiz.intToBoolean(rs.getInt("isRandomOrder")), 
						Quiz.intToBoolean(rs.getInt("isImmediate")), Quiz.intToBoolean(rs.getInt("hasPracticeMode")));
				this.quizzes.add(quiz);
				quiz.addQuestions(db);
			}
		} catch (SQLException e) {
	         e.printStackTrace();
		} 	
	}

	/**
	 * Add quiz to QuizManager data structure
	 * @param quiz to add to quiz array.
	 */
	public void addQuiz(Quiz quiz){
		this.quizzes.add(quiz);
	}
	
	/**
	 * Get next available QuizId based on the array of Quizzes
	 * @return next available QuizId based on the array of Quizzes
	 */
	public int getNextId(){
		return this.quizzes.size();
	}
	
	/**
	 * Gets a list of recently created quizzes and creates an unordered
	 * list with a link to each quiz.
	 * @return ordered HTML list of quizzes, with links to quizzes 
	 */
	public String getRecentQuizzes(){
		StringBuilder sb = new StringBuilder();
		for(int i = this.quizzes.size() - 1; i >= 0; i--){
			sb.append("<ol>" + this.quizzes.get(i).getNameAndDate() + "</ol>");
		}
		return sb.toString();
	}
	
	/**
	 * Gets quiz at specified index. Since index = quizID,
	 * index is the equivalent of quizID, so effectively
	 * gets Quiz by QuizID.
	 * @param index QuizID of the desired quiz
	 * @return Quiz of QuizID requested
	 */
	public Quiz getQuizAt(int index){
		return this.quizzes.get(index);
	}
	
	/*
	 *
GetPopularQuizzes

	 */
	
	
}

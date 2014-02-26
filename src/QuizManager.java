package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class QuizManager {
	private ArrayList<Quiz> quizzes;
	private PriorityQueue<Quiz> popularQuizzes;

	public QuizManager(DBConnection db){
		this.quizzes = new ArrayList<Quiz>();
		this.popularQuizzes = new PriorityQueue<Quiz>();
		populateQuizzesList(db);
	}
	
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

	public void addQuiz(Quiz q){
		this.quizzes.add(q);
	}
	
	public int getNextId(){
		return this.quizzes.size();
	}
	
	public String getRecentQuizzes(){
		StringBuilder sb = new StringBuilder();
		for(int i = this.quizzes.size() - 1; i >= 0; i--){
			sb.append("<li>" + this.quizzes.get(i).getNameAndDate() + "</li>");
		}
		return sb.toString();
	}
	
	public Quiz getQuizAt(int index){
		return this.quizzes.get(index);
	}
	
	/*
	 *
GetPopularQuizzes

	 */
	
	
}

package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Quiz {
	
	private int id;
	private String name;
	private ArrayList<Answer> answers; //(History tracking)
	private Queue<User> recentTestTakers;
	private ArrayList<Question> questions;
	String description;
	private User creator;
	private Date date;
	/*
	 * One Page vs. Multiple Pages - Multiple Pages = 1 question per page
	 * Servlet prints all questions or goes into multiple question flow if false 
	 */
	private boolean onePage; 
	private boolean isRandomOrder; //order of  questions or set order
	private boolean isImmediateCorrection; // - for multiple page, immediately determines if correct answer Ignore if single question
	private boolean hasPracticeMode; // - Whether the quiz can be taken in practice mode

	public Quiz(int id, String name, String description, boolean onePage, boolean isRandomOrder, boolean isImmediateCorrection, boolean hasPracticeMode){
		this.name = name;
		this.id = id;
		this.description = description;
		this.onePage = onePage;
		this.isImmediateCorrection = isImmediateCorrection;
		this.isRandomOrder = isRandomOrder;
		this.hasPracticeMode = hasPracticeMode;
		this.answers = new ArrayList<Answer>();
		this.recentTestTakers = new LinkedList<User>();
		this.questions = new ArrayList<Question>();
		this.date = new Date();
	}
	
	public static int boolToInt(boolean b){
		if(b) 
			return 1;
		return 0;
	}
	
	public static boolean intToBoolean(int i){
		return i > 0;
	}
	
	public String PrintAllQuestions(){
		StringBuilder sb = new StringBuilder();
		System.out.println(this.questions.size());
		for(int i = 0; i < this.questions.size(); i++)
			sb.append(this.questions.get(i).getQuestion());
		return sb.toString();
	}
	
	public String getQuestionAt(int index){
		return this.questions.get(index).getQuestion();
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public User getCreator(){
		return this.creator;
	}
	
	public int getNumUsersTaken(){
		return answers.size();
	}
	
	public int getNumQuestions(){
		return this.questions.size();
	}

	public void addQuestion(Question q, String type, DBConnection db){
		String insertion = "INSERT INTO questions VALUES (" + this.id + ",\""  + q.rawQuestion() + "\",\"" 
		+ q.getAnswer() + "\"," + this.questions.size() + ",\"" + type + "\");";
		this.questions.add(q);
		db.updateDB(insertion);
	}
	
	public void addQuestions(DBConnection db){
		String query = "Select * from questions where quizID = " + this.id + " order by questionNum asc;";
		try {
			ResultSet rs = db.executeQuery(query);
			while(rs.next()) {
				String type = rs.getString("type");
				if(type.equals("QuestionResponse"))
	            	this.questions.add(new QuestionResponse(rs.getString("question"), rs.getString("answer"), rs.getInt("questionNum")));
				else if (type.equals("Picture"))
	            	this.questions.add(new PictureResponse(rs.getString("question"), rs.getString("answer"), rs.getInt("questionNum")));
				else if (type.equals("FillInBlank"))
	            	this.questions.add(new FillInTheBlank(rs.getString("question"), rs.getString("answer"), rs.getInt("questionNum")));
			}
		} catch (SQLException e) {
         e.printStackTrace();
		} 
	}
	
	public ArrayList<Question> getQuestionList(){
		@SuppressWarnings("unchecked")
		ArrayList<Question> questions_copy = (ArrayList<Question>)this.questions.clone();
		if(this.isRandomOrder){
			Collections.shuffle(questions_copy);
		}
		return questions_copy;
	}
	
	public String getNameAndDate(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return ("<a href=quizPage.jsp?id=" + this.id + "> "+ name + " " + df.format(date) + "</a>");
	}
	
	public Queue<Integer> getQuestionOrder(){
		LinkedList<Integer> q_order = new LinkedList<Integer>();
		for(int i = 0; i < this.questions.size(); i++){
			q_order.addLast(i);
		}
		if(this.isRandomOrder)
			Collections.shuffle(q_order);
		return q_order;
	}
	
	public boolean isSinglePage(){
		return this.onePage;
	}
	
	public boolean isImmediateCorrection(){
		return this.isImmediateCorrection;
	}


}

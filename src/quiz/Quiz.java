package quiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Quiz {
	
	private int id;
	private String name;
	private HashMap<String, Answer> answers; //(History tracking)
	private Queue<String> recentTestTakers;
	private ArrayList<Question> questions;
	String description;
	private String creatorName;
	private Date date;
	
	/*
	 * One Page vs. Multiple Pages - Multiple Pages = 1 question per page
	 * Servlet prints all questions or goes into multiple question flow if false 
	 */
	private boolean onePage; 
	private boolean isRandomOrder; //order of  questions or set order
	private boolean isImmediateCorrection; // - for multiple page, immediately determines if correct answer Ignore if single question
	private boolean hasPracticeMode; // - Whether the quiz can be taken in practice mode

	public Quiz(String creatorName, int id, String name, String description, boolean onePage, boolean isRandomOrder, boolean isImmediateCorrection, boolean hasPracticeMode){
		this.name = name;
		this.id = id;
		this.description = description;
		this.onePage = onePage;
		this.isImmediateCorrection = isImmediateCorrection;
		this.isRandomOrder = isRandomOrder;
		this.hasPracticeMode = hasPracticeMode;
		this.answers = new HashMap<String, Answer>();
		this.recentTestTakers = new LinkedList<String>();
		this.questions = new ArrayList<Question>();
		this.date = new Date();
		this.creatorName = creatorName;
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
		for(int i = 0; i < this.questions.size(); i++)
			sb.append(this.questions.get(i).getQuestion());
		return sb.toString();
	}
	
	public String getQuestionAt(int index){
		return this.questions.get(index).getQuestion();
	}
	
	public Question getQuestion(int index){
		return this.questions.get(index);
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
	
	public String getCreator(){
		return this.creatorName;
	}
	
	public int getNumUsersTaken(){
		return answers.size();
	}
	
	public int getNumQuestions(){
		return this.questions.size();
	}

	public void addQuestion(Question q){
		this.questions.add(q);
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
	
	public void addAnswer(Answer a){
		this.answers.put(a.getUser(), a);
		this.recentTestTakers.add(a.getUser());
	}
	
	public double getUserScore(String u){
		return this.answers.get(u).getScore();
	}

}

package quiz;

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
		for(int i = 0; i < this.questions.size(); i++)
			sb.append(this.questions.get(i).getQuestion() + "<br><br/>");
		return sb.toString();
	}
	
	public String PrintQuestion(int index){
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
		return ("<a href=quizPage.jsp?id=" + this.id + "> "+ name + " " + date.toString() + "</a>");
	}
	
	


}

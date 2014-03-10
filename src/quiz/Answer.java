package quiz;

import java.sql.Timestamp;
import java.util.*;


public class Answer {

	private int numCorrect;
	private Timestamp startTime;
	private long timeToComplete;
	private String user;
	private Quiz quiz;
	private HashMap<Question, String> answers;
	private Timestamp timeSubmitted;
	
	public Answer(String user, Quiz quiz) {
		this.user = user;
		this.quiz = quiz;
		this.numCorrect = 0;
		this.answers = new HashMap<Question, String>();
		this.startTime = new Timestamp(System.currentTimeMillis());
	}
	
	public int getNumCorrect(){
		return this.numCorrect;
	}
	
	public double getScore() {
		return (double)this.numCorrect/this.answers.size();
	}
	
	public void endTimer() {
		this.timeSubmitted = new Timestamp(System.currentTimeMillis());
		this.timeToComplete = this.timeSubmitted.getTime() - this.startTime.getTime();
	}
	
	public void setAnswer(Question question, String answer) {
		if (question.checkAnswer(answer)) {
			this.numCorrect++;
		}
		answers.put(question, answer);
	}
	
	public long getTimeToComplete() {
		return this.timeToComplete;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public Quiz getQuiz() {
		return this.quiz;
	}
	
	public Timestamp getDateCompleted() {
		return this.timeSubmitted;
	}
	
	public String getAnswerToQuestion(Question q){
		return this.answers.get(q);
	}
	
	public int compareTo(Answer answer) {
		double thisScore = this.getScore();
		double compareScore = answer.getScore();
		if (thisScore == compareScore) {
			return (int)(this.timeToComplete - answer.timeToComplete);
		} else {
			return (int)(thisScore - compareScore);
		}
	}
		
}

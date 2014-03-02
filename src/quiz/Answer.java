package quiz;

import java.util.*;


public class Answer {

	private int numCorrect;
	private long startTime;
	private long timeToComplete;
	private User user;
	private Quiz quiz;
	private HashMap<Question, String> answers;
	private Date dateCompleted;
	
	public Answer(User user, Quiz quiz) {
		this.user = user;
		this.quiz = quiz;
		this.numCorrect = 0;
		this.answers = new HashMap<Question, String>();
	}		
	
	public double getScore() {
		return (double)this.numCorrect/this.answers.size();
	}
	
	public void startTimer() {
		this.startTime = System.currentTimeMillis();
	}
	
	public void endTimer() {
		this.timeToComplete = System.currentTimeMillis() - this.startTime;
		this.dateCompleted = new Date();
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
	
	public User getUser() {
		return this.user;
	}
	
	public Quiz getQuiz() {
		return this.quiz;
	}
	
	public Date getDateCompleted() {
		return this.dateCompleted;
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

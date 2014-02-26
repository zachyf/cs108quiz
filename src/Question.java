package quiz;

public abstract class Question {
	
	public abstract String getQuestion();
	
	public abstract boolean checkAnswer(String answer);
	
	public abstract String getAnswer();
	
	public abstract String rawQuestion();
		
}

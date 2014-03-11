package quiz;

public abstract class Question {
	
	public abstract String getQuestion(int num);
	
	public abstract boolean checkAnswer(String answer);
	
	public abstract String getAnswer();
	
	public abstract String rawQuestion();
	
	public abstract String displayQuestion(int num);
	
	public abstract String getType();
	
	public abstract int getNum();
		
}

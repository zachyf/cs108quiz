package quiz;

public class FillInTheBlank extends Question {

	private String question, answer;
	private int num;
	
	/**
	 * Constructor for FillInTheBlank
	 * @param question: A String of the question
	 * @param answer: A String of the answer
	 * @param num: An int for the number of the question in the quiz
	 */
	public FillInTheBlank(String question, String answer, int num) {
		this.question = question;
		this.answer = answer;
		this.num = num;
		
	}
	
	@Override
	public String rawQuestion(){
		return this.question;
	}
	
	@Override
	public String getType(){
		return "FillInBlank";
	}
	
	@Override
	public int getNum(){
		return this.num;
	}

	/**
	 * getQuestion returns a String of the HTML required to
	 * display the question. This includes the question and the text
	 * input for the answer.
	 */
	@Override
	public String getQuestion(int num) {
		StringBuilder buff = new StringBuilder();
		
		// Generate the HTML for the question
		String numStr = String.valueOf(num);
		buff.append("<p class=question>" + numStr + ". " + this.question + "</p>");		
		buff.append("<input type=\"text\" name=\""+ this.num + "\"><br>");
		
		return buff.toString();
	}
	
	/**
	 * displayQuestion returns a String of the HTML required to
	 * display the question and answer. This does NOT include an image and the text
	 * input for the answer.
	 */
	@Override
	public String displayQuestion(int num) {
		StringBuilder buff = new StringBuilder();
		
		// Generate the HTML for the question
		String numStr = String.valueOf(num);
		buff.append("<p class=question>" + numStr + ". " + this.question + "</p>");		
		buff.append("<p class=answer> Answer is: " + answer + "</p>");
		return buff.toString();
	}

	/**
	 * checkAnswer takes a String answer and returns true if the
	 * answer is correct, false otherwise.
	 */
	@Override
	public boolean checkAnswer(String answer) {
		return this.answer.toLowerCase().equals(answer.toLowerCase());
	}

	/**
	 * getAnswer returns the correct answer as a String
	 */
	@Override
	public String getAnswer() {
		return this.answer;
	}

}

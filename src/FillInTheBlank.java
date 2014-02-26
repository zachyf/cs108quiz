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
	
	
	/**
	 * getQuestion returns a String of the HTML required to
	 * display the question. This includes the question and the text
	 * input for the answer.
	 */
	@Override
	public String getQuestion() {
		StringBuilder buff = new StringBuilder();
		
		// Generate the HTML for the question
		String numStr = String.valueOf(num);
		buff.append("<p class=question>" + numStr + ". " + this.question + "</p>");		
		buff.append("<input type=text name="+ numStr + "><br>");
		
		return buff.toString();
	}

	/**
	 * checkAnswer takes a String answer and returns true if the
	 * answer is correct, false otherwise.
	 */
	@Override
	public boolean checkAnswer(String answer) {
		return this.answer.equals(answer);
	}

	/**
	 * getAnswer returns the correct answer as a String
	 */
	@Override
	public String getAnswer() {
		return this.answer;
	}

}

package quiz;

public class PictureResponse extends Question {
	
	private static final int pixelSize = 300;
	private String question, answer;
	private int num;
	
	/**
	 * Constructor for PictureResponse
	 * @param question: A String of the URL for the picture
	 * @param answer: A String of the answer
	 * @param num: An int for the number of the question in the quiz
	 */
	public PictureResponse(String question, String answer, int num) {
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
	 * display the question. This includes an image and the text
	 * input for the answer.
	 */
	@Override
	public String getQuestion() {
		StringBuilder buff = new StringBuilder();
		
		// Generate the HTML for the question
		String numStr = String.valueOf(num);
		buff.append("<p class=question>" + numStr + ".</p>");	
		buff.append("<img src=" + question + 
				" width=" + pixelSize + " height=" + pixelSize +"><br>");		
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

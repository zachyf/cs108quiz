package quiz;

public class PictureResponse extends Question {
	
	private static final int pixelSize = 300;
	private String question, answer;
	private int num;
	
	public PictureResponse(String question, String answer, int num) {
		this.question = question;
		this.answer = answer;
		this.num = num;
	}

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

	@Override
	public boolean checkAnswer(String answer) {
		return this.answer.equals(answer);
	}

	@Override
	public String getAnswer() {
		return this.answer;
	}

}

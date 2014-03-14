package quiz;

public class MultiAnswerQuestion extends Question {

	private String question, answer;
	private int num, numAnswers;
	private boolean ordered;
	
	/**
	 * Constructor for QuestionResponse
	 * @param question: A String of the question
	 * @param answer: A String of the answer
	 * @param num: An int for the number of the question in the quiz
	 */
	public MultiAnswerQuestion(String question, String answer, int num) {
		this.question = question;
		this.answer = answer;
		this.num = num;
		
		String[] parts = answer.split(", ");
		this.numAnswers = parts.length - 1;
		
		if (parts[parts.length - 1].equals("&=ordered")) {
			this.ordered = true;
		} else {
			this.ordered = false;
		}
	}
	
	@Override
	public String getType(){
		return "MultiAnswer";
	}
	
	@Override
	public String rawQuestion(){
		return this.question;
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
		
		for (int i = 0; i < this.numAnswers; i++) {
			//buff.append("<input type=\"text\" class=\"form-control\" name=\""+ this.num +" " + i + "\"><br>");
			int index = i + 1;
			buff.append("<div class=input-group input-group-lg>");
			buff.append("<span class=input-group-addon>"+ index + ". </span>");
			buff.append("<input type=text name=\""+ this.num +" " + i + "\" class=\"form-control\">");
			buff.append("</div>");
		}
		buff.append("<input type=\"hidden\" name=\"numAnswers"+this.num+"\" value=\""+this.numAnswers+"\"><br>");
		return buff.toString();
	}
	
	/**
	 * displayQuestion returns a String of the HTML required to
	 * display the question and correct answer. This does NOT 
	 * include an image and the text input for the answer.
	 */
	@Override
	public String displayQuestion(int num) {
		StringBuilder buff = new StringBuilder();
		
		// Generate the HTML for the question
		String numStr = String.valueOf(num);
		buff.append("<p class=question>" + numStr + ". " + this.question + "</p>");	
		buff.append("<p class=answer> Answer is: " + this.displayAnswer() + "</p>");
		return buff.toString();
	}

	/**
	 * checkAnswer takes a String answer and returns true if the
	 * answer is correct, false otherwise.
	 */
	@Override
	public boolean checkAnswer(String answer) {
		String[] partsCorrect = this.answer.split(", ");
		String[] partsAnswer = answer.split(", ");
		if (partsAnswer.length != numAnswers) {
			return false;
		}
		if (ordered) {
			for (int i = 0; i < partsAnswer.length; i++) {
				if (!partsCorrect[i].toLowerCase().equals(partsAnswer[i].toLowerCase())) {
					return false;
				}
			}
			return true;
		} else {
			for (int i = 0; i < numAnswers; i++) {
				for (int j = 0; j <= numAnswers; j++) {
					if (j == numAnswers){
						return false;
					}
					if (partsCorrect[i].toLowerCase().equals(partsAnswer[j].toLowerCase())) {
						break;
					}
				}
			}
			return true;
		}
	}
	
	private String displayAnswer() {
		String[] parts = this.answer.split(", ");
		StringBuilder buff = new StringBuilder();
		buff.append(parts[0]);
		for (int i = 1; i < this.numAnswers; i++) {
			buff.append(", ");
			buff.append(parts[i]);
		}
		
		return buff.toString();
	}

	/**
	 * getAnswer returns the correct answer as a String
	 */
	@Override
	public String getAnswer() {
		return this.answer;
	}

}

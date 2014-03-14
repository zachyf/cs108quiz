package quiz;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceMultipleAnswer extends Question {

	private String question, answer;
	private List<String> choices;
	private int num, numAnswers;
	
	/**
	 * Constructor for QuestionResponse
	 * @param question: A String of the question
	 * @param answer: A String of the answer
	 * @param num: An int for the number of the question in the quiz
	 */
	/**
	 * Constructor for MultipleChoice
	 * @param question: A String of the question
	 * @param choices: A list of choices for the answer
	 * @param answer: A String of the answer
	 * @param num: An int for the number of the question in the quiz
	 */
	public MultipleChoiceMultipleAnswer(String question, List<String> choices, String answer, int num) {
		this.question = question;
		this.answer = answer;
		this.choices = choices;
		this.num = num;
		
		String[] parts = answer.split(", ");
		this.numAnswers = parts.length;
	}
	
	public MultipleChoiceMultipleAnswer(String question, String answer, int num) {
		this.question = question;
		this.answer = answer;
		this.choices = new ArrayList<String>();
		//this.choices.add(answer);
		this.num = num;
	}
	
	public List<String> getChoices(){
		return this.choices;
	}
	
	public void addChoices(String choice){
		this.choices.add(choice);
	}
	
	@Override
	public String rawQuestion(){
		return this.question;
	}
	
	@Override
	public String getType(){
		return "MultipleChoiceMultipleAnswer";
	}
	
	@Override
	public int getNum(){
		return this.num;
	}
	
	/**
	 * getQuestion returns a String of the HTML required to display the question.
	 * This includes the question and the radio buttons for the answer choices.
	 */
	@Override
	public String getQuestion(int num) {
		StringBuilder buff = new StringBuilder();
		
		// Generate the HTML for the question
		String numStr = String.valueOf(num);
		buff.append("<p class=question>" + numStr + ". " + this.question + "</p>");
		for (int i = 0; i < choices.size(); i++) {
			String choice = choices.get(i);
			buff.append("<label class=\"checkbox\">");
			buff.append("<input type=\"checkbox\" name=\"check" + this.num + " " + i + "\" value=\"checkAnswer\">" + choice + "");
			buff.append("<input type=\"hidden\" name=\"choice" + this.num + " " + i +"\" value=" + choice + ">");
	        buff.append("</label>");
		}
		
		buff.append("<input type=\"hidden\" name=\"numChoices"+this.num+"\" value=\""+choices.size()+"\"><br>");
		
		return buff.toString();
	}
	
	/**
	 * getQuestion returns a String of the HTML required to display the question.
	 * This includes the question and the radio buttons for the answer choices.
	 */
	@Override
	public String displayQuestion(int num) {
		StringBuilder buff = new StringBuilder();
		
		// Generate the HTML for the question
		String numStr = String.valueOf(num);
		buff.append("<p class=question>" + numStr + ". " + this.question + "</p>");
		for (int i = 0; i < choices.size(); i++) {
			String choice = choices.get(i);
			buff.append("<label class=\"checkbox\">");
			buff.append("<input type=\"checkbox\" name=\"check" + this.num + " " + i + "\" value=\"checkAnswer\">" + choice + "");
			buff.append("<input type=\"hidden\" name=\"choice" + this.num + " " + i +"\" value=" + choice + ">");
	        buff.append("</label>");
		}
		buff.append("<p class=answer> Answer is: " + answer + "</p>");
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
		if (partsAnswer.length != partsCorrect.length) {
			return false;
		}
		int numAnswers = partsCorrect.length;
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
	
	/**
	 * getAnswer returns the correct answer as a String
	 */
	@Override
	public String getAnswer() {
		return this.answer;
	}
}
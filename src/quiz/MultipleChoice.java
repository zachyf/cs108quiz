package quiz;

import java.util.*;

public class MultipleChoice extends Question {
	
	private String question, answer;
	private List<String> choices;
	private int num;
	
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
	public MultipleChoice(String question, List<String> choices, String answer, int num) {
		this.question = question;
		this.answer = answer;
		this.choices = choices;
		this.num = num;
	}
	
	@Override
	public String rawQuestion(){
		return this.question;
	}
	
	@Override
	public String getType(){
		return "MultipleChoice";
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
	public String getQuestion() {
		StringBuilder buff = new StringBuilder();
		
		// Generate the HTML for the question
		String numStr = String.valueOf(num);
		buff.append("<p class=question>" + numStr + ". " + this.question + "</p>");
		buff.append("<ul class=answers>");	
		for (int i = 0; i < choices.size(); i++) {
			String choice = choices.get(i);
			buff.append("<input type=radio name=" + numStr + 
					" value=" + choice + ">" + choice + "<br>");
		}
		buff.append("</ul>");
		
		return buff.toString();
	}
	
	/**
	 * getQuestion returns a String of the HTML required to display the question.
	 * This includes the question and the radio buttons for the answer choices.
	 */
	@Override
	public String displayQuestion() {
		StringBuilder buff = new StringBuilder();
		
		// Generate the HTML for the question
		String numStr = String.valueOf(num);
		buff.append("<p class=question>" + numStr + ". " + this.question + "</p>");
		buff.append("<ul class=answers>");	
		for (int i = 0; i < choices.size(); i++) {
			String choice = choices.get(i);
			buff.append("<input type=radio name=" + numStr + 
					" value=" + choice + ">" + choice + "<br>");
		}
		buff.append("</ul>");
		buff.append("<p class=answer> Answer is: " + answer + "</p>");
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

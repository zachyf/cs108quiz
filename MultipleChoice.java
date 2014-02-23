package quiz;

import java.util.*;

public class MultipleChoice extends Question {
	
	private String question, answer;
	private List<String> choices;
	private int num;
	
	public MultipleChoice(String question, List<String> choices, String answer, int num) {
		this.question = question;
		this.answer = answer;
		this.choices = choices;
		this.num = num;
	}
	
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

	@Override
	public boolean checkAnswer(String answer) {
		return this.answer.equals(answer);
	}

	@Override
	public String getAnswer() {
		return this.answer;
	}


}

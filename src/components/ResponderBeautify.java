package components;

import components.interfaces.IReponderBeautify;

public class ResponderBeautify implements IReponderBeautify{

	@Override
	public String booleanAnswer(String rawAnswer) {
		int i = 0;
		if(affiramativeSet.contains(rawAnswer.toLowerCase()) == true) {
			int random = (int)(Math.random()*(positiveAnswerSet.size()));	
			for (String answer : positiveAnswerSet) {
				if(i == random)
					return answer;
				i++;
			}		
		} else if(negativeSet.contains(rawAnswer.toLowerCase()) == true) {
			int random = (int)(Math.random()*(negativeAnswerSet.size()));	
			for (String answer : negativeAnswerSet) {
				if(i == random)
					return answer;
				i++;
			}
		}
		return "I don't know how to answer this";
	}
	
	@Override
	public String formatString(String rawString) {
		String formattedString;
		
		formattedString = rawString.replace("_", " ");
		formattedString = formattedString.toLowerCase();
		
		return formattedString;
	}
	
}

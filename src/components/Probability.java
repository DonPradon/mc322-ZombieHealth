package components;

import java.util.HashMap;
import java.util.Map;

import components.interfaces.IProbability;

public class Probability implements IProbability{

	@Override
	public double guessProbability(HashMap<String, Integer> data) {
		int total = 0, maxOccurances = 0;
		
		for(int occurances: data.values()) {
			total += occurances;
		}
		for(int occurances: data.values()) {
			if(occurances > maxOccurances) maxOccurances = occurances;
		}
		return ((double)maxOccurances/total);
		
	}
	
	@Override
	public String bestGuess(HashMap<String, Integer> data) {
		int maxOccurances = 0;
		String bestGuess = "unknown";
		
		for(Map.Entry<String, Integer> map: data.entrySet()) {
			if(map.getValue() > maxOccurances) {
				maxOccurances = map.getValue();
				bestGuess = map.getKey();
			}
		}
		
		return bestGuess;
	}

}

package components.interfaces;

import java.util.HashMap;

public interface IProbability {
	public double guessProbability(HashMap<String, Integer> data);
	public String bestGuess(HashMap<String, Integer> data);
}
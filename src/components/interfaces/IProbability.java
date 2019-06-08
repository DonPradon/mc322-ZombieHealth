package components.interfaces;

import java.util.HashMap;

public interface IProbability {
	public HashMap<String, Double> guessProbability(HashMap<String, Integer> options);

}

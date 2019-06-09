package components.interfaces;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IReponderBeautify {

	HashSet<String> affiramativeSet = Stream.of("y","1", "yes", "sim", "v", "t", "true"
			).collect(Collectors.toCollection(HashSet::new));
	HashSet<String> negativeSet = Stream.of("n", "0", "no", "nao", "f", "false"
			).collect(Collectors.toCollection(HashSet::new));

	HashSet<String> positiveAnswerSet = Stream.of(
			"Yes, I think so.",
			"Yes",
			"Yes, is it bad?", 
			"Yes...", 
			"Yes, Since this morning I feel this way.",
			"Yes I do."
			).collect(Collectors.toCollection(HashSet::new));

	HashSet<String> negativeAnswerSet = Stream.of(
			"No, I don't think so.",
			"No...", 
			"No."
			).collect(Collectors.toCollection(HashSet::new));
	
	public String booleanAnswer(String rawAnswer);
	public String formatString(String rawString);
}

package components;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import components.interfaces.IDoctor;
import components.interfaces.IResponder;
import data.interfaces.ISmartDataProducer;
import data.interfaces.ITableProducer;

public class Doctor implements IDoctor{
	private IResponder responder;
	private ITableProducer producer;
	private ISmartDataProducer sProducer;
	
	@Override
	public void connect(IResponder responder) {
		this.responder = responder;		
	}


	@Override
	public void connect(ITableProducer producer) {
		this.producer = producer;
	}


	@Override
	public void connect(ISmartDataProducer sProducer) {
		this.sProducer = sProducer;
	}

	@Override
	public void startInterview() {
		//IDEA _ mais burro possivel: contruir o hashmap perguntando TUDO
		// tirar o campo diagonistico, comparar, dar o diagnotico
		ArrayList<HashMap<String, String>> instances = this.producer.requestInstances();
		String[] attributes = this.producer.requestAttributes();
		String[] symptons = Arrays.copyOfRange(attributes, 0, attributes.length-1);
		String diagnostic = "not sure";
		int questionAsked = 0;
		DecimalFormat df2 = new DecimalFormat("#.##");
		boolean oddCase = false; 

		//criar condicao para parar o loop
		while(calculateProbability() < 0.80 && questionAsked < (attributes.length-1)) {
			String question = sProducer.bestAttribute(symptons);	
			String response;
			// caso tenha duas doenças iguais
			if(question == null) {
				oddCase = true;
				break;
			}
			System.out.println("Doctor: Do you have " + question + "?");
			response = responder.ask(question);
			sProducer.removeHash(question, response);		
			questionAsked++;
		}
		
		diagnostic = bestGuess();
		boolean result = responder.finalAnswer(diagnostic);
		
		//case 2 or more have the same symptons with different diseases
		if (oddCase == true) {
			System.out.println("We are facing an odd case, your disease can be the following things: ");
			for(String disease : sProducer.outComeMap().keySet())
				System.out.println(disease);
			System.out.print("But my guess is: ");
		} else {
			System.out.print("U probably have: ");
		}
		
		System.out.println(diagnostic + " (probability: " + 
				df2.format(calculateProbability()*100) + "%)");
		System.out.println("Correct answer? " + ((result) ? "I'm right!" : "I'm wrong"));
	}
	private double calculateProbability() {
		int total = 0, maxOccurances = 0;
		
		for(int occurances: this.sProducer.outComeMap().values()) {
			total += occurances;
		}
		for(int occurances: this.sProducer.outComeMap().values()) {
			if(occurances > maxOccurances) maxOccurances = occurances;
		}
		return ((double)maxOccurances/total);
	}
	private String bestGuess() {
		int maxOccurances = 0;
		String bestGuess = "unknown";
		
		for(Map.Entry<String, Integer> map: this.sProducer.outComeMap().entrySet()) {
			if(map.getValue() > maxOccurances) {
				maxOccurances = map.getValue();
				bestGuess = map.getKey();
			}
		}
		
		return bestGuess;
	}

}

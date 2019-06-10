package components;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Arrays;
import components.interfaces.IDoctor;
import components.interfaces.IProbability;
import components.interfaces.IProbabilityGraph;
import components.interfaces.IReponderBeautify;
import components.interfaces.IResponder;
import data.interfaces.ISmartDataProducer;
import data.interfaces.ITableProducer;

public class Doctor implements IDoctor {
	private IResponder responder;
	private ITableProducer producer;
	private ISmartDataProducer sProducer;
	private IProbability calculator;
	private IReponderBeautify beautifier;
	private IProbabilityGraph graph;
	
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
	public void connect(IProbability calculator) {
		this.calculator = calculator;
		
	}

	@Override
	public void connect(IProbabilityGraph graph) {
		this.graph = graph;
		
	}
	
	@Override
	public void connect(IReponderBeautify beautifier) {
		this.beautifier = beautifier;		
	}
	
	@Override
	public void startInterview() {
		//checa se todas as dependencias foram setadas
		for (Field f : getClass().getDeclaredFields()) {
			try {
				if (f.get(this) == null) {
					System.out.println("Para o correto funcionamento do Doutor, conecte todos os seus componentes");
					System.out.println("componente " + f.getType().getSimpleName().toUpperCase() + " nao foi conectado corretamente");
					return;						
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		String[] attributes = this.producer.requestAttributes();
		String[] symptons = Arrays.copyOfRange(attributes, 0, attributes.length-1);
		String diagnostic = "not sure";
		int questionAsked = 0;
		DecimalFormat df2 = new DecimalFormat("#.##");
		boolean oddCase = false; 
		//Chance de acerto maior que x ou todas as perguntas possiveis
		while(calculator.guessProbability(sProducer.outComeMap()) < 0.80 
				&& questionAsked < (attributes.length)
			) {
			String question = sProducer.bestAttribute(symptons);	
			String response;
			// caso tenha duas doenças iguais
			if(question == null) {
				oddCase = true;
				break;
			}
			System.out.println("Doctor: Do you have " + beautifier.formatString(question) + "?");
			response = responder.ask(question);
			if(response != null) {
				System.out.println("Patient: " + beautifier.booleanAnswer(response));
				
				sProducer.removeHash(question, response);		
			
			}
			questionAsked++;
		}
		
		diagnostic = calculator.bestGuess(sProducer.outComeMap());
		boolean result = responder.finalAnswer(diagnostic);
		
		//case 2 or more have the same symptons with different diseases
		if (oddCase == true) {
			System.out.println("Doctor: We are facing an odd case, your disease can be the following things: ");
			for(String disease : sProducer.outComeMap().keySet())
				System.out.println(disease);
			System.out.print("Doctor: But my guess is: ");
		} else {
			System.out.print("Doctor: You probably have: ");
		}
		
		System.out.println(beautifier.formatString(diagnostic) + " (probability: " + 
				df2.format((calculator.guessProbability(sProducer.outComeMap()))*100) + "%)");
		System.out.println("Correct answer? " + ((result) ? "I'm right!" : "I'm wrong"));
	}
}

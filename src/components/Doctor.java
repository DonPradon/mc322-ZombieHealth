package components;

import java.util.ArrayList;
import java.util.HashMap;

import components.interfaces.IResponder;
import data.interfaces.ITableProducer;

public class Doctor implements IDoctor{
	private IResponder responder;
	private ITableProducer producer;
	
	@Override
	public void connect(IResponder responder) {
		this.responder = responder;		
	}


	@Override
	public void connect(ITableProducer producer) {
		this.producer = producer;
	}
	
	@Override
	public void startInterview() {
		//IDEA _ mais burro possivel: contruir o hashmap perguntando TUDO
		// tirar o campo diagonistico, comparar, dar o diagnotico
		ArrayList<HashMap<String, String>> instances = this.producer.requestInstances();
		String[] attributes = this.producer.requestAttributes();
		String diagnostic = "not sure";
		boolean found = true;
		
		HashMap patientPreview = new HashMap();
		if (responder != null) {
			int i;
			for(i = 0;i<attributes.length-1; i++) {
				patientPreview.put(attributes[i],this.responder.ask(attributes[i]));
			}
			HashMap tempPatient = null;
			
			//itera sobre todos os hashmaps
			for(i = 0; i<instances.size();i++) {
				found = true;
				tempPatient = new HashMap(instances.get(i));
				tempPatient.remove(attributes[attributes.length-1]);
				//itera sobre as propriedades do hashmap
				for(int j = 0; j<attributes.length-1; j++) {
					if(patientPreview.get(attributes[j])!= tempPatient.get(attributes[j])) {
						found = false;
					}
				}
				if (found == true) {
					diagnostic = (String)instances.get(i).get(attributes[attributes.length-1]);
				}
			}
			System.out.println("Responder has: " + diagnostic);
			//teste
		} else {
			System.out.println("I Can't start an interview without a Responder :(");
		}
		boolean result = responder.finalAnswer(diagnostic);
		System.out.println("Correct answer? " + ((result) ? "I'm right!" : "I'm wrong"));
	}


}

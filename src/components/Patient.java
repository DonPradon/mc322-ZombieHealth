package components;

import java.util.HashMap;

import components.interfaces.IPatient;
import data.interfaces.ITableProducer;

public class Patient implements IPatient{
	
	private HashMap<String, String> patientMap;
	private String diagnosticKey;
	
	@Override
	public String ask(String question) {

		String response = null;
		
		if (patientMap.get(question) != null) {
			response = (String) (patientMap.get(question));
		}
		return response;
	}

	@Override
	public boolean finalAnswer(String answer) {
		if (answer.equalsIgnoreCase((String) patientMap.get(diagnosticKey))) {
			return true;
		}		
		return false;
	}

	@Override
	public void connect(ITableProducer producer) {
		int numPatient;
		//gera um numero aleatorio
		numPatient = (int)(Math.random()*(producer.requestInstances().size()));
		//seleciona o paciente
		this.patientMap = producer.requestInstances().get(numPatient);
		//salva a chave que contem o diagnotisico (ultima da tabela)
		this.diagnosticKey = producer.requestAttributes()[producer.requestAttributes().length-1];
	}

}

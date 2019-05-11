package components;

import java.util.HashMap;

import data.interfaces.ITableProducer;

public class Patient implements IPatient{
	
	private HashMap patientMap;
	private String diagnosticKey;
	
	@Override
	public String ask(String question) {
		//TODO responder mais coisas alem de sim e nao
		// lista de opcoes? nao so checar t!
		// tratar o hashmap para conter a resposta direto
		String response = "unknwon";
		
		if (patientMap.get(question) != null) {
			response = (patientMap.get(question).equals(("t")) ? "yes" : "no");
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

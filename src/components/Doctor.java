package components;

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
		String[] attributes = this.producer.requestAttributes();
		String diagnotic = null;
		
		if (responder != null) {
			for(int i = 0;i<attributes.length; i++) {
				this.responder.ask(attributes[i]);
			}
		} else {
			System.out.println("I Can't start an interview without a Responder :(");
		}
	}


}

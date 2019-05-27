package data;

import java.util.HashMap;

import data.interfaces.ITableProducer;
import data.interfaces.ITableProducerReceptacle;

public class DeciderComponent implements ITableProducerReceptacle{
	private HashMap<String, AttributeComponent> data;
	private ITableProducer producer;
	
	public void connect(ITableProducer producer) {
		this.producer = producer;
	}
	public void connect() {
		
	}
	public void createSmartData() {
		if (this.producer!=null) {
			
			
		} else System.out.println("Define the data u want to get smarter first");
	}
}

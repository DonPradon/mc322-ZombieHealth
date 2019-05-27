package data;

import java.util.HashMap;

public class AttributeComponent {
	private String nome;
	private int count;
	private HashMap<String, Integer> outcome;
	//entropy?
	//conect com quem?

	public String getNome() {
		return this.nome;
	}
	
	public int getCount() {
		return this.count;
	}

	public boolean compareAtt(String nome) {
		if(this.nome.equals(nome)) {
			return true;
		} return false;
	}
	
	
	
}

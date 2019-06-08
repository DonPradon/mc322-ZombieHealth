package data.interfaces;

import java.util.HashMap;

public interface ISmartDataProducer extends ITableProducerReceptacle, IMath{
	/*
	 * RECEBE:  atributo que deseja calcular a info gain
	 * RETORNA: entropia do atributo
	 */
	public double entropyCalculation(String attribute);
	/* 
	 * RECEBE: lista de atributos
	 * RETORNA: atributo com a maior entropia (maior ganho de info);
	 */
	public String bestAttribute(String[] attributes);
	
	/*
	 * Função que remove os hash com qualquer valor diferente do passado para aquela chave.
	 * 
	 *  RECEBE: chave e valor que vai ser analisada.
	 */
	public void removeHash(String key, String value);
	
	public HashMap<String, Integer> outComeMap();
}

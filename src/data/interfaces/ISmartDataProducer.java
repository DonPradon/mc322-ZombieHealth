package data.interfaces;

import java.util.HashMap;

public interface ISmartDataProducer extends ITableProducerReceptacle, IMath{
	/*
	 * RECEBE:  atributo á ser analisado, condicao do atributo a ser analisada(v ou f)
	 * RETORNA: resultados com o num de ocorrencias da tabela 
	 *
	 *private HashMap<String, Integer> outCome(String condition, boolean hasCondition);
	 *
	 * 
	 * RECEBE:  Possiveis saidas e suas quantidades para e v ou f daquele aquele atributo
	 * RETORNA:	entropia do atributo que gerou as saidas
	 *
	 *private double parcialEntropyCalculation(HashMap<String, Integer> outcomesCond);
	 *
	 *
	 * RECEBE: Nome do atributo que deve ser invocado 
	 */
	public double entropyCalculation(String attribute);
}

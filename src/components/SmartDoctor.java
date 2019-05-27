package components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import data.interfaces.ISmartDataProducer;
import data.interfaces.ITableProducer;

public class SmartDoctor implements ISmartDataProducer{
	private String outcomeKey;	
	private ArrayList<HashMap<String,String>> dataMap;
	
	@Override
	public void connect(ITableProducer producer) {
		String[] attributes = producer.requestAttributes();
		outcomeKey = attributes[attributes.length-1];
		dataMap = producer.requestInstances();
		
	}
	
	@Override
	public double entropyCalculation(String attribute) {
		HashMap<String, HashMap<String, Integer>> bla = new HashMap<String, HashMap<String,Integer>>();
		HashMap<String, HashMap<String, Integer>> outcomesMap = new HashMap<String, HashMap<String,Integer>>();
		HashMap<String, Integer> fixedMap = new HashMap<String, Integer>();
		double entropy = 0;
		bla = outCome(attribute);
		outcomesMap = outCome(this.outcomeKey);
		for(HashMap<String, Integer> map : outcomesMap.values()) {
			fixedMap.putAll(map);
		}
		for(HashMap<String, Integer> map: bla.values()) {
			entropy -=parcialEntropyCalculation(map);
		}
		return entropy;
	}
	private HashMap<String, HashMap<String,Integer>> outCome(String attribute) {
		HashSet<String> attributeValues = new HashSet<String>();
		HashMap<String, Integer> outcomesCount = null;
		HashMap<String, HashMap<String, Integer>> attributeMap = new HashMap<String, HashMap<String, Integer>>();
		//cria hashset de todas as possibilidades do atributo
		for (HashMap<String, String> entry : this.dataMap) {
			if(entry.containsKey(attribute)) attributeValues.add(entry.get(attribute));
		}
		/*
		 * Loop que olha todas as possibilidades do atributo a ser anallisado (t/f) || (tdas os outcomes possiveis)
		 * para cada possibilidade conta quantas vezes cada saída foi geradas.
		 */
		//itera sobre as possibilidades dos atributos: (v/f) || (possiveis doencas)
		for(String value: attributeValues) {
			outcomesCount = new HashMap<String, Integer>();
			//itera sobre cada linha do csv, p/ olhar a coluna do atributo analisado
			for(HashMap<String, String> mapLine: this.dataMap) {
				//expande o mapa de ocorrencia de diagnostico p aquele valor do atributo
				if(mapLine.get(attribute).equalsIgnoreCase(value)) {
					String outCome = mapLine.get(outcomeKey);
					//primeira vez que a doenca aparece no mapa
					if(!outcomesCount.containsKey(outCome)) {
						outcomesCount.put(outCome, 1);
					} else {
						int count = outcomesCount.get(outCome);
						count+=1;
						outcomesCount.put(outCome, count);
					}
				}
			}
			attributeMap.put(value, outcomesCount);
		}
		return attributeMap;
	}

	private double parcialEntropyCalculation(HashMap<String, Integer> outcomesCond) {
		double weight = 0, pEntropy = 0, totalcase = 0;
		//pega total de outcomes analisados para cada cenario
		for(int occurances1: outcomesCond.values()) totalcase += occurances1;

		//divide cada cenario pelo total de dados analisados
		weight = totalcase / dataMap.size();
		
		
		//calcula a entropia do atributo
		for(int occurances1 : outcomesCond.values()) {
			pEntropy -= pLog2((double)(occurances1/totalcase));
		}

		double res1 = weight * pEntropy;
		return weight * pEntropy;
	}

	public double pLog2(double p) {
		double value = 0;
		
		if (p!=0) 
			value = p *( Math.log(p) / Math.log(2));
		return value;
	}

}

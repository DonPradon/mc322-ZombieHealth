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
	public String bestAttribute(String[] attributes) {
		double highestEntropy = 0;
		String bestAttribute = null;
		for(String attribute: attributes) {
			double entropyAtt = entropyCalculation(attribute);
			if (entropyAtt > highestEntropy) {
				highestEntropy = entropyAtt;
				bestAttribute = attribute;
			}
		}	
		return bestAttribute;
	}
	
	@Override
	public void removeHash(String key, String value) {
		for(int i = 0;  i < this.dataMap.size() ; i++) {
			HashMap<String, String> map = this.dataMap.get(i);
			if(map.containsKey(key)) {
				if(map.get(key).equalsIgnoreCase(value) == false) {
					this.dataMap.remove(i);
					i--;				
				}
			}
		}
	}
	
	@Override
	public double entropyCalculation(String attribute) {
		HashMap<String, HashMap<String, Integer>> attributesOutComeMap = new HashMap<String, HashMap<String,Integer>>();
		HashMap<String, Integer> fixedMap = outComeMap();
		
		double entropy = 0;
		
		attributesOutComeMap = attributeOutComeMap(attribute);
	
		for(HashMap<String, Integer> map: attributesOutComeMap.values()) {
			entropy -=parcialEntropyCalculation(map);
		}
		
		entropy += parcialEntropyCalculation(fixedMap);
		
		return entropy;
	}
	
	public HashMap<String, Integer> outComeMap() {
		HashMap<String, Integer> fixedMap = new HashMap<String, Integer>();
		HashMap<String, HashMap<String, Integer>> outcomesMap = new HashMap<String, HashMap<String,Integer>>();	
		outcomesMap = attributeOutComeMap(this.outcomeKey);
		for(HashMap<String, Integer> map : outcomesMap.values()) {
			fixedMap.putAll(map);
		}
		return fixedMap;
	}
	
	private HashMap<String, HashMap<String,Integer>> attributeOutComeMap(String attribute) {
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

		return weight * pEntropy;
	}

	public double pLog2(double p) {
		double value = 0;
		
		if (p!=0) 
			value = p *( Math.log(p) / Math.log(2));
		return value;
	}

}

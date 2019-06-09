package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

import data.interfaces.IChoose;
import data.interfaces.IDataSource;
import data.interfaces.ITableProducer;


public class DataSetComponent implements IDataSource, ITableProducer, IChoose{
	
	private String dataSource;
	private String[] attributes;
	private ArrayList<HashMap<String, String>> instances;
	private Scanner input;
	
	@Override
	public void chooseFile() {
		String filePath = "";
		boolean validFile = false;
		input = new Scanner(System.in);
		//caminho para a pasta das tabelas
		String absolutePath = "./src/data/csv_tables";
		
		System.out.println("Choose the disease data file you want to consult:");
		System.out.println("================");
		try {
			//lista as opcoes para o usuario
			Stream<Path> files = Files.list(Paths.get(absolutePath + ""));
			files.forEach(entry -> {
	
				System.out.println(entry.getFileName());
				
			});
			System.out.println("================");
			files.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//espera a escolha do usuario
		System.out.println("Type the file name: ");
		while(validFile!=true) {
			String fileName = input.nextLine();
			filePath = absolutePath + "/" + fileName;
			File choosenFile = new File(filePath);
			validFile = choosenFile.exists();
			if(validFile == false) {
				System.out.println("File not found");
			}
		}
		setDataSource(filePath);
	}
	@Override
	public String[] requestAttributes() {
		
		return this.attributes;
	}

	@Override
	public ArrayList<HashMap<String, String>> requestInstances() {
		return this.instances;
	}

	@Override
	public String getDataSource() {
		return this.dataSource;
	}

	@Override
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
		if (dataSource == null) {
			attributes = null;
			instances = null;
		} else
	    	readDS();	
	}

	private void readDS() {
		
		HashMap<String,String> lineMap = null;
		ArrayList<HashMap<String, String>> instArray = new ArrayList<HashMap<String, String>>();
		try {
			BufferedReader file = new BufferedReader(new FileReader(dataSource));
			String line = file.readLine();
			if (line != null) {
				//pega primeira linha e separa em atributos
				this.attributes = line.split(",");
				line = file.readLine();
				while (line != null) {
					//cria o hashMap de 1 linha
					lineMap = new HashMap<String,String>();
					String[] instLine = line.split(",");
					//preenche o Hash a linha
					for(int i=0 ; i<this.attributes.length;i++) {
						lineMap.put(this.attributes[i], instLine[i]);
					}
					//adiciona no array
					instArray.add(lineMap);
					line = file.readLine();
				}
				this.instances = instArray;
			}
			file.close();
		} catch (IOException erro) {
			erro.printStackTrace();
		}
	}
}

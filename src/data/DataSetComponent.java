package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import data.interfaces.IChoose;
import data.interfaces.IDataSource;
import data.interfaces.ITableProducer;



public class DataSetComponent implements IDataSource, ITableProducer, IChoose{
	
	private String dataSource;
	private String[] attributes;
	private String[][] instances;
	
	@Override
	public void chooseFile() {
		String filePath = "";
		boolean validFile = false;
		//sacanner
		Scanner input = new Scanner(System.in);
		//caminho para a pasta das tabelas
		String absolutePath = "./src/data/csv_tables";
		
		System.out.println("Escolha o banco de doenças que deseja consultar:");
		try {
			//lista as opcoes para o usuario
			Stream<Path> files = Files.list(Paths.get(absolutePath + ""));
			files.forEach(entry -> {
	
				System.out.println(entry.getFileName());
				
			});
			files.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//espera a escolha do usuario
		System.out.println("Digite o nome do arquivo: ");
		while(validFile!=true) {
			String fileName = input.nextLine();
			filePath = absolutePath + "/" + fileName;
			File choosenFile = new File(filePath);
			validFile = choosenFile.exists();
			if(validFile == false) {
				System.out.println("Arquivo nao encontrado");
			}
		}
		setDataSource(filePath);
	}
	@Override
	public String[] requestAttributes() {
		
		return this.attributes;
	}

	@Override
	public String[][] requestInstances() {
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
		ArrayList<String[]> instArray = new ArrayList<String[]>();
		try {
			BufferedReader file = new BufferedReader(new FileReader(dataSource));

			String line = file.readLine();
			if (line != null) {
				attributes = line.split(",");
				line = file.readLine();
				while (line != null) {
					String[] instLine = line.split(",");
					instArray.add(instLine);
					line = file.readLine();
				}
				instances = instArray.toArray(new String[0][]);
			}
			file.close();
		} catch (IOException erro) {
			erro.printStackTrace();
		}
	}
}

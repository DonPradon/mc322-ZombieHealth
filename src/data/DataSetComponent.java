package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import data.interfaces.IDataSource;
import data.interfaces.ITableProducer;



public class DataSetComponent implements IDataSource, ITableProducer{
	
	private String dataSource;
	private String[] attributes;
	private String[][] instances;
	
	
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

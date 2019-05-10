package data.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

public interface ITableProducer {
	public String[] requestAttributes();
	public ArrayList<HashMap> requestInstances();
}

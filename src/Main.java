import components.Doctor;
import components.Patient;
import data.DataSetComponent;

public class Main {

	public static void main(String[] args) {
		//cria o componente de dados
		DataSetComponent dataset = new DataSetComponent();
				
		dataset.setDataSource("src/data/csv_tables/example.csv");
		
		
		Doctor doctor = new Doctor();
		Patient patient = new Patient();
		
		//conecta paciente a matriz de dados
		patient.connect(dataset);
		//conecta doutor a matriz de dados
		doctor.connect(dataset);
		//conecta doutor ao paciente
		doctor.connect(patient);
		// roda progama
		doctor.startInterview();
		
	}

}

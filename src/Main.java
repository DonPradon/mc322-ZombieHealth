import java.util.HashMap;

import components.Doctor;
import components.Patient;
import components.SmartDoctor;
import data.DataSetComponent;

public class Main {

	public static void main(String[] args) {
		//cria o componente de dados
		DataSetComponent dataset = new DataSetComponent();
		dataset.chooseFile();
		HashMap<String, Integer> d1, d2;
		SmartDoctor sDoctor = new SmartDoctor();
		Doctor doctor = new Doctor();
		Patient patient = new Patient();
		
		sDoctor.connect(dataset);
		//d1 = sDoctor.outCome("paralysis");
		//d2 = sDoctor.outCome("paralysis", true);
		//sDoctor.parcialEntropyCalculation(d1);
		//sDoctor.parcialEntropyCalculation(d2);
		//conecta paciente a matriz de dados
		patient.connect(dataset);
		//conecta doutor a matriz de dados
		doctor.connect(dataset);
		//conecta doutor ao paciente
		doctor.connect(patient);
		//conecta smartpdocuer
		doctor.connect(sDoctor);
		// roda progama
		doctor.startInterview();
		
	}

}

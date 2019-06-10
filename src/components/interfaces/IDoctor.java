package components.interfaces;

import data.interfaces.ISmartDataProducerReceptacle;
import data.interfaces.ITableProducerReceptacle;

public interface IDoctor extends IEnquirer, IResponderReceptacle, ITableProducerReceptacle, 
								ISmartDataProducerReceptacle, IProbabilityReceptacle, IResponderBeautifyReceptacle, IProbabilityGraphReceptacle {

}

package components;

import components.interfaces.IEnquirer;
import components.interfaces.IResponderReceptacle;
import data.interfaces.ISmartDataProducerReceptacle;
import data.interfaces.ITableProducerReceptacle;

public interface IDoctor extends IEnquirer, IResponderReceptacle, ITableProducerReceptacle, ISmartDataProducerReceptacle{

}

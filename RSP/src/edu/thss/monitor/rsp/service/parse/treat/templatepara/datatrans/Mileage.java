package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

public class Mileage  implements IDataTrans{
	
	//ClassisType为3，SensorType为3，JBC108TYPE为4
	@Override
	public String trans(String value) throws RSPException {
		// TODO Auto-generated method stub
		Unsign32Int unsign32Int = new Unsign32Int();
		String postValue = unsign32Int.trans(value);
		Float result = Float.parseFloat(postValue) * 200;
		return result.toString();
		
	}

}

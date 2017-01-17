package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

public class DoubleSub40 implements IDataTrans{

	@Override
	public String trans(String value) throws RSPException {
		double result = Double.parseDouble(value);
		result -= 40.0;
		return String.format("%.3f",result);
		//return String.valueOf(result);
	}

}
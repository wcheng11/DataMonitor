package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

public class ASCIIString2Double implements IDataTrans{

	@Override
	public String trans(String value) throws RSPException {
		// TODO Auto-generated method stub
		String result = null;
		try {
			//result =(new Double(Double.parseDouble(value))).toString();
			result = String.format("%.3f",Double.parseDouble(value));
		} catch (NumberFormatException e) {
			result = value;
		}
		return result;
	}

}

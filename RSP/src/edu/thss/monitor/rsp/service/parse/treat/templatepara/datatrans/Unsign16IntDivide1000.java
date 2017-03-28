package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

public class Unsign16IntDivide1000 implements IDataTrans {

	@Override
	public String trans(String value) {

		String sub1 = value.substring(0, 8);
		String sub2 = value.substring(8);
		String str = sub1 + sub2;		
	
		int result = Integer.parseInt(str, 2);
		double result2 = (double)result / 1000.0;
		return String.format("%.2f",result2);
		//return String.valueOf(result2);
	}

}

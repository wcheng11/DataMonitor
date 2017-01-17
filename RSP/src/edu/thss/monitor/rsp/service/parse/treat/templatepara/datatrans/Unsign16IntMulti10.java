package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

public class Unsign16IntMulti10 implements IDataTrans {

	@Override
	public String trans(String value) {

		String sub1 = value.substring(0, 8);
		String sub2 = value.substring(8);
		String str = sub1 + sub2;		
	
		int result = Integer.parseInt(str, 2);
		long result2 = (long)result * 10;
		return String.format("%d",result2);
		//return String.valueOf(result2);
	}

}

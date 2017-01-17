package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

public class Unsign8Int implements IDataTrans {

	@Override
	public String trans(String value) {

		return String.valueOf(Integer.parseInt(value, 2));
	}

}

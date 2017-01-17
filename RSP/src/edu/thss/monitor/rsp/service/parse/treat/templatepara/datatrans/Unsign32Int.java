package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import org.junit.Test;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 32位无符号整数转换
 * 
 * @author zhuangxy 2013-1-18
 */
public class Unsign32Int implements IDataTrans {

	@Override
	public String trans(String value) {

		String sub1 = value.substring(0, 8);
		String sub2 = value.substring(8, 16);
		String sub3 = value.substring(16, 24);
		String sub4 = value.substring(24);

		String str = sub1 + sub2 + sub3 + sub4;
		
		long result = Long.parseLong(str, 2);
		
		return String.valueOf(result);
	}

}

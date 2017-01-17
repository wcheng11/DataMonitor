package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 32位无符号整数转换后除10（用于博创协议）
 * @author CaoYuan
 * 2014-3-24
 */
public class Unsign32IntDivede10 implements IDataTrans{
	@Override
	public String trans(String value) {

		String sub1 = value.substring(0, 8);
		String sub2 = value.substring(8, 16);
		String sub3 = value.substring(16, 24);
		String sub4 = value.substring(24);

		String str = sub4 + sub3 + sub2 + sub1;
		
		long result = Long.parseLong(str, 2);
		double result2 = (double)result / 10.0;
		return String.format("%.3f",result2);
		//return String.valueOf(result2);
	}
}

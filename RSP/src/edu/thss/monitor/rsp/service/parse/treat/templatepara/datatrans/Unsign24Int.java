package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 24位无符号整数转换,版本号信息
 * @author wen
 * 2013-1-17
 */
public class Unsign24Int implements IDataTrans{

	/**
	 * 版本号：前16位为协议版本号，后1位为程序版本号
	 */
	@Override
	public String trans(String value) {
		
		String sub1 = value.substring(0, 8);
		String sub2 = value.substring(8,16);
		String sub3 = value.substring(16);	
		String str = sub1 + sub2;
		int result1 = Integer.parseInt(str, 2);
		
		String str1 = sub3;
		int result2 = Integer.parseInt(str1, 2);
		
		int result3=result1*1000+result2;
						
		return String.valueOf(result3);
		
	}

}

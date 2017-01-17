package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 12位无符号整数转换（用于博创协议）
 * @author CaoYuan
 * 2014-3-24
 */
public class Unsign14Int implements IDataTrans{

	/**
	 * 12位无符号整数转换
	 * 十六进制，低字节在前，高字节在后。例351，解析成0135
	 */
	@Override
	public String trans(String value) {
		
		String sub2 = "00" + value.substring(0,6);
		String sub1 = value.substring(6);		
		
		String str = sub2 + sub1;
		
		int result = Integer.parseInt(str, 2);
		
		return String.valueOf(result);
		
	}

}

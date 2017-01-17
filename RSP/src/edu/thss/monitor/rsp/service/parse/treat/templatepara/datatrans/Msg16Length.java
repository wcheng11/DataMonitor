package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 16位无符号整数转换
 * @author zhuangxy
 * 2013-1-17
 */
public class Msg16Length implements IDataTrans{

	/**
	 * 16位无符号整数转换, 8位高字节在前，8为低字节在后
	 * 即，输入数据：LLLLLLLLHHHHHHHH
	 */
	@Override
	public String trans(String value) {
		
		String sub1 = value.substring(0, 8);
		String sub2 = value.substring(8);
		String str = sub1 + sub2;
		
		int result = Integer.parseInt(str, 2);
		
		return String.valueOf(result);
		
	}

}

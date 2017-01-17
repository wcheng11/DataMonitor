package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 16位无符号整数转换
 * @author zhuangxy
 * 2013-1-17
 */
public class Unsign16Int implements IDataTrans{

	/**
	 * 16位无符号整数转换, 8位高字节在前，8为低字节在后
	 * 即，输入数据：HHHHHHHHLLLLLLLL
	 */
	@Override
	public String trans(String value) {
		
		String sub1 = value.substring(value.length()-8);
		System.out.print("sub1的值为"+sub1);
		String sub2 = value.substring(0,value.length()-8);
		System.out.print("sub2的值为"+sub2);
		String str = sub2 + sub1;
		System.out.print("sub1+sub2的值为"+str);
		
		int result = Integer.parseInt(str, 2);
		
		return String.valueOf(result);
		
	}

}

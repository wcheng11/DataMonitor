package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 16位无符号整形处10000
 * @author wen
 *
 */
public class Us16Div1W implements IDataTrans{

	@Override
	public String trans(String value) throws RSPException {
		
		System.out.print("开始Unsign16IntDivide10000");
		String sub1 = value.substring(0, 8);
		String sub2 = value.substring(8);
		String str = sub1 + sub2;		
		System.out.print("str的值为"+str);
		
		int result = Integer.parseInt(str, 2);
		double result2 = (double)result / 10000.0;
		System.out.print(String.format("%.4f",result2));
		return String.format("%.4f",result2);			
	}
}
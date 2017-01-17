package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;
/**
 * 16进制ASCII字符串交换高地位，转成16进制数字
 * @author CaoYuan
 *
 */
public class ASCIIChar2Hex implements IDataTrans{

	@Override
	public String trans(String value) throws RSPException {
		String hexStr = value.substring(2,3)+ value.substring(0,2);
		int re = Integer.parseInt(hexStr, 16);
		String result = String.valueOf(re);
		
		return result;
	}
//	public static void main(String args[]) throws RSPException {
//		ASCIIChar2Hex a = new ASCIIChar2Hex();
//		a.trans("D20");
//	}

}

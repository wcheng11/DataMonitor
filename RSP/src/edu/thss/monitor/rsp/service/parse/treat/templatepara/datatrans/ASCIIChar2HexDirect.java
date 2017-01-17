package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans ;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 16进制ASCII字符串转成16进制数字
 * @author CaoYuan
 *
 */
public class ASCIIChar2HexDirect implements IDataTrans{

	@Override
	public String trans(String value) throws RSPException {
		//String hexStr = value.substring(2,3)+ value.substring(0,2);
		String temp = "";
		for (int i = 0; i+2 <= value.length(); i = i+2) {
			temp = value.substring(i, i+2)+temp;
		}
		

		int re = Integer.parseInt(temp, 16);
		String result = String.valueOf(re);


		return result;
	}
//	public static void main(String args[]) throws RSPException {
//		ASCIIChar2HexDirect test = new ASCIIChar2HexDirect();
//		test.trans("D4040000");
//	}

}

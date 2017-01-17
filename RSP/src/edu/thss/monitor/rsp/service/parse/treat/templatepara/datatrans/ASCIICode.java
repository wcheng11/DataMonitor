package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 将二进制ASCII解析成字符串
 * @author CaoYuan
 *
 */
public class ASCIICode implements IDataTrans {

	@Override
	public String trans(String value) throws RSPException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; 8 * i < value.length(); i += 1) {
			if(8*(i+1) > value.length())
				break;
			int temp = Integer.parseInt(value.substring(8*i, 8*(i+1)), 2);
			sb.append((char)temp);
		}
		//System.out.println("!!!"+sb.toString());
		return sb.toString();
	}
	
//	public static void main(String args[]) throws RSPException {
//		ASCIICode a = new ASCIICode();
//		a.trans("443230");
//	}
	
}

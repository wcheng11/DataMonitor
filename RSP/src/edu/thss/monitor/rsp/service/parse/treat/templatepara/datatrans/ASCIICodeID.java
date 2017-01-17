package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 将二进制ASCII解析成字符串并反转高低位
 * 适用于博创协议中的ID号
 * @author CaoYuan
 *
 */
public class ASCIICodeID implements IDataTrans {

	@Override
	public String trans(String value) throws RSPException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; 8 * i < value.length(); i += 1) {
			if(8*(i+1) > value.length())
				break;
			int temp = Integer.parseInt(value.substring(8*i, 8*(i+1)), 2);
			sb.append((char)temp);
		}	
		String ss = sb.toString();
		String ret = ss.substring(6,8)+ss.substring(4,6)+ss.substring(2,4)+ss.substring(0,2);
		//System.out.println("!!!"+ret);
		return ret;
	}

}

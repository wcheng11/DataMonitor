package edu.thss.monitor.rsp.service.parse.treat.template.parse;

import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.template.ITemplatePretreat;
import edu.thss.monitor.rsp.service.parse.util.ByteConvertUtil;

public class BCHSwitchParser implements ITemplatePretreat{

	@Override
	public Object process(Object packetData, Template template,
			TemplatePara tempPara, String treatPara) throws RSPException {
		
		// 把byte[]类型的数据转化为二进制的String字段
		byte[] data = (byte[]) packetData;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			sb.append((char)data[i]);
		}
		String str = sb.toString();
		String str1 = str.substring(4,8)+str.substring(0,4);
		byte[] treatData = new byte[4];
		for (int i = 0; i < 4; i++) {
			int high = Integer.parseInt(str1.substring(i*2,i*2+1), 16);
			int low = Integer.parseInt(str1.substring(i*2+1,i*2+2), 16);
			treatData[i] = (byte) ((high<<4) + low);
		}
		return treatData;
	}
	
//	public static void main(String args[]) {
//		String ab = "1B003A04";
//		
//	}

	

}

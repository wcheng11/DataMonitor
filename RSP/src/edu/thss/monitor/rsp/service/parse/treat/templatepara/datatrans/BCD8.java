package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;
import edu.thss.monitor.rsp.service.parse.util.ByteConvertUtil;

public class BCD8 implements IDataTrans {

	@Override
	public String trans(String value) {
		
		byte[] b = new byte[1];
		b[0] = (byte) Integer.parseInt(value, 2);
		return ByteConvertUtil.bytesToHexString(b);
	}
	
}

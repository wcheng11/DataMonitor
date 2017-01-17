package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;
import edu.thss.monitor.rsp.service.parse.util.ByteConvertUtil;

public class BCD16 implements IDataTrans {

	@Override
	public String trans(String value) {
		
		byte[] b = new byte[2];
		b[0] = (byte) Integer.parseInt(value.substring(0, 8), 2);
		b[1] = (byte) Integer.parseInt(value.substring(8), 2);
		return ByteConvertUtil.bytesToHexString(b);
	}
	
}

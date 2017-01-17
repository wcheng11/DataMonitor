package edu.thss.monitor.rsp.service.parse.treat.templatepara.datatrans;

import java.text.DecimalFormat;

import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.templatepara.IDataTrans;

/**
 * 倾角数据转换
 * @author zhuangxy
 * 2013-1-30
 */
public class InclinationAngle implements IDataTrans{

	@Override
	public String trans(String value) throws RSPException {
		
		double pre;
		try {
			pre = Double.parseDouble(value);
		} catch (Exception e) {

			throw new RSPException("数据类型转换错误：无法将" + value + "转换为double。");
		}
		double result = 0;
		DecimalFormat decimalFormat = new DecimalFormat(".#");
		
		if (pre >= 0 && pre <= 15000) {
			result  = pre/1000.0;
			result =Double.parseDouble(decimalFormat.format(result)) ;
		} else if (pre >= 50536 && pre <= 65535){
		    
			result  = (pre - 65536)/1000.0;
			result =Double.parseDouble(decimalFormat.format(result)) ;
		} else {
			result = pre;
		}
		
		return String.valueOf(result);
	}

}


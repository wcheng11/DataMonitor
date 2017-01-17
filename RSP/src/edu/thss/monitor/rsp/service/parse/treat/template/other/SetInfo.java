package edu.thss.monitor.rsp.service.parse.treat.template.other;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.template.ITemplatePretreat;

/**
 * 组合信元解析预处理
 * @author zhuangxy
 *
 */
public class SetInfo implements ITemplatePretreat {

	@Override
	public Object process(Object packetData, Template template,
			TemplatePara tempPara, String treatPara) throws RSPException {

		int num = 0;
		try {
			num = Integer.parseInt(treatPara);
		} catch (Exception e) {

			LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "组合信元预处理出错：输入的处理参数" + treatPara + "格式错误。");
		}
		
		byte[] data = (byte[]) packetData;
		int length = num * 8 + 4;
		
		if (data.length != num * 12) {
			throw new RSPException("组合信元预处理出错：组合信元个数与数据长度不一致。");
		}
		
		byte[] treatData = new byte[length];
		System.arraycopy(data, 0, treatData, 0, 12);
		int index = 12;
		
		for(int i = 12; i < data.length; i += 12){
			
			System.arraycopy(data, i + 4, treatData, index, 8);
			index += 8;
		}
		
		return treatData;
	}

}

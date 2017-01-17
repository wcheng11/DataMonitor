package edu.thss.monitor.rsp.service.parse.treat.protocol.impl;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.protocol.ITemplateRecognizer;

/**
 * 唯一模板识别，对应于协议的模板只有一个，因此返回唯一的协议模板
 * @author zhuangxy
 *
 */
public class UniqueTemplateRecognizer implements ITemplateRecognizer {

	/**
	 * 识别的模板
	 */
	private Template template;
	
		
			
	@Override
	public Template getTemplate(Object packetData, String templateName)
			throws RSPException {

		LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "正在识别模板...");
		
		
		System.out.print("唯一模板识别---");	
		 
			

		template = (Template) RegionalRC.getResourceItem("template",
				templateName);
		
		if (template != null) {
			LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别完成，获取模板" + template.getTemplateID());
			System.out.print(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别完成，获取模板" + template.getTemplateID());	
			return template;
		} else {
			System.out.print(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别错误：无法获取协议模板" + templateName);
			throw new RSPException(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别错误：无法获取协议模板" + templateName);
		}

		
	}


}

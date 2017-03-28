package edu.thss.monitor.rsp.service.parse.treat.protocol.impl;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.protocol.ITemplateRecognizer;
import edu.thss.monitor.rsp.service.parse.util.ByteConvertUtil;

public class CTY870TempRecg implements ITemplateRecognizer {

	/**
	 * 日志记录
	 */
//	protected static Log logger = LogFactory.getLog(InfoUnitRecognizer.class);
	
//	protected static ApplicationContext context = AppContext.getSpringContext();
//	BaseDAO dao = (BaseDAO) context.getBean("baseDAO");

	private final static int REG_LENGTH = 4;

	/**
	 * 模板识别判断字段
	 */
	private byte[] templateReg;
	private byte InfotypeReg;	
	/**
	 * 识别的模板
	 */
	private Template template;

	@Override
	public Template getTemplate(Object pData, String regPara) throws RSPException {

		LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "正在识别模板...");
		byte[] packetData = (byte[]) pData;
//		templateReg = new byte[1]; // 4个字节

		//InfotypeReg = new byte[1]; // 1个字节
		InfotypeReg = packetData[11];	
		String templateName=String.format("%02x",InfotypeReg);
					
		template = (Template) RegionalRC.getResourceItem("template",
				templateName);
		
		
		if (template != null) {
			LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别完成，获取模板" + template.getTemplateID());
			return template;
		} else {
			throw new RSPException(LogConstant.LOG_FLAG_PROTOCOLRECONG, templateName, "协议模板识别错误：无法获取协议模板" + templateName);
		}
	}

}

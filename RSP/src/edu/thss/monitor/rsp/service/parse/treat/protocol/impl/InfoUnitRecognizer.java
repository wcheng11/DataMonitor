package edu.thss.monitor.rsp.service.parse.treat.protocol.impl;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.protocol.ITemplateRecognizer;
import edu.thss.monitor.rsp.service.parse.util.ByteConvertUtil;

public class InfoUnitRecognizer implements ITemplateRecognizer {

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

	/**
	 * 识别的模板
	 */
	private Template template;

	@Override
	public Template getTemplate(Object pData, String regPara) throws RSPException {

		LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "正在识别模板...");
		byte[] packetData = (byte[]) pData;
		templateReg = new byte[REG_LENGTH]; // 4个字节

		if (packetData.length < REG_LENGTH) {
			LogRecord.error(LogConstant.LOG_FLAG_PROTOCOLRECONG + "模板识别错误，未知数据段：数据长度不够！");
			return null;
		}

		// 获取报文的前4个字节的数值，高字节在后，低字节在前
		for (int i = 0; i < templateReg.length; i++) {
			templateReg[i] = packetData[REG_LENGTH - 1 - i];
		}
		// 数值转为String
		String templateName = ByteConvertUtil.bytesToHexString(templateReg);

		template = (Template) RegionalRC.getResourceItem("template",
				templateName);
		
//		Map attrMap = new HashMap();
//		attrMap.put("templateID", ("'"+templateName+"'"));
//		try {
//			template = (Template) dao.findByAttr(Template.class, attrMap)
//					.get(0);
//		} catch (Exception e) {
//			System.out.println("++++++++" +templateName);
//		}
		
		if (template != null) {
			LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别完成，获取模板" + template.getTemplateID());
			return template;
		} else {
			throw new RSPException(LogConstant.LOG_FLAG_PROTOCOLRECONG, templateName, "协议模板识别错误：无法获取协议模板" + templateName);
		}
	}

}

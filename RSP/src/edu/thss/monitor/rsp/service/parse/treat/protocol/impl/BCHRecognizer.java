package edu.thss.monitor.rsp.service.parse.treat.protocol.impl;

import java.io.UnsupportedEncodingException;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.protocol.ITemplateRecognizer;

public class BCHRecognizer implements ITemplateRecognizer {

	private Template template;
	
	@Override
	public Template getTemplate(Object pData, String regPara)
			throws RSPException {
		LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "正在识别模板...");
		
		byte[] packetData = (byte[]) pData;
		byte[] ID = new byte[8];
		for (int i = 0; i < 8; i++) {
			ID[i] = packetData[i+6];
		}
		
		String s = null;
		try {
			s = new String(ID, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		String templateName = null;
		if (s.equals("110037C2")) {
			templateName = "bchwj";
		} else if (s.equals("180037C2")) {
			templateName = "bchnj";
		} else if (s.equals("130037C2")) {
			templateName = "bchbc";
		} else {
			templateName = "bchwjreal";
		}
		
		
		template = (Template) RegionalRC.getResourceItem("template",templateName);
		
		if (template != null) {
			LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别完成，获取模板" + template.getTemplateID());
			return template;
		} else {
			throw new RSPException(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别错误：无法获取协议模板");
		}

		
	}

}

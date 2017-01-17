package edu.thss.monitor.rsp.service.parse.treat.template;

import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 模板解析接口
 * @author zhuangxy
 * 2013-1-16
 */
public interface ITemplateParser {

	/**
	 * 报文解析
	 * @param data 原始报文
	 * @param template 报文模板
	 * @return 解析后的报文数据
	 * @throws RSPException 
	 */
	public ParsedDataPacket parse(Object data, Template template, 
			String treatPara) throws RSPException;

}

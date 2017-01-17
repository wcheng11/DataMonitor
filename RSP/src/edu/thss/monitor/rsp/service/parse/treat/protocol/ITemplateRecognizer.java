package edu.thss.monitor.rsp.service.parse.treat.protocol;

import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 协议模板识别类接口
 * @author zhuangxy
 * 2013-1-8
 */
public interface ITemplateRecognizer {

	/**
	 * 获取解析模板
	 * @param packetData 未解析的报文数据
	 * @return 模板
	 * @throws RSPException
	 */
	public Template getTemplate(Object packetData, String regPara) throws RSPException;

	

}

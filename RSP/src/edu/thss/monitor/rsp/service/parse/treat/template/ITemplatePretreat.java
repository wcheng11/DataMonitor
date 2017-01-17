package edu.thss.monitor.rsp.service.parse.treat.template;

import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 模板预处理方案接口，适合使用的预处理方案包括：校验、解密、解压缩等。
 * @author zhuangxy
 * 2013-1-15
 */
public interface ITemplatePretreat {

	/**
	 * 进行模板预处理
	 * @param packetData 需要预处理的未解析的报文数据
	 * @param template 报文对应的解析模板
	 * @param tempPara 预处理相关的模板参数
	 * @param treatPara 预处理参数
	 * @return 预处理后的报文数据
	 * @throws RSPException 
	 */
	public Object process(Object packetData, Template template, TemplatePara tempPara,
			String treatPara) throws RSPException;

}

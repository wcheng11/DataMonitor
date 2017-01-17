package edu.thss.monitor.rsp.service.parse.process;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.ProTreatClass;
import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.protocol.ITemplateRecognizer;

/**
 * 协议模板识别
 * 
 * @author zhuangxy 2013-1-8
 */
public class TemplateRecognizer {

	/**
	 * 日志记录
	 */
//	protected static Log logger = LogFactory.getLog(TemplateRecognizer.class);

	/**
	 * 识别的模板
	 */
	private Template template;

	/**
	 * 模板识别接口
	 */
	private ITemplateRecognizer tempReg;

	/**
	 * 协议处理方案：模板识别处理方案
	 */
	private ProTreatClass ptc;

	/**
	 * 获取协议的模板
	 * 
	 * @param protocol
	 *            已识别的协议
	 * @param packetData
	 *            未解析的报文数据
	 * @return 识别的模板
	 * @throws RSPException
	 */
	public Template recognizeTemplate(Protocol protocol, Object packetData)
			throws RSPException {

		LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "正在获取协议模板识别方案...");
//		System.out.print(LogConstant.LOG_FLAG_PROTOCOLRECONG + "正在获取协议模板识别方案...");	
		
		ptc = protocol.getTemplateRecogClass();
		
		LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别方案:" + ptc.getClassName());
//		System.out.print(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别方案:" + ptc.getClassName());	
//		System.out.print("协议处理方案:"+ptc.getTreatName());	
				
		if (ptc != null) {
			LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "正在获取协议模板识别类...");

			//template = this.recognize(packetData, protocol.getTempRegParameter());
			template = this.recognize(packetData, "870timerpara110");
			return template;

		} else {
			throw new RSPException(LogConstant.LOG_FLAG_PROTOCOLRECONG, protocol.getProtocalName(), "协议模板识别错误：无法获取协议"
					+ protocol.getProtocalName() + "的识别方案");
		}

	}

	private Template recognize(Object packetData, String para) throws RSPException {
		try {
			String tempRegClass = ptc.getClassName();
			
//			System.out.print("模板识别类为"+tempRegClass);	
					
			tempReg = (ITemplateRecognizer) Class.forName(tempRegClass)
					.newInstance();
		} catch (InstantiationException e) {
			throw new RSPException(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别错误：无法初始化协议模板识别类" + ptc.getClassName());

		} catch (ClassNotFoundException e) {
			throw new RSPException(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别错误：无法找到协议的模板识别类" + ptc.getClassName());

		} catch (Exception e) {
			throw new RSPException(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别错误");
		}

		LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别类获取成功");
//		System.out.print(LogConstant.LOG_FLAG_PROTOCOLRECONG + "协议模板识别类获取成功");	
		
		template = tempReg.getTemplate(packetData, para);
//		System.out.print("识别的模板为"+template.getTemplateName());		
		return template;
	}

}

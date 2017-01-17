package edu.thss.monitor.rsp.service.parse.process;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.TempTreClass;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.treat.template.ITemplateParser;

/**
 * 模板解析
 * 
 * @author zhuangxy 2013-1-17
 */
public class TemplateParseProcessor {

	/**
	 * 日志记录
	 */
	// protected static Log logger =
	// LogFactory.getLog(TemplateParseProcesser.class);

	/**
	 * 解析后的报文
	 */
	private ParsedDataPacket parsedData;

	/**
	 * 模板解析接口
	 */
	private ITemplateParser templateParser;

	/**
	 * 模板解析
	 * 
	 * @param data
	 *            原始数据
	 * @param template
	 *            模板
	 * @return 解析后的数据
	 * @throws RSPException
	 */
	public ParsedDataPacket processParse(Object data, Template template)
			throws RSPException {

		LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "开始解析报文数据，报文模板："
				+ template.getTemplateName());
		
//		System.out.print(LogConstant.LOG_FLAG_PARSE + "开始解析报文数据，报文模板："
//				+ template.getTemplateName());	
		TempTreClass ttc = template.getParseClassName();

		try {
			// 加载模板解析类
			String className = ttc.getClassName();
			System.out.print("模板解析类名为："+className);
			
			
			templateParser = (ITemplateParser) Class.forName(className)
					.newInstance();
		} catch (InstantiationException e) {
			throw new RSPException(LogConstant.LOG_FLAG_PARSE, template
					.getTemplateName(), "模板解析类识别错误：无法初始化模板解析类"
					+ ttc.getClassName());

		} catch (ClassNotFoundException e) {
			throw new RSPException(LogConstant.LOG_FLAG_PARSE, template
					.getTemplateName(), "模板解析类识别错误：无法找到模板解析类"
					+ ttc.getClassName());

		} catch (Exception e) {
			throw new RSPException(LogConstant.LOG_FLAG_PARSE, template
					.getTemplateName(), "模板解析类识别错误");
		}

		parsedData = templateParser.parse(data, template, template
				.getParseParameter());
		return parsedData;
	}

	/*
	 * @Test public void testForName(){ try { ITemplateParser templateParser =
	 * (ITemplateParser)Class.forName(
	 * "edu.thss.monitor.rsp.service.parse.treat.template.parse.BinaryParser")
	 * .newInstance(); System.out.println(); } catch (InstantiationException e)
	 * { System.out.println("1"); e.printStackTrace(); } catch
	 * (IllegalAccessException e) { System.out.println("2");
	 * e.printStackTrace(); } catch (ClassNotFoundException e) {
	 * System.out.println("3"); e.printStackTrace(); } }
	 */
}

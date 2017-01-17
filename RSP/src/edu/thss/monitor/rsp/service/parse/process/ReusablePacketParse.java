package edu.thss.monitor.rsp.service.parse.process;

import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 可复用报文解析 实现了报文解析过程中会重复的步骤，包括： 步骤2：模板识别 步骤3：模板处理，包括校验、解密、解压缩等 步骤4：解析
 * 
 * @author zhuangxy 2013-1-17
 */
public class ReusablePacketParse {

	/**
	 * 解析类型：一次解析
	 */
	private static final int ONEC_PARSE = 1;

	/**
	 * 解析类型：循环解析
	 */
	private static final int RECURSIVE_PARSE = 2;

	/**
	 * 解析后的数据
	 */
	private ParsedDataPacket parsedData;

	private Protocol protocol;

	public ReusablePacketParse(Protocol p) {
		this.protocol = p;
		this.parsedData = new ParsedDataPacket();
	}

	public ReusablePacketParse() {
		this.parsedData = new ParsedDataPacket();
	}

	/**
	 * 
	 * 可复用解析步骤，包括： 步骤2：模板识别 步骤3：模板处理，包括校验、解密、解压缩等 步骤4：解析
	 * 
	 * @param protocol
	 *            原始报文的协议
	 * @param rawData
	 *            原始报文数据
	 * @return 解析后的原始报文
	 * @throws RSPException
	 */
	public ParsedDataPacket reusableParse(RawDataPacket rawData)
			throws RSPException {

		/**
		 * 解析类型包括一次解析和循环解析两种： 1. 一次解析仅对报文进行一次模板识别、预处理、模板解析 2.
		 * 循环解析若一次解析完成后，仍有剩余未解析报文，会重 复进行模板识别、预处理、解析流程
		 */
		int parseType = protocol.getParseMethod();
		Template template = new Template();
		

		if (parseType == ONEC_PARSE) { // 一次解析

//			System.out.print("识别解析次数-为1次--");		
			// 步骤2：模板识别
			template = this.getTemplate(rawData);
		
//			System.out.print("模板获取成功"+template.getTemplateName());	

			parsedData = this.onceParse(template, rawData.getPacketData());

			// 设置公有标识

		} else if (parseType == RECURSIVE_PARSE) { // 循环解析

			// TODO：如何解决二进制和SCAII码报文的不同长度识别
			// FIXME: 现在只能进行二进制报文的循环解析
			RawDataPacket raw = rawData;
			byte[] data = (byte[]) raw.getPacketData();
			byte[] data2;

			ParsedDataPacket result;

			while (data.length > 0) { // 还有未解析的报文

				// 步骤2：模板识别
				template = this.getTemplate(raw);

				if (template == null)
					break;

				int length = this.getDataLength(template);

				// 截取数据段
				byte[] subData = new byte[length];
				try {
					System.arraycopy(data, 0, subData, 0, length);
				} catch (Exception e) {
					throw new RSPException(LogConstant.LOG_FLAG_PARSE, template
							.getTemplateID(), "循环解析出错：剩余未知字段无法解析。模板："
							+ template.getTemplateID() + "; 模板长度："
							+ template.getFixedLength() + "; 数据长度"
							+ data.length);
				}

				result = this.onceParse(template, subData);

				data2 = new byte[data.length - length];
				System.arraycopy(data, length, data2, 0, data.length - length);
				data = data2;
				raw.setPacketData(data2);

				this.parsedData.getWorkStatusMap().putAll(
						result.getWorkStatusMap());

			}

		} else { // 解析方式定义错误
			throw new RSPException(LogConstant.LOG_FLAG_PARSE, protocol
					.getProtocalName(), "协议" + protocol.getProtocalName()
					+ "解析方式定义错误，未定义的协议解析方式");
		}

//		parsedData.setCommonKey(template.getCommonKey());
//		parsedData.setUniqueKey(template.getUniqueKey());

		return parsedData;
	}

	/**
	 * 模板识别
	 * 
	 * @param rawData
	 *            需要识别模板的原始报文
	 * @return 报文的模板
	 * @throws RSPException
	 */
	private Template getTemplate(RawDataPacket rawData) throws RSPException {
		// 步骤2：模板识别
//		System.out.print("准备获取模板--");	
		TemplateRecognizer tr = new TemplateRecognizer();
		Template template = tr.recognizeTemplate(protocol, rawData
				.getPacketData());
		
//		System.out.print("获取的模板为"+template.getTemplateID());	
		
		return template;
	}

	/**
	 * 获取原始报文数据长度
	 * 
	 * @param template
	 *            报文对应的模板
	 * @param rawData
	 *            原始数据
	 */
	private int getDataLength(Template template) {

		// FIXME: 添加从长度模板参数获取长度的判断
		return template.getFixedLength();
	}

	/**
	 * 一次解析
	 * 
	 * @param rawData
	 *            待解析的原始报文数据
	 * @return 解析后报文数据
	 * @throws RSPException
	 */
	private ParsedDataPacket onceParse(Template template, Object rawData)
			throws RSPException {

		// 步骤3：模板处理，包括校验、解密、解压缩等
		//TemplatePretreatProcessor tpp = new TemplatePretreatProcessor();
		//Object data = tpp.processTreat(rawData, template);

//		System.out.print("模板识别完毕，准备模板解析");		
		
		// 步骤4：解析
		TemplateParseProcessor tpap = new TemplateParseProcessor();
		return tpap.processParse(rawData, template);
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}
}

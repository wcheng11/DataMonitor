package edu.thss.monitor.rsp.service.parse.treat.template.parse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Template;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.relate.Tem2TemPara;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.process.EmbeddedPacketParser;
import edu.thss.monitor.rsp.service.parse.treat.template.ITemplateParser;
import edu.thss.monitor.rsp.service.parse.util.ByteConvertUtil;

/**
 * 二进制数据模板解析 用于解析二进制数据类型的报文
 * 
 * @author zhuangxy 2013-1-16
 */
public class BinaryParser implements ITemplateParser {

	private static final int BASE_INFO_PARAMATER = 1;
	private static final int WORK_STATUS_PARAMETER = 2;
	private static final int PROTOCOL_PARAMETER = 3;
	private static final int NULL_PARAMETER = 4;

	/**
	 * 日志记录
	 */
	// protected static Log logger = LogFactory.getLog(BinaryParser.class);

	/**
	 * 基本信息参数列表
	 */
	private Map<String, String> baseInfoMap;

	/**
	 * 工况信息参数列表
	 */
	private Map<String, String> workStatusMap;

	/**
	 * 转为String类型的未解析的原始工况数据
	 */
	private String packetData;

	/**
	 * 模板到模板参数关联记录
	 */
	private List<Tem2TemPara> tem2TemParaList;
	
	/**
	 * 返回结果
	 */
	private ParsedDataPacket pdp;

	/**
	 * 模板的模板参数列表
	 */
	// private List<TemplatePara> parameterList;

	@SuppressWarnings("unchecked")
	public BinaryParser() {
		this.baseInfoMap = new HashMap();
		this.workStatusMap = new HashMap();
	}

	@Override
	public ParsedDataPacket parse(Object data, Template template,
			String treatPara) throws RSPException {

		LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "正在获取模板参数列表...");

		tem2TemParaList = template.getTem2TemParaList();

		TemplatePara tempPara;
		String tempParaValue;

		// 把byte[]类型的数据转化为二进制的String字段
		this.packetData = ByteConvertUtil.bytesToBinaryString((byte[]) data);

		LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "正在根据模板解析报文...");

		for (Tem2TemPara tem2TemPara : tem2TemParaList) {

			tempPara = tem2TemPara.getTemplatePara();
			tempParaValue = "";
			LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "正在解析模板参数"
					+ tempPara.getParameterID() + "...");

			int paraType = tempPara.getParameterType();
			// 基础信息参数，或工况参数，或保留字段参数
			if (paraType == BASE_INFO_PARAMATER
					|| paraType == WORK_STATUS_PARAMETER
					|| paraType == NULL_PARAMETER) {

				int offset = Integer.parseInt(tem2TemPara.getOffset()); // 偏移量
				tempParaValue = this.getTempParaValue(tempPara, offset);

				if (tempPara.getParameterType() == NULL_PARAMETER) { // 保留字段参数，无意义
					// 不做处理
				} else if (paraType == BASE_INFO_PARAMATER) { // 基础信息参数
					baseInfoMap.put(tempPara.getParameterID(), tempParaValue);
				} else { // 工况参数
					workStatusMap.put(tempPara.getParameterID(), tempParaValue);
				}

				LogRecord.debug(LogConstant.LOG_FLAG_PARSE
						+ tempPara.getParameterID() + " "
						+ tempPara.getParameterName() + "参数解析完成，参数值："
						+ tempParaValue);

			}
			// 协议参数
			else if (paraType == PROTOCOL_PARAMETER) {

				int offset = Integer.parseInt(tem2TemPara.getOffset()); // 偏移量
				tempParaValue = this.getTempParaValue(tempPara, offset);

				// 嵌套协议解析
				// TODO: 这里的转化效率不高，可考虑其他高效的解决方案
				byte[] convert = ByteConvertUtil
						.binaryStringToBytes(tempParaValue);

				EmbeddedPacketParser epp = new EmbeddedPacketParser();
				ParsedDataPacket pdata = epp.parseEmbeddedPacket(convert,
						tempPara);

				workStatusMap.putAll(pdata.getWorkStatusMap());
				if(pdata.getCommonKey() != null){
					pdp.setCommonKey(template.getCommonKey());
				}
				if(pdata.getUniqueKey() != null){
					pdp.setUniqueKey(template.getUniqueKey());
				}
				

			}
			// 未定义模板参数类型
			else {
				throw new RSPException(LogConstant.LOG_FLAG_PARSE, tempPara
						.getParameterID(), "模板参数" + tempPara.getParameterID()
						+ "类型错误，未定义的模板参数类型");
			}
		}

		pdp = new ParsedDataPacket();
		if(template.getCommonKey() != null){
			pdp.setCommonKey(template.getCommonKey());
		}
		if(template.getUniqueKey() != null){
			pdp.setUniqueKey(template.getUniqueKey());
		}
		pdp.setBaseInfoMap(baseInfoMap);
		pdp.setWorkStatusMap(workStatusMap);
		return pdp;
	}

	/**
	 * 根据模板参数和偏移量获取参数值
	 * 
	 * @param tempPara
	 *            模板参数
	 * @param offset
	 *            模板参数偏移量
	 * @return 模板参数值
	 */
	private String getTempParaValue(TemplatePara tempPara, int offset) {
		String tempParaValue = null;
		if (offset >= 0) { // 偏移量为整数，从前往后截取

			tempParaValue = packetData.substring(offset, offset
					+ this.getParaLength(tempPara));

		} else { // 偏移量为负数，从后往前截取
			int end = packetData.length() + offset + 1;
			int start = end - this.getParaLength(tempPara);

			tempParaValue = packetData.substring(start, end);
		}
		return tempParaValue;
	}

	/**
	 * 获取模板参数的长度
	 * 
	 * @param tempPara
	 *            模板参数
	 * @return 模板参数的长度
	 */
	private int getParaLength(TemplatePara tempPara) {

		// 模板参数有定义长度
		if (tempPara.getLength() != null) {
			return tempPara.getLength();
		}
		// 模板参数无定义长度，模板中最多只允许有一个无定义长度的模板参数
		else {
			int length = packetData.length();
			// 本参数的长度 = 总长度 - 其它参数的长度
			for (Tem2TemPara ttp : tem2TemParaList) {
				if (ttp.getTemplatePara().getLength() != null) {
					length -= ttp.getTemplatePara().getLength();
				}
			}
			return length;
		}
	}

	@Test
	public void testOffset() {

		// String str = "12345678";
		// int offset = -3;
		// int length = 4;
		//
		// int end = str.length() + offset + 1;
		// int start = end - length;
		//
		// String result = str.substring(start, end);
		// System.out.println(result);
		// Assert.assertEquals(result, "3456");

		// String test = "00110000";
		// byte b = Byte.parseByte(test, 2);
		// System.out.println(b);

	}
}

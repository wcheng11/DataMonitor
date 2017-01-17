package edu.thss.monitor.rsp.service.parse.process;

import java.util.Map;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.exception.RSPException;


/**
 * 嵌套协议解析器，用于解析协议参数的数据
 * 
 * @author zhuangxy 2013-1-16
 */
public class EmbeddedPacketParser {

	/**
	 * 日志记录
	 */
//	protected static Log logger = LogFactory.getLog(BinaryParser.class);
	
//	protected static ApplicationContext context = AppContext.getSpringContext();
//	BaseDAO dao = (BaseDAO) context.getBean("baseDAO");

	/**
	 * 解析嵌套协议数据
	 * 
	 * @param packetData 协议参数数据
	 * @param tempPara 模板参数
	 * @return 嵌套协议中的工况数据列表
	 * @throws RSPException
	 */
	public ParsedDataPacket parseEmbeddedPacket(Object packetData,
			TemplatePara tempPara) throws RSPException {

		LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "模板参数" + tempPara.getParameterID() + "是协议参数");
		RawDataPacket rawData = new RawDataPacket();
		Protocol p = this.getProtocol(tempPara);
		
		rawData.setPacketData(packetData);

		ReusablePacketParse rpp = new ReusablePacketParse(p);
		ParsedDataPacket parsedData = rpp.reusableParse(rawData);

		return parsedData;
	}

	private Protocol getProtocol(TemplatePara tempPara) throws RSPException {
		
		Protocol protocol = (Protocol) RegionalRC.getResourceItem("protocol",
				tempPara.getParameterName());
		
//		Protocol protocol = (Protocol) dao.findById(Protocol.class, tempPara.getParameterName());
		
		
		if (protocol != null) {
			LogRecord.debug(LogConstant.LOG_FLAG_PARSE + "协议获取完毕，协议名称：" + protocol.getProtocalName());
			return protocol;
		} else {
			throw new RSPException(LogConstant.LOG_FLAG_PARSE, tempPara.getParameterName(), "协议识别错误：未能正常识别协议参数" +
					tempPara.getParameterName()+ "的嵌套协议");
		}
	}

}

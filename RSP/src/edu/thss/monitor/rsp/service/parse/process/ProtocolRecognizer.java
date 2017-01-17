package edu.thss.monitor.rsp.service.parse.process;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.pub.LogConstant;
import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.exception.RSPException;

/**
 * 根据报文的数据来源获取协议 报文的数据来源是指监测平台接收报文的端口或ftp地址
 * 
 * @author zhuangxy 2013-1-8
 */
public class ProtocolRecognizer {

	/**
	 * 日志记录
	 */
	// protected static Log logger =
	// LogFactory.getLog(ProtocolRecognizer.class);

	// protected static ApplicationContext context =
	// AppContext.getSpringContext();
	// BaseDAO dao = (BaseDAO) context.getBean("baseDAO");
	/**
	 * 识别的协议
	 */
	private Protocol protocol;

	/**
	 * 根据报文数据来源获取协议
	 * 
	 * @param packetSource
	 *            报文数据来源，如接收报文的端口号或ftp地址
	 * @return 数据来源对应的协议
	 * @throws RSPException
	 */
	public Protocol recognizeProtocol(String packetSource) throws RSPException {

		protocol = null;
		LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG + "正在获取协议，协议数据来源："
				+ packetSource);
//		System.out.print(LogConstant.LOG_FLAG_PROTOCOLRECONG + "正在获取协议，协议数据来源："
//				+ packetSource);

		protocol = (Protocol) RegionalRC.getResourceItem("source2Protocol",
				packetSource);

		if (protocol != null) {
			LogRecord.debug(LogConstant.LOG_FLAG_PROTOCOLRECONG
					+ "协议获取完毕，协议名称：" + protocol.getProtocalName());
			
//		System.out.print(LogConstant.LOG_FLAG_PROTOCOLRECONG
//				+ "协议获取完毕，协议名称：" + protocol.getProtocalName());	
			
			return protocol;
		} else {
			throw new RSPException(LogConstant.LOG_FLAG_PROTOCOLRECONG,
					packetSource, "协议识别错误：未能正常识别协议，协议数据来源" + packetSource);
		}

	}

}

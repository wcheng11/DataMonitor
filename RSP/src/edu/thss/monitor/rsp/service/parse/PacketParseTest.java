package edu.thss.monitor.rsp.service.parse;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import edu.thss.monitor.base.logrecord.imp.LogRecord;
import edu.thss.monitor.base.resource.RegionalRC;
import edu.thss.monitor.base.resource.bean.ChangeInfo;
import edu.thss.monitor.pub.entity.Device;
import edu.thss.monitor.pub.entity.Protocol;
import edu.thss.monitor.pub.entity.TemplatePara;
import edu.thss.monitor.pub.entity.service.ParsedDataPacket;
import edu.thss.monitor.pub.entity.service.RawDataPacket;
import edu.thss.monitor.pub.exception.RSPException;
import edu.thss.monitor.rsp.service.parse.process.ProtocolRecognizer;
import edu.thss.monitor.rsp.service.parse.process.ReusablePacketParse;
import edu.thss.monitor.rsp.service.parse.process.TempValueTransformer;
import edu.thss.monitor.rsp.service.parse.util.SyncUtil;

/**
 * 报文解析，是报文解析模块唯一的对外接口
 * 
 * @author zhuangxy 2013-1-10
 */
public class PacketParseTest {

	/**
	 * 日志记录
	 */
//	protected static Log logger = LogFactory.getLog(PacketParseService.class);
	
	/**
	 * 解析后的报文数据
	 */
	private ParsedDataPacket parsedData;

	/**
	 * 解析报文数据
	 * 
	 * @param rawData
	 *            从数据接收模块接收的原始数据报文
	 * @return 解析后的数据报文
	 * @throws RSPException
	 */
	public ParsedDataPacket parsePacket(RawDataPacket rawData)
			throws RSPException {

		this.checkRawData(rawData);

		// 步骤1：识别协议
		ProtocolRecognizer pr = new ProtocolRecognizer();
		Protocol protocol = pr.recognizeProtocol(rawData.getPacketSource());

		/**
		 * 以下代码段包含步骤： 步骤2：模板识别 步骤3：模板处理，包括校验、解密、解压缩等 步骤4：解析
		 */
		ReusablePacketParse rpp = new ReusablePacketParse(protocol);
		this.parsedData = rpp.reusableParse(rawData);

		// 步骤5：模板参数处理（数据转换）
		TempValueTransformer tvt = new TempValueTransformer();
		this.parsedData = tvt.transformTempValue(parsedData);

		// 步骤6：设备识别
		TemplatePara commonKey = parsedData.getCommonKey();
		String commonValue = parsedData.getBaseInfoMap().get(
				commonKey.getParameterID());
		Device device = SyncUtil.getDeviceByCommonKey(commonValue);

		// 步骤7：ip与设备映射维护
		ChangeInfo changeInfo = new ChangeInfo(ChangeInfo.CHANGETYPE_UPDATE,
				"device2ip", device.getDeviceID(), rawData.getIp());
		// 进行更改同步
		RegionalRC.syncChange(changeInfo);

		// 步骤8：数据封装
		this.parsedData.setDevice(device);
		this.parsedData.setTimestamp(rawData.getTimestamp());
		this.parsedData.setIp(rawData.getIp());

		return parsedData;

	}

	/**
	 * 检查rawData是否合法
	 * 
	 * @param rawData
	 */
	private boolean checkRawData(RawDataPacket rawData) {
		// TODO： 检查rawData是否合法

		return false;

	}

	@Test
	public void testPacketParseService() {

		byte[] data = { 0x30, 0x30, 0x4, 0, 1, 0x4b, 0x19, 0x5a, 0x1f, 0x41, 0x35, 0x1a, 
//				0x38, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8, 
//				0x1e, 0x17, 0x1f, 0x1c, 9, 10, 11, 12, 13, 14, 15, 16,
//				0x14, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8, 
//				0x3e, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//				0x01, 0x17, 0x1f, 0x1c, 9, 10, 11, 12, 13, 14, 15, 16,
//				0x31, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//				0x16, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//				0x1a, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//				0x1b, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//				(byte)0x80, 0x17, 0x1f, 0x1c, 1, 2, 3, 4, 5, 6, 7, 8,
//				0x11, 0x60, 0x1f, 0x1c, 1, 1, 0, 0, 2, 0, 0, 0,
//				0x12, 0x60, 0x1f, 0x1c, 2, 1, 0, 0, 1, 0, 0, 0,
//				0x17, 0x17, 0x1f, 0x1c, 1, 1, 0, 0, 1, 1, 0, 0,
//				0x1f, 0x60, 0x1f, 0x1c, 0x55, 0, 0, 0, 0, 0, 0, 0,
				0x0a, 0x60, 0x1f, 0x1c, 0x53, 0x00, 0, 1, 0x21, 0x05, 0x32, 0x49, 
				0x0b, 0x60, 0x1f, 0x1c, 0x20, 0x13, 0x10, 0x52, 0x1f, 0x1c, 0x01, 0, 
				0x0c, 0x60, 0x1f, 0x1c, 0, 0, 0, 0, 0, 0, (byte)0xc6, 0x01,
				(byte) 0xce, 0x2 };
		
		RawDataPacket rawData = new RawDataPacket();
		rawData.setIp("testip");
		rawData.setPacketData(data);
		rawData.setPacketSource("port:5050");
		rawData.setTimestamp(new Date());
		
		
		try {
			ProtocolRecognizer pr = new ProtocolRecognizer();
			Protocol protocol = pr.recognizeProtocol(rawData.getPacketSource());
			
			ReusablePacketParse rpp = new ReusablePacketParse(protocol);
			this.parsedData = rpp.reusableParse(rawData);
			
			TempValueTransformer tvt = new TempValueTransformer();
			this.parsedData = tvt.transformTempValue(parsedData);
			
			LogRecord.debug("报文解析结果：");
			LogRecord.debug("基础信息：");
			
			Iterator iter = parsedData.getBaseInfoMap().entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				LogRecord.debug(entry.getKey()+ ": " + entry.getValue());
			}
			LogRecord.debug("工况：");
			iter = parsedData.getWorkStatusMap().entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				LogRecord.debug(entry.getKey()+ ": " + entry.getValue());
			}
			
			
		} catch (RSPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
